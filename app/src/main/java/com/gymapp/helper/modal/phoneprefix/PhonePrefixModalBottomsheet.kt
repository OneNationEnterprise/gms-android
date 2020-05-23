package com.gymapp.helper.modal.phoneprefix

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gymapp.R
import com.gymapp.main.data.model.country.Country
import kotlinx.android.synthetic.main.bottomsheet_phone_prefix.*
import java.lang.reflect.GenericDeclaration
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable

class PhonePrefixModalBottomsheet(
    var countries: List<Country>,
    val listener: PhonePrefixSelectedListener
) : BottomSheetDialogFragment(), PhonePrefixSelectedListener {

    private var phonePrefixAdapter: PhonePrefixAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_phone_prefix, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phonePrefixAdapter = PhonePrefixAdapter(countries, this)

        val countriesLL = LinearLayoutManager(context)

        countriesLL.orientation = RecyclerView.VERTICAL
        countriesRv.layoutManager = countriesLL

        countriesRv.adapter = phonePrefixAdapter

        closeIv.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            ) as FrameLayout

            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    override fun dialCodeSelected(country: Country) {
        this.dismiss()
        listener.dialCodeSelected(country)
    }

    companion object {
        const val TAG = "CountriesOptionSelectionModalBottomSheet"
    }
}