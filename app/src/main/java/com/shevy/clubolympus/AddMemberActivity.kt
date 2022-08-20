package com.shevy.clubolympus

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.shevy.clubolympus.data.ClubOlympusContract.MemberEntry
import com.shevy.clubolympus.databinding.ActivityAddMemberBinding


class AddMemberActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<Cursor> {
    lateinit var binding: ActivityAddMemberBinding

    private val EDIT_MEMBER_LOADER = 123

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var sportEditText: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var currentMemberUri: Uri
    private var currUri: Uri? = null

    private var gender: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currUri = intent.data
        if (currUri == null) {
            invalidateOptionsMenu()
            title = "Add a member"
        } else {
            currentMemberUri = currUri ?: MemberEntry.CONTENT_URI
            title = "Edit the member"
            LoaderManager.getInstance(this).initLoader(EDIT_MEMBER_LOADER, null, this)
        }
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
                saveMember()
                return true
            }
            R.id.delete_member -> {
                showDeleteMemberDialog()
                return true
            }
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveMember() {
        val firstName = firstNameEditText.text.toString().trim { it <= ' ' }
        val lastName = lastNameEditText.text.toString().trim { it <= ' ' }
        val sport = sportEditText.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Input the first name", Toast.LENGTH_LONG).show()
            return
        } else if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Input the last name", Toast.LENGTH_LONG).show()
            return
        } else if (TextUtils.isEmpty(sport)) {
            Toast.makeText(this, "Input sport", Toast.LENGTH_LONG).show()
            return
        } else if (gender == MemberEntry.GENDER_UNKNOWN) {
            Toast.makeText(this, "Choose the gender", Toast.LENGTH_LONG).show()
            return
        }
        val contentValues = ContentValues()
        contentValues.put(MemberEntry.COLUMN_FIRST_NAME, firstName)
        contentValues.put(MemberEntry.COLUMN_LAST_NAME, lastName)
        contentValues.put(MemberEntry.COLUMN_SPORT, sport)
        contentValues.put(MemberEntry.COLUMN_GENDER, gender)
        if (currUri == null) {
            val contentResolver = contentResolver
            val uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues)
            if (uri == null) {
                Toast.makeText(
                    this, "Insertion of data in the table failed",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show()
            }
        } else {
            val rowsChanged = contentResolver.update(
                currentMemberUri, contentValues,
                null, null
            )
            if (rowsChanged == 0) {
                Toast.makeText(
                    this, "Saving of the data in the table failed",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this, "Member updated", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(
            MemberEntry._ID,
            MemberEntry.COLUMN_FIRST_NAME,
            MemberEntry.COLUMN_LAST_NAME,
            MemberEntry.COLUMN_GENDER,
            MemberEntry.COLUMN_SPORT
        )
        return CursorLoader(this, currentMemberUri, projection, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor?>, cursor: Cursor) {
        if (cursor.moveToFirst()) {
            val firstNameColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME)
            val lastNameColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_LAST_NAME)
            val genderColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_GENDER)
            val sportColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_SPORT)
            val firstName = cursor.getString(firstNameColumnIndex)
            val lastName = cursor.getString(lastNameColumnIndex)
            val gender = cursor.getInt(genderColumnIndex)
            val sport = cursor.getString(sportColumnIndex)
            firstNameEditText.setText(firstName)
            lastNameEditText.setText(lastName)
            sportEditText.setText(sport)
            when (gender) {
                MemberEntry.GENDER_MALE -> genderSpinner.setSelection(1)
                MemberEntry.GENDER_FEMALE -> genderSpinner.setSelection(2)
                MemberEntry.GENDER_UNKNOWN -> genderSpinner.setSelection(0)
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

    private fun showDeleteMemberDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want delete the Member")
        builder.setPositiveButton(
            "Delete"
        ) { dialog, which -> deleteMember() }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which ->
            dialog?.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }


    private fun deleteMember() {
        if (currUri != null) {
            val rowsDeleted = contentResolver.delete(currentMemberUri, null, null)
            if (rowsDeleted == 0) {
                Toast.makeText(
                    this,
                    "Deleting of data from the table failed", Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Member is deleted", Toast.LENGTH_LONG
                ).show()
            }
            finish()
        }
    }
}