package com.shevy.clubolympus

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.shevy.clubolympus.data.ClubOlympusContract.MemberEntry
import com.shevy.clubolympus.databinding.ActivityAddMemberBinding

class AddMemberActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMemberBinding

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var sportEditText: EditText
    private lateinit var genderSpinner: Spinner

    private var gender: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val currentMemberUri = intent.data

        title = if (currentMemberUri == null) {
            "Add Member"
        } else {
            "Edit the Member"
        }

        firstNameEditText = binding.etFirstName
        lastNameEditText = binding.etLastName
        sportEditText = binding.etSport
        genderSpinner = binding.spGender

        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_gender,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = spinnerAdapter
        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedGender: String = p0?.getItemAtPosition(p2) as String
                if (!TextUtils.isEmpty(selectedGender)) {
                    gender = when (selectedGender) {
                        "Male" -> MemberEntry.GENDER_MALE
                        "Female" -> MemberEntry.GENDER_FEMALE
                        else -> MemberEntry.GENDER_UNKNOWN
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                gender = 0
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_member_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_member -> {
                insertMember()
                return true
            }
            R.id.delete_member -> return true
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertMember() {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val sport = binding.etSport.text.toString().trim()

        val contentValues = ContentValues().apply {
            put(MemberEntry.COLUMN_FIRST_NAME, firstName)
            put(MemberEntry.COLUMN_LAST_NAME, lastName)
            put(MemberEntry.COLUMN_GENDER, gender)
            put(MemberEntry.COLUMN_SPORT, sport)
        }

        val uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues)

        if (uri == null) {
            Toast.makeText(this, "Insertion of data in the table failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
        }
    }
}