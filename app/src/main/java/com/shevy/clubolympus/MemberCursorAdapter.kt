package com.shevy.clubolympus

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.shevy.clubolympus.data.ClubOlympusContract.MemberEntry

class MemberCursorAdapter(context: Context?, c: Cursor?, autoRequery: Boolean) :
    CursorAdapter(context, c, autoRequery) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.member_item, parent, false)
    }

    @SuppressLint("Range")
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val firstNameTextView = view?.findViewById(R.id.firstNameTextView) as TextView
        val lastNameTextView = view.findViewById(R.id.lastNameTextView) as TextView
        val sportTextView = view.findViewById(R.id.sportNameTextView) as TextView

        if (cursor != null && context != null) {
            firstNameTextView.text =
                cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME))
            lastNameTextView.text =
                cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_LAST_NAME))
            sportTextView.text = cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_SPORT))
        }
    }
}