package com.shevy.clubolympus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.shevy.clubolympus.data.ClubOlympusContract.MemberEntry
import com.shevy.clubolympus.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var dataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataTextView = binding.dataTextView

        val floatingActionButton = binding.floatingActionButton
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddMemberActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        displayData()
    }

    private fun displayData() {
        val projection = arrayOf(
            MemberEntry._ID,
            MemberEntry.COLUMN_FIRST_NAME,
            MemberEntry.COLUMN_LAST_NAME,
            MemberEntry.COLUMN_GENDER,
            MemberEntry.COLUMN_SPORT
        )

        val cursor = contentResolver.query(MemberEntry.CONTENT_URI, projection, null, null, null)
        dataTextView.text = "All members\n\n"
        dataTextView.append(
            "${MemberEntry._ID} " +
                    "${MemberEntry.COLUMN_FIRST_NAME} " +
                    "${MemberEntry.COLUMN_LAST_NAME} " +
                    "${MemberEntry.COLUMN_GENDER} " +
                    MemberEntry.COLUMN_SPORT
        )

        val idIndex = cursor?.getColumnIndex(MemberEntry._ID)
        val idFirstName = cursor?.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME)
        val idLastName = cursor?.getColumnIndex(MemberEntry.COLUMN_LAST_NAME)
        val idGender = cursor?.getColumnIndex(MemberEntry.COLUMN_GENDER)
        val idSport = cursor?.getColumnIndex(MemberEntry.COLUMN_SPORT)

        while (cursor?.moveToNext() == true) {
            val currentId = idIndex?.let { cursor.getInt(it) }
            val currentFirstName = idFirstName?.let { cursor.getString(it) }
            val currentLastName = idLastName?.let { cursor.getString(it) }
            val currentGender = idGender?.let { cursor.getString(it) }
            val currentSport = idSport?.let { cursor.getString(it) }

            dataTextView.append("\n$currentId $currentFirstName $currentLastName $currentGender $currentSport")
        }

        cursor?.close()
    }
}