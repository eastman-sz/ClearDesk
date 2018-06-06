package com.util

import android.util.Log

class ILog {

    companion object {

        private const val LOGKEY = "clearDesk"

        fun e(msg : String){
            Log.e(LOGKEY , msg)
        }

        fun d(msg : String){
            Log.d(LOGKEY , msg)
        }

    }

}