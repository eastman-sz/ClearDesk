package com.util

class PrefHelper {

    companion object {

        fun setGalleryEnabled(enabled : Boolean){
            PrefUtil.instance().setIntPref(Prefkey.GALLERY_SWITCH , if (enabled) 1 else 0)
        }

        fun isGalleryEnabled() : Boolean{
            val a = PrefUtil.instance().getIntPref(Prefkey.GALLERY_SWITCH , 1)
            return a == 1
        }


    }

}