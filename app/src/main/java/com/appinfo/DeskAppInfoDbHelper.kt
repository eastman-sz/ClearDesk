package com.appinfo

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.db.CursorHelper
import com.db.DbTableHelper
import com.db.ISqliteDataBase
import com.util.Constant

class DeskAppInfoDbHelper {

    companion object {

        fun save(name : String , packageName : String , pageIndex : Int){
            val contentValue = ContentValues()
            contentValue.put("name" , name)
            contentValue.put("packageName" , packageName)
            contentValue.put("pageIndex" , pageIndex)

            val db = ISqliteDataBase.getSqLiteDatabase()
            db.insert(DBNAME , null , contentValue)
        }

        fun getDeskApps(pageIndex: Int)  : List<AppInfo>{
            val list = ArrayList<AppInfo>()
            var cursor : Cursor ?= null
            try {
                val db = ISqliteDataBase.getSqLiteDatabase()
                cursor = db.query(DBNAME , null , "pageIndex = ? " , arrayOf(pageIndex.toString()) , null , null , null)
                if (null == cursor){
                    return list
                }
                while (cursor.moveToNext()){
                    val appInfo = fromCusor(cursor)

                    list.add(appInfo)
                }
            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                cursor?.close()
            }
            return list
        }

        fun getDeskApps() : List<AppInfo>{
            val list = ArrayList<AppInfo>()
            var cursor : Cursor ?= null
            try {
                val db = ISqliteDataBase.getSqLiteDatabase()
                cursor = db.query(DBNAME , null , null , null , null , null , null)
                if (null == cursor){
                    return list
                }
                while (cursor.moveToNext()){
                    val appInfo = fromCusor(cursor)

                    list.add(appInfo)
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

        fun fromCusor(cursor: Cursor) : AppInfo{
            var name = CursorHelper.getString(cursor , "name")
            var packageName = CursorHelper.getString(cursor , "packageName")
            var pageIndex = CursorHelper.getInt(cursor , "pageIndex")

            val appInfo = AppInfo()
            appInfo.name = name
            appInfo.packageName = packageName
            appInfo.pageIndex = pageIndex

            return appInfo
        }

        private val DBNAME = Constant.APP_FLAG + "deskappInfo"

        fun createTable(db: SQLiteDatabase?) {
            DbTableHelper.fromTableName(DBNAME)
                    .addColumn_Varchar("name", 10)
                    .addColumn_Varchar("packageName", 50)
                    .addColumn_Integer("pageIndex")
                    .buildTable(db)
        }

        fun dropTable(db: SQLiteDatabase?) {
            db?.execSQL("drop table if exists $DBNAME")
        }

    }


}