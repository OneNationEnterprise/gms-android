package com.gymapp.features.classes.presentation.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.classes.data.model.ClassDate
import com.gymapp.features.classes.domain.ClassesViewModel
import com.gymapp.features.classes.presentation.detail.ClassDetailActivity
import com.gymapp.features.classes.presentation.list.adapter.ClassesAdapter
import com.gymapp.features.classes.presentation.list.adapter.DatesAdapter
import com.gymapp.helper.Constants
import com.gymapp.helper.DateHelper
import kotlinx.android.synthetic.main.activity_classes_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ClassesActivity : BaseActivity(R.layout.activity_classes_list) {

    lateinit var classesViewModel: ClassesViewModel

    lateinit var classesAdapter: ClassesAdapter

    lateinit var datesAdapter: DatesAdapter

    lateinit var bundle: Bundle

    override fun setupViewModel() {
        classesViewModel = getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bundle = intent.getBundleExtra(Constants.arguments) ?: return

        setTitle(bundle.getString(Constants.classesListPageName)!!)

        setupAdapter()

        GlobalScope.launch {
            classesViewModel.fetchClassesList(
                bundle.getString(Constants.gymId),
                DateHelper.getCurrentDate(),
                bundle.getString(Constants.categoryId)
            )
        }

        classesViewModel.createDatesList()
    }

    private fun setupAdapter() {
        classesAdapter = ClassesAdapter(ArrayList(), classesViewModel)

        val linearLayoutManager =
            LinearLayoutManager(this@ClassesActivity, LinearLayoutManager.VERTICAL, false)

        classesRv.apply {
            adapter = classesAdapter
            layoutManager = linearLayoutManager
        }

        datesAdapter = DatesAdapter(ClassDate("", ""), ArrayList(), classesViewModel)

        val horizontalLinearLayoutManager =
            LinearLayoutManager(this@ClassesActivity, LinearLayoutManager.HORIZONTAL, false)

        datesRv.apply {
            adapter = datesAdapter
            layoutManager = horizontalLinearLayoutManager
        }

    }

    override fun bindViewModelObservers() {
        classesViewModel.classesList.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                noDataTv.visibility = View.VISIBLE
                classesAdapter.updateList(ArrayList())
                return@Observer
            }

            noDataTv.visibility = View.GONE

            classesAdapter.updateList(classes = it)
        })

        classesViewModel.classId.observe(this, Observer {
            val intent = Intent(this, ClassDetailActivity::class.java)
            val args = Bundle()

            args.putString(Constants.classId, it)

            intent.putExtra(Constants.arguments, args)

            startActivity(intent)
        })

        classesViewModel.classDate.observe(this, Observer {

            classesAdapter.updateList(ArrayList())
            datesAdapter.updateSelectedClassDate(it)

            GlobalScope.launch {
                classesViewModel.fetchClassesList(
                    bundle.getString(Constants.gymId),
                    it.dateForServerRequest,
                    bundle.getString(Constants.categoryId)
                )
            }
        })

        classesViewModel.datesList.observe(this, Observer {
            datesAdapter.initAdapter(it[0], it)
        })
    }

}