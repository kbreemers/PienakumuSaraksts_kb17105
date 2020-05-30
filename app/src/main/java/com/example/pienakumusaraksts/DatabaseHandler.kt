package com.example.pienakumusaraksts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pienakumusaraksts.DTO.PienakumuSaraksts

class DatabaseHandler(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_PIENAKUMI (" +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                "$COL_NAME varchar," +
                "$COL_IS_RESOLVED integer);"

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun addPienakums(pienakums : PienakumuSaraksts): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, pienakums.name)
        val result:Long = db.insert(TABLE_PIENAKUMI,null, cv)
        return result != (-1).toLong()
    }

    fun getPienakums() : MutableList<PienakumuSaraksts>{
        val result : MutableList<PienakumuSaraksts> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_PIENAKUMI", null)
        if (queryResult.moveToFirst()) {
            do {
                val pienakumuSaraksts = PienakumuSaraksts()
                pienakumuSaraksts.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                pienakumuSaraksts.name = queryResult.getString(queryResult.getColumnIndex(
                    COL_NAME))
//                pienakumuSaraksts.createdAt = queryResult.getString(queryResult.getColumnIndex(
//                    COL_CREATED_AT))
//                pienakumuSaraksts.isResolved = queryResult.getLong(queryResult.getColumnIndex(
//                    COL_IS_RESOLVED))
                result.add(pienakumuSaraksts)
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }
}