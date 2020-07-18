package com.gymapp.features.homepage.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.gymapp.R
import com.gymapp.main.data.model.brand.HomepageBrandListItem
import java.util.ArrayList

class HomepageGymsItemAdapter(
    var context: Context,
    var brandListItem: MutableList<HomepageBrandListItem>
) :
    PagerAdapter() {
    override fun isViewFromObject(view: View, anyObject: Any): Boolean {
        return view == anyObject
    }

    override fun getCount(): Int {
//        return brandListItem.size
        return 16
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
//        view.brandIconsContainer.setOnClickListener {
//            listener.onBrandSelected(brandsNearSelectedLocation[position].id())
//        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, objectView: Any) {
        container.removeView(objectView as ConstraintLayout?)
    }

}