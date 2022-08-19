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

class MemberCursorAdapter(context: Context?, c: Cursor?) :
    CursorAdapter(context, c, 0) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.member_item, parent, false)
    }

    @SuppressLint("Range")
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val tvItemName = view?.findViewById(R.id.tvItemName) as TextView
        val tvItemGender = view.findViewById(R.id.tvItemGender) as TextView
        val tvItemSportGroup = view.findViewById(R.id.tvItemSportGroup) as TextView
        if (cursor != null && context != null) {
            tvItemName.text = cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME))
            tvItemName.append(" ")
            tvItemName.append(cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_LAST_NAME)))
            tvItemGender.text = when (cursor.getInt(cursor.getColumnIndex(MemberEntry.COLUMN_GENDER))) {
                MemberEntry.GENDER_MALE -> context.getString(R.string.male)
                MemberEntry.GENDER_FEMALE -> context.getString(R.string.female)
                else -> context.getString(R.string.non_gender)
            }
            tvItemSportGroup.text = cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_SPORT_GROUP))
        }

    }
}