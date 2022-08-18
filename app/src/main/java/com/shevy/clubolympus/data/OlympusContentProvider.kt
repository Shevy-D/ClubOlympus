package com.shevy.clubolympus.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.shevy.clubolympus.data.ClubOlympusContract.MemberEntry

class OlympusContentProvider : ContentProvider() {
    private val members = 111
    private val membersId = 222

    lateinit var dbOpenHelper: OlympusDbOpenHelper
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS, members)
        addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS + "/#", membersId)
    }

    override fun onCreate(): Boolean {
        dbOpenHelper = OlympusDbOpenHelper(context)
            return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbOpenHelper.readableDatabase
        val cursor = when (uriMatcher.match(uri)) {
            members -> {
                db.query(
                    MemberEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            membersId -> {
                val selection = "${MemberEntry._ID}=?"
                val selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.query(
                    MemberEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            else -> {
                throw IllegalArgumentException("Can't query incorrect URI $uri")
            }
        }
        cursor.setNotificationUri(context?.contentResolver,uri)
        return cursor
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