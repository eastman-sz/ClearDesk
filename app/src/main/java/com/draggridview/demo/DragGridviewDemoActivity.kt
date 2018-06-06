package com.draggridview.demo

import android.os.Bundle
import com.common.base.BaseAppCompactActivitiy
import com.draggridview.DynamicGridView
import com.sz.sk.clear.desk.R
import kotlinx.android.synthetic.main.activity_drag_gridview_demo.*

class DragGridviewDemoActivity : BaseAppCompactActivitiy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_gridview_demo)

        initActivitys()
    }

    override fun initTitle() {

    }

    override fun initViews() {
        val adapter = CheeseDynamicAdapter(context , Cheeses.sCheeseStrings.asList(),
                resources.getInteger(R.integer.column_count))
        dynamicGridView.adapter = adapter

        dynamicGridView.setOnDragListener(object : DynamicGridView.OnDragListener{
            override fun onDragStarted(position: Int) {

            }

            override fun onDragPositionsChanged(oldPosition: Int, newPosition: Int) {

            }
        })

        dynamicGridView.setOnItemLongClickListener { parent, view, position, id ->
            dynamicGridView.startEditMode(position)

            true
        }
    }

    override fun onBackPressed() {
        if (dynamicGridView.isEditMode()) {
            dynamicGridView.stopEditMode()
        } else {
            super.onBackPressed()
        }
    }
}
