package com.example.projektzd.database

import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.fragment.app.FragmentActivity
import java.lang.NullPointerException
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


class DatabaseHelper(context: FragmentActivity?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
                "CREATE TABLE $TABLE_NAME ($COL_1 TEXT PRIMARY KEY,$COL_2 TEXT,$COL_3 TEXT,$COL_4 TEXT," +
                        "$COL_5 INTEGER,$COL_6 INTEGER,$COL_7 TEXT,$COL_8 INTEGER,$COL_9 INTEGER,$COL_10 TEXT,$COL_11 TEXT)"
        )

    }
    fun reset(db: SQLiteDatabase){
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL(
                "CREATE TABLE $TABLE_NAME ($COL_1 TEXT PRIMARY KEY,$COL_2 TEXT,$COL_3 TEXT,$COL_4 TEXT," +
                        "$COL_5 INTEGER,$COL_6 INTEGER,$COL_7 TEXT,$COL_8 INTEGER,$COL_9 INTEGER,$COL_10 TEXT,$COL_11 TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    val allData: Cursor
        get() {
            val db = this.writableDatabase
            return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        }

    fun getFavorite(id: String): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("SELECT $COL_8 FROM $TABLE_NAME WHERE $COL_1 = '"+id+"'", null)
    }
    fun getRead(id: String): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("SELECT $COL_9 FROM $TABLE_NAME WHERE $COL_1 = '"+id+"'", null)
    }

    fun insertData(
            id: String,
            name: String,
            rentalDate: String,
            returnDate: String,
            remainingDays: Int,
            pageCount: Int,
            thumbnail: String? = " ",
            favorite: Boolean,
            read: Boolean,
            authors: String? = " ",
            description: String? = " "

    ) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, rentalDate)
        contentValues.put(COL_4, returnDate)
        contentValues.put(COL_5, remainingDays)
        contentValues.put(COL_6, pageCount)
        contentValues.put(COL_7, thumbnail)
        contentValues.put(COL_8, favorite.toString())
        contentValues.put(COL_9,read.toString())
        contentValues.put(COL_10,authors)
        contentValues.put(COL_11,description)
        db.insert(TABLE_NAME, null, contentValues)
    }


    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    fun updateData(id: String, start_date: String, return_date: String, remainingDays: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_3, start_date)
        contentValues.put(COL_4, return_date)
        contentValues.put(COL_5, remainingDays)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
    }

    fun updateFavorite(
            id: String,
            favorite: Int
    ) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_8, favorite)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
    }
    fun updateRead(
            id: String,
            read: Int
    ) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_9, read)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
    }


    companion object {
        const val DATABASE_NAME = "stars.db"
        const val TABLE_NAME = "star_table"
        const val COL_1 = "ID"
        const val COL_2 = "TITLE"
        const val COL_3 = "RENTAL_DATE"
        const val COL_4 = "RETURN_DATE"
        const val COL_5 = "REMAINING_DAYS"
        const val COL_6 = "NUMBER_OF_PAGES"
        const val COL_7 = "THUMBNAIL"
        const val COL_8 = "FAVORITE"
        const val COL_9 = "READ"
        const val COL_10 = "PUBLISHER"
        const val COL_11 = "DESCRIPTION"
    }


}
