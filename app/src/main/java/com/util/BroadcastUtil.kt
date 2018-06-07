package com.util

import android.content.Intent
import com.application.IApplication

class BroadcastUtil {

    companion object {

        fun sendBroadcast(action: String){
            IApplication.getContext().sendBroadcast(Intent(action))
        }

    }

}