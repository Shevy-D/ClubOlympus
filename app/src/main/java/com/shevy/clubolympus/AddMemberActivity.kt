package com.shevy.clubolympus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import com.shevy.clubolympus.databinding.ActivityAddMemberBinding
import com.shevy.clubolympus.databinding.ActivityMainBinding

class AddMemberActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMemberBinding

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var groupNameEditText: EditText
    private lateinit var genderSpinner: Spinner
    private var gender: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstNameEditText = binding.etFirstName
        lastNameEditText = binding.etLastName
        groupNameEditText = binding.etGroup
        genderSpinner = binding.spGender
    }
}