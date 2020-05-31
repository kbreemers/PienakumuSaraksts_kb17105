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

    fun addResponsibility(responsibility : PienakumuSaraksts): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, responsibility.name)
        cv.put(COL_IS_RESOLVED, responsibility.isResolved)
//        if (responsibility.isResolved)
//            cv.put(COL_IS_RESOLVED, true)
//        else
//            cv.put(COL_IS_RESOLVED, false)

        val result:Long = db.insert(TABLE_PIENAKUMI,null, cv)
        return result != (-1).toLong()
    }

    fun deleteResponsibility(id: Long) {
        val db = writableDatabase
        db.delete(TABLE_PIENAKUMI, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun updateResponsibilty(responsibility : PienakumuSaraksts) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, responsibility.name)
//        cv.put(COL_IS_RESOLVED, responsibility.isResolved)
        db.update(TABLE_PIENAKUMI,cv, "$COL_ID=?", arrayOf(responsibility.id.toString()))
    }

    fun updateAsResolved(responsibilityId: Long, isResolved: Boolean) {
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_PIENAKUMI WHERE $COL_ID = $responsibilityId", null)
        if (queryResult.moveToFirst()) {
            do {
                val pienakumuSaraksts = PienakumuSaraksts()
                pienakumuSaraksts.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                pienakumuSaraksts.name = queryResult.getString(queryResult.getColumnIndex(
                    COL_NAME))
                pienakumuSaraksts.createdAt = queryResult.getString(queryResult.getColumnIndex(
                    COL_CREATED_AT))
                pienakumuSaraksts.isResolved = isResolved
                updateResponsibilty(pienakumuSaraksts)
            } while (queryResult.moveToNext())
        }
        queryResult.close()
    }

    fun getResponsibility() : MutableList<PienakumuSaraksts> {
        val result : MutableList<PienakumuSaraksts> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_PIENAKUMI", null)
        if (queryResult.moveToFirst()) {
            do {
                val pienakumuSaraksts = PienakumuSaraksts()
                pienakumuSaraksts.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                pienakumuSaraksts.name = queryResult.getString(queryResult.getColumnIndex(
                    COL_NAME))
                pienakumuSaraksts.createdAt = queryResult.getString(queryResult.getColumnIndex(
                    COL_CREATED_AT))
                pienakumuSaraksts.isResolved = queryResult.getInt(queryResult.getColumnIndex(
                    COL_IS_RESOLVED)) == 1
                result.add(pienakumuSaraksts)
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }
}