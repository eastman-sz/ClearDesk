package com.gallery

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

class GalleryPagerAdapter<T : View> : PagerAdapter {

    private var context : Context ?= null
    private var list : List<T> = ArrayList<T>()

    constructor(context: Context , list: List<T>){
        this.context = context
        this.list = list
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return 10000
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = list.get(position % 3)
        if (container == view.parent) {
            container.removeView(view)
        }
        container.addView(view)

        return view
    }

}