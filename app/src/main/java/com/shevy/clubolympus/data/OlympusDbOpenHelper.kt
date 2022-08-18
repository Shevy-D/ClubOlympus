package com.shevy.clubolympus.data

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.shevy.clubolympus.data.ClubOlympusContract.MemberEntry

@SuppressLint("NewApi")
class OlympusDbOpenHelper(
    context: Context?
) : SQLiteOpenHelper(
    context,
    ClubOlympusContract.DATABASE_NAME,
    null,
    ClubOlympusContract.DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        //SQL - Structured Query Language (Структурированный язык запросов)
        val CREATE_MEMBERS_TABLE =
            "CREATE TABLE ${MemberEntry.TABLE_NAME} " +
                    "(${MemberEntry._ID} INTEGER PRIMARY KEY, " +
                    "${MemberEntry.COLUMN_FIRST_NAME} TEXT, " +
                    "${MemberEntry.COLUMN_LAST_NAME} TEXT, " +
                    "${MemberEntry.COLUMN_GENDER} INTEGER NOT NULL, " +
                    "${MemberEntry.COLUMN_SPORT} TEXT)"
        db?.execSQL(CREATE_MEMBERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ClubOlympusContract.DATABASE_NAME}")
        onCreate(db)
    }
}