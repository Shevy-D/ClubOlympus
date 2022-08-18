package com.shevy.clubolympus.data

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class OlympusContentProvider : ContentProvider() {
    lateinit var dbOpenHelper: OlympusDbOpenHelper

    override fun onCreate(): Boolean {
        dbOpenHelper = OlympusDbOpenHelper(context)
            return false
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }

    override fun getType(p0: Uri): String? {
        return null
    }
}

//URI: Unified Resource Identifier - Единый идентификатор ресурса
//content://com.shevy.clubolympus.members
//URL: Unified Resource Locator
//http://google.com
//content://com.shevy.clubolympus.members/id
// content://com.android.contacts/contacts
// content://com.android.calendar/events
// content://user_dictionary/words
// content:// - scheme - схема
// com.android.calendar - content authority - значение
// events - type of data