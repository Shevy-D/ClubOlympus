package com.shevy.clubolympus.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

object ClubOlympusContract {

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "olympus"
    const val SCHEME = "content://"
    const val AUTHORITY = "com.shevy.clubolympus"
    const val PATH_MEMBERS = "members"
    val BASE_CONTENT_URI: Uri = Uri.parse(SCHEME + AUTHORITY)

    object MemberEntry : BaseColumns {
        const val TABLE_NAME = "members"
        const val _ID = BaseColumns._ID
        const val COLUMN_FIRST_NAME = "firstName"
        const val COLUMN_LAST_NAME = "lastName"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_SPORT = "sport"
        const val COLUMN_SPORT_GROUP = "sportGroup"

        const val GENDER_UNKNOWN = 0
        const val GENDER_MALE = 1
        const val GENDER_FEMALE = 2

        val CONTENT_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS)
        const val CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE +
                "/" + AUTHORITY + "/" + PATH_MEMBERS
        const val CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                "/" + AUTHORITY + "/" + PATH_MEMBERS
    }
}