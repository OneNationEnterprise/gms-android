package com.gymapp.features.classes.list.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.classes.domain.ClassesViewModel
import com.gymapp.features.classes.presentation.detail.ClassDetailActivity
import com.gymapp.helper.Constants
import com.gymapp.helper.DateHelper
import kotlinx.android.synthetic.main.activity_classes_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ClassesActivity : BaseActivity(R.layout.activity_classes_list) {

    lateinit var classesViewModel: ClassesViewModel

    lateinit var classesAdapter: ClassesAdapter

    override fun setupViewModel() {
        classesViewModel = getViewModel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.getBundleExtra(Constants.arguments) ?: return

        setTitle(bundle.getString(Constants.classesListPageName)!!)

        setupAdapter()

        GlobalScope.launch {
            classesViewModel.fetchClassesList(
                bundle.getString(Constants.gymId),
                DateHelper.getCurrentDate(),
                bundle.getString(Constants.categoryId)
            )
        }
    }

    private fun setupAdapter() {
        classesAdapter = ClassesAdapter(ArrayList(), classesViewModel)

        val linearLayoutManager =
            LinearLayoutManager(this@ClassesActivity, LinearLayoutManager.VERTICAL, false)

        classesRv.apply {
            adapter = classesAdapter
            layoutManager = linearLayoutManager
        }
    }

    override fun bindViewModelObservers() {
        classesViewModel.classesList.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                noDataTv.visibility = View.VISIBLE
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
    }

}