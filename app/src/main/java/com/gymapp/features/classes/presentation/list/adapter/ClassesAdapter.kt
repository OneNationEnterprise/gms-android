package com.gymapp.features.classes.list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.gym.type.DifficultyLevel
import com.gymapp.R
import com.gymapp.features.classes.list.presentation.adapter.ClassSelectedListener
import com.gymapp.helper.DateHelper
import com.gymapp.main.data.model.classes.Class
import kotlinx.android.synthetic.main.item_classes_list_card.view.*
import org.jetbrains.anko.textColor

class ClassesAdapter(
    var classes: ArrayList<Class>,
    private val classSelectedListener: ClassSelectedListener
) :
    RecyclerView.Adapter<ClassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        return ClassViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_classes_list_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return classes.size
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bindView(classes[position], classSelectedListener)
    }

    fun updateList(classes: List<Class>) {
        this.classes.clear()
        this.classes.addAll(classes)
        notifyDataSetChanged()
    }

}

class ClassViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(
        gymClass: Class,
        classSelectedListener: ClassSelectedListener
    ) {

        itemView.startHourTv.text = gymClass.openTime
        itemView.classTitleTv.text = gymClass.name

        when (gymClass.difficultyLevel) {
            DifficultyLevel.BEGINNER -> {
                itemView.difficultyValueTv.text =
                    itemView.context.getString(R.string.difficulty_beginner)
                itemView.difficultyValueTv.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green002
                    )
                )
            }
            DifficultyLevel.INTERMEDIATE -> {
                itemView.difficultyValueTv.text =
                    itemView.context.getString(R.string.difficulty_intermediate)
                itemView.difficultyValueTv.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.yellow005
                    )
                )
            }
            DifficultyLevel.ADVANCE -> {
                itemView.difficultyValueTv.text =
                    itemView.context.getString(R.string.difficulty_advanced)
                itemView.difficultyValueTv.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.red003
                    )
                )
            }
            else -> {
                itemView.difficultyValueTv.visibility = View.GONE
            }
        }
        val spotsAlotted = gymClass.spotsAlotted ?: 0
        itemView.spotsValueTv.text = "${gymClass.spots - spotsAlotted}/${gymClass.spots}"

        itemView.caloriesBurnedValueTv.text = itemView.context.getString(
            R.string.estimate_cals_burned,
            gymClass.estimatedCaloriesBurnt
        )
        itemView.durationValueTv.text = "${gymClass.duration}'"

        itemView.priceValueTv.text = gymClass.amount

        itemView.classContainer.setOnClickListener {
            classSelectedListener.classSelectedListener(gymClass)
        }
    }
}