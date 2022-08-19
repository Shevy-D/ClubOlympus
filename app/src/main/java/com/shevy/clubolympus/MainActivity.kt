package com.shevy.clubolympus

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.shevy.clubolympus.data.ClubOlympusContract.MemberEntry
import com.shevy.clubolympus.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),
LoaderManager.LoaderCallbacks<Cursor>{
    lateinit var binding: ActivityMainBinding

    lateinit var dataListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataListView = binding.dataListView

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
        val cursorAdapter = MemberCursorAdapter(this, cursor, false)
        dataListView.adapter = cursorAdapter

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}