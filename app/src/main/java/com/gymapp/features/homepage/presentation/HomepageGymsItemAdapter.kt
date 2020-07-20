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
import kotlinx.android.synthetic.main.item_homepage_brand.view.*
import java.util.ArrayList

class HomepageGymsItemAdapter(
    val context: Context,
    private val brandListItem: MutableList<HomepageBrandListItem>,
    private val clickListener: HomepageGymClickListener
) :
    PagerAdapter() {
    override fun isViewFromObject(view: View, anyObject: Any): Boolean {
        return view == anyObject
    }

    override fun getCount(): Int {
        return brandListItem.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_homepage_brand, container, false) as ViewGroup

//        if (!brandsNearSelectedLocation[position].carouselImage().isNullOrEmpty()) {
//            Picasso.get().load(brandsNearSelectedLocation[position].carouselImage()).into(view.brandFeaturedPhoto)
//        }
//
//        if (!brandsNearSelectedLocation[position].profilePhoto().isNullOrEmpty()) {
//            Picasso.get().load(brandsNearSelectedLocation[position].profilePhoto()).into(view.miniLogo)
//        }
//
        view.passesContainer.setOnClickListener {
            clickListener.hasSelectedPasses(brandListItem[position].brand.brandId)
        }

        view.productImageContainer.setOnClickListener {
            clickListener.hasSelectedABrand(brandListItem[position].brand.brandId)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, objectView: Any) {
        container.removeView(objectView as ConstraintLayout?)
    }

}