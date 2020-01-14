package com.example.projektzd.database

import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.fragment.app.FragmentActivity


class DatabaseHelper(context: FragmentActivity?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME ($COL_1 TEXT PRIMARY KEY,$COL_2 TEXT,$COL_3 TEXT,$COL_4 TEXT," +
                    "$COL_5 INTEGER,$COL_6 INTEGER,$COL_7 TEXT)"
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

    fun insertData(
        id: String,
        name: String,
        rentalDate: String,
        returnDate: String,
        remainingDays: Int,
        pageCount: Int,
        thumbnail: String? = " "
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
        db.insert(TABLE_NAME, null, contentValues)

    }

    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "KSIAZKA = ?", arrayOf(id))
    }

    fun updateData(id: String, start_date: String, return_date: String, remainingDays: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_3, start_date)
        contentValues.put(COL_4, return_date)
        contentValues.put(COL_5, remainingDays)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
    }

    companion object {
        const val DATABASE_NAME = "stars.db"
        const val TABLE_NAME = "star_table"
        const val COL_1 = "ID"
        const val COL_2 = "KSIAZKA"
        const val COL_3 = "DATA_WYP"
        const val COL_4 = "DATA_ODD"
        const val COL_5 = "POZOSTALE_DNI"
        const val COL_6 = "LICZBA_STRON"
        const val COL_7 = "THUMBNAIL"
    }
}