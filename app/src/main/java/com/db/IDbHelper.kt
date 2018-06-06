package com.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class IDbHelper : SQLiteOpenHelper {

    constructor(context: Context?) : super(context , "clearDeskDbinfo" , null , 1)

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}