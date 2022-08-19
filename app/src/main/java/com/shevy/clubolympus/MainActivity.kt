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

        val idColumnIndex = cursor?.getColumnIndex(MemberEntry._ID)
        val firstNameColumnIndex = cursor?.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME)
        val lastNameColumnIndex = cursor?.getColumnIndex(MemberEntry.COLUMN_LAST_NAME)
        val genderColumnIndex = cursor?.getColumnIndex(MemberEntry.COLUMN_GENDER)
        val sportColumnIndex = cursor?.getColumnIndex(MemberEntry.COLUMN_SPORT)

        while (cursor?.moveToNext() == true) {
            val currentId = idColumnIndex?.let { cursor.getInt(it) }
            val currentFirstName = firstNameColumnIndex?.let { cursor.getString(it) }
            val currentLastName = lastNameColumnIndex?.let { cursor.getString(it) }
            val currentGender = genderColumnIndex?.let { cursor.getString(it) }
            val currentSport = sportColumnIndex?.let { cursor.getString(it) }

            dataTextView.append("\n$currentId $currentFirstName $currentLastName $currentGender $currentSport")
        }

        cursor?.close()
    }
}