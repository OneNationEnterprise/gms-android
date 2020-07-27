package com.gymapp.features.classes.presentation.list.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.features.classes.data.model.ClassDate
import com.gymapp.features.classes.list.presentation.adapter.ClassSelectedListener
import kotlinx.android.synthetic.main.item_class_date_list.view.*
import org.jetbrains.anko.textColor


class DatesAdapter(
    var selectedClassDate: ClassDate,
    var dates: ArrayList<ClassDate>,
    private val classSelectedListener: ClassSelectedListener
) :
    RecyclerView.Adapter<ClassDateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassDateViewHolder {
        return ClassDateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_class_date_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun onBindViewHolder(holder: ClassDateViewHolder, position: Int) {
        holder.bindView(dates[position], classSelectedListener, selectedClassDate)
    }

    fun updateSelectedClassDate(classDate: ClassDate) {
        this.selectedClassDate = classDate
        notifyDataSetChanged()
    }

    fun initAdapter(classDate: ClassDate, dates: ArrayList<ClassDate>) {
        this.selectedClassDate = classDate
        this.dates = dates
        notifyDataSetChanged()
    }

}

class ClassDateViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(
        classDate: ClassDate,
        classSelectedListener: ClassSelectedListener,
        selectedClassDate: ClassDate,
        showPadding:Boolean
    ) {

        if (classDate == selectedClassDate) {
            itemView.classDateTv.background =
                itemView.resources.getDrawable(R.drawable.bg_round_red)
            itemView.classDateTv.textColor = itemView.resources.getColor(R.color.white)
            itemView.classDateTv.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        } else {
            itemView.classDateTv.background =
                itemView.resources.getDrawable(R.drawable.bg_round_red_margin_blue_bg)
            itemView.classDateTv.textColor = itemView.resources.getColor(R.color.red001)
            itemView.classDateTv.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        }

        itemView.classDateTv.text = classDate.dateToBeShownInAdapter

        itemView.classDateTv.setOnClickListener {
            classSelectedListener.onClassDateSelected(classDate)
        }

    }
}