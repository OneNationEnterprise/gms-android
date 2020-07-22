package com.gymapp.features.homepage.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.gymapp.R
import com.gymapp.features.homepage.presentation.adapter.HomepageGymClickListener
import com.gymapp.main.data.model.brand.HomepageBrandListItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_homepage_brand.view.*

class HomepageGymsItemAdapter(
    val context: Context,
    private val gymListItem: MutableList<HomepageBrandListItem>,
    private val clickListener: HomepageGymClickListener
) :
    PagerAdapter() {
    override fun isViewFromObject(view: View, anyObject: Any): Boolean {
        return view == anyObject
    }

    override fun getCount(): Int {
        return gymListItem.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_homepage_brand, container, false) as ViewGroup

        val gym = gymListItem[position]

        if (gym.gymImage.isNotEmpty()) {
            Picasso.get().load(gym.gymImage).into(view.gymImageIv)
        }

        if (gym.brand.logo.isNotEmpty()) {
            Picasso.get().load(gym.brand.logo).into(view.brandLogoIv)
        }

        view.passesContainer.setOnClickListener {
            clickListener.hasSelectedPasses(gymListItem[position].brand.brandId)
        }

        view.productImageContainer.setOnClickListener {
            clickListener.hasSelectedABrand(gymListItem[position].brand.brandId)
        }

        view.numberOfGymsTv.text =
            view.context.getString(R.string.homepage_gyms_nearby, gym.brand.gymCount.toString())

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, objectView: Any) {
        container.removeView(objectView as ConstraintLayout?)
    }

}