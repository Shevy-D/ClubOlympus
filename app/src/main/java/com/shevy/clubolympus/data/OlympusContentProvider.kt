package com.shevy.clubolympus.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
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
        return true
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
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val firstName = values!!.getAsString(MemberEntry.COLUMN_FIRST_NAME)
            ?: throw java.lang.IllegalArgumentException("You have to input first name")

        val lastName = values.getAsString(MemberEntry.COLUMN_LAST_NAME)
            ?: throw java.lang.IllegalArgumentException("You have to input last name")

        val gender = values.getAsInteger(MemberEntry.COLUMN_GENDER)
        require(!(gender == null || !(gender == MemberEntry.GENDER_UNKNOWN || gender == MemberEntry.GENDER_MALE || gender == MemberEntry.GENDER_FEMALE))) { "You have to input correct gender " }

        val sport = values.getAsString(MemberEntry.COLUMN_SPORT)
            ?: throw java.lang.IllegalArgumentException("You have to input sport")

        val db = dbOpenHelper.writableDatabase
        when (uriMatcher.match(uri)) {
            members -> {
                val id = db.insert(MemberEntry.TABLE_NAME, null, values)
                if (id == (-1).toLong()) {
                    Log.e("insertMethod", "Insertion of data in the table failed for $uri")
                    return null
                }
                context?.contentResolver?.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
            else -> {
                throw IllegalArgumentException("Insertion of data in the table failed for $uri")
            }
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        if (values != null) {
            if (values.containsKey(MemberEntry.COLUMN_FIRST_NAME)) {
                val firstName = values.getAsString(MemberEntry.COLUMN_FIRST_NAME)
                    ?: throw java.lang.IllegalArgumentException("You have to input first name")
            }
        }

        if (values != null) {
            if (values.containsKey(MemberEntry.COLUMN_LAST_NAME)) {
                val lastName = values.getAsString(MemberEntry.COLUMN_LAST_NAME)
                    ?: throw java.lang.IllegalArgumentException("You have to input last name")
            }
        }

        if (values != null) {
            if (values.containsKey(MemberEntry.COLUMN_GENDER)) {
                val gender = values.getAsInteger(MemberEntry.COLUMN_GENDER)
                require(!(gender == null || !(gender == MemberEntry.GENDER_UNKNOWN || gender == MemberEntry.GENDER_MALE || gender == MemberEntry.GENDER_FEMALE))) { "You have to input correct gender " }
            }
        }

        if (values != null) {
            if (values.containsKey(MemberEntry.COLUMN_SPORT)) {
                val sport = values.getAsString(MemberEntry.COLUMN_SPORT)
                    ?: throw java.lang.IllegalArgumentException("You have to input sport")
            }
        }

        val db = dbOpenHelper.writableDatabase
        val count = when (uriMatcher.match(uri)) {
            members -> {
                db.update(
                    MemberEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
                )
            }
            membersId -> {
                val selection = "${MemberEntry._ID}=?"
                val selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.update(
                    MemberEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
                )
            }
            else -> {
                throw IllegalArgumentException("Can't update URI $uri")
            }
        }
        if (count != 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper.writableDatabase
        val count = when (uriMatcher.match(uri)) {
            members -> {
                db.delete(
                    MemberEntry.TABLE_NAME,
                    selection,
                    selectionArgs
                )
            }
            membersId -> {
                val selection = "${MemberEntry._ID}=?"
                val selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.delete(
                    MemberEntry.TABLE_NAME,
                    selection,
                    selectionArgs
                )
            }
            else -> {
                throw IllegalArgumentException("Can't delete URI $uri")
            }
        }
        if (count != 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return count
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            members -> {
                MemberEntry.CONTENT_MULTIPLE_ITEMS
            }
            membersId -> {
                MemberEntry.CONTENT_SINGLE_ITEM
            }
            else -> {
                throw IllegalArgumentException("Unknown URI $uri")
            }
        }
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