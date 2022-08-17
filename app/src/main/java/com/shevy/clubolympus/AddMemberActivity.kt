package com.shevy.clubolympus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shevy.clubolympus.databinding.ActivityAddMemberBinding
import com.shevy.clubolympus.databinding.ActivityMainBinding

class AddMemberActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}