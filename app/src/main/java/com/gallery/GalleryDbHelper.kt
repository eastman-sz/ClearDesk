package com.gallery

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.db.CursorHelper
import com.db.DbTableHelper
import com.db.ISqliteDataBase
import com.util.BroadcastAction
import com.util.BroadcastUtil
import com.util.Constant

class GalleryDbHelper {

    companion object {

        fun save(imgPaths : List<String>){
            delete()

            val db = ISqliteDataBase.getSqLiteDatabase()
            db.beginTransaction()
            try {
                imgPaths.forEach {

                    val contentValues = ContentValues()
                    contentValues.put("imgPath" , it)

                    db.insert(DBNAME , null , contentValues)
                }
            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                db.setTransactionSuccessful()
                db.endTransaction()

                BroadcastUtil.sendBroadcast(BroadcastAction.GALLERY_IMAGE_REFRESH)
            }
        }

        fun getAllGalleryPath() : List<String>{
            val list = ArrayList<String>()
            var cursor : Cursor ?= null
            try {
                val db = ISqliteDataBase.getSqLiteDatabase()
                cursor = db.query(DBNAME , null, null, null, null, null, null, null)
                if (null != cursor){
                    while (cursor!!.moveToNext()){
                        val imgPath = CursorHelper.getString(cursor , "imgPath")

                        list.add(imgPath)
                    }
                }
            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                cursor?.close()
            }
            return list
        }

        fun delete(){
            val db = ISqliteDataBase.getSqLiteDatabase()
            db.delete(DBNAME , null , null)
        }

        private val DBNAME = Constant.APP_FLAG + "galleryInfo"

        fun createTable(db: SQLiteDatabase?) {
            DbTableHelper.fromTableName(DBNAME)
                    .addColumn_Varchar("imgPath", 50)

                    .buildTable(db)
        }

        fun dropTable(db: SQLiteDatabase?) {
            db?.execSQL("drop table if exists $DBNAME")
        }

    }

}