package com.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gallery.GalleryDbHelper

class IDbHelper : SQLiteOpenHelper {

    constructor(context: Context?) : super(context , "clearDeskDbinfo" , null , 1)

    override fun onCreate(db: SQLiteDatabase?) {

        GalleryDbHelper.createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        GalleryDbHelper.dropTable(db)
    }

}