package com.gymapp.features.profile.payment.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.gym.GetCustomerCardTokensQuery
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.payment.domain.CardsListViewModel
import com.gymapp.features.profile.payment.presentation.adapter.CardAdapterListener
import com.gymapp.features.profile.payment.presentation.adapter.CardsAdapter
import com.gymapp.helper.ui.InAppBannerNotification
import kotlinx.android.synthetic.main.activity_profile_cards_list.*
import kotlinx.android.synthetic.main.bottomsheet_dialog_delete_saved_card.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CardsListActivity : BaseActivity(R.layout.activity_profile_cards_list),
    CardsListViewListener, CardAdapterListener {

    lateinit var cardsListViewModel: CardsListViewModel
    lateinit var cardsAdapter: CardsAdapter
    private val SAVE_CARD_ACTIVITY_REQUEST_CODE = 128
    private var isOnEditMode = false
    lateinit var cardAdapterListener: CardAdapterListener
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.profile_payment))

        cardsListViewModel.cardsListViewListener = this

        GlobalScope.launch {
            cardsListViewModel.fetchData()
        }

        cardAdapterListener = this
        activity = this

        addNewCardTv.setOnClickListener {
            startActivityForResult(
                Intent(this, SaveCardActivity::class.java),
                SAVE_CARD_ACTIVITY_REQUEST_CODE
            )
        }
    }

    override fun setupViewModel() {
        cardsListViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {}

    override fun initRecycleView(cardsList: MutableList<GetCustomerCardTokensQuery.GetCustomerCardToken?>) {

        CoroutineScope(Dispatchers.Main).launch {
            if (cardsList.isNullOrEmpty()) {
                toolbarEdit.visibility = View.GONE
                noSavedCardsLayout.visibility = View.VISIBLE
                cardsAdapter =
                    CardsAdapter(
                        ArrayList(),
                        cardAdapterListener
                    )
            } else {
                toolbarEdit.visibility = View.VISIBLE
                noSavedCardsLayout.visibility = View.GONE
                cardsRv.visibility = View.VISIBLE
                cardsAdapter =
                    CardsAdapter(
                        cardsList,
                        cardAdapterListener
                    )

                toolbarEdit.setOnClickListener {
                    isOnEditMode = !isOnEditMode

                    setCardsAdapterDeleteIconVisibility()
                }

                setCardsAdapterDeleteIconVisibility()
            }

            cardsRv.apply {
                adapter = cardsAdapter
                layoutManager = LinearLayoutManager(context)
            }

            hideLoading()
        }


    }

    private fun setCardsAdapterDeleteIconVisibility() {
        if (isOnEditMode) {
            toolbarEdit.text = "Done"
            cardsAdapter.showDeleteIcon()
        } else {
            toolbarEdit.text = getString(R.string.edit)
            cardsAdapter.hideDeleteIcon()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SAVE_CARD_ACTIVITY_REQUEST_CODE) {
            GlobalScope.launch {
                cardsListViewModel.fetchData()
            }
        }
    }

    override fun openSaveCardActivity() {
        startActivityForResult(
            Intent(this, SaveCardActivity::class.java),
            SAVE_CARD_ACTIVITY_REQUEST_CODE
        )
    }

    override fun showLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.GONE
        }
    }

    override fun showErrorMessage(message: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            hideLoading()
            InAppBannerNotification.showErrorNotification(cardsListLayout, activity, message)
        }
    }

    override fun onDeleteSelected(cardId: String, cardEnding: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val view = layoutInflater.inflate(R.layout.bottomsheet_dialog_delete_saved_card, null)
            val dialog = BottomSheetDialog(activity, R.style.BottomSheetDialog)
            dialog.setContentView(view)

            view.deleteCardNumberTv.text = "Card ending $cardEnding"

            view.closeIv.setOnClickListener {
                dialog.dismiss()
            }

            view.cancelTv.setOnClickListener {
                dialog.dismiss()
            }

            view.deleteCardTv.setOnClickListener {
                dialog.dismiss()
                GlobalScope.launch {
                    cardsListViewModel.onDeleteCardClicked(cardId)
                }
            }

            dialog.show()
        }
    }


}