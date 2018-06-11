package com.sys

import com.appinfo.AppInfo

interface OnDeskAppSetAdapterClickListener {

    fun onAddApp()

    fun onItemClick(position : Int , appInfo: AppInfo)

}