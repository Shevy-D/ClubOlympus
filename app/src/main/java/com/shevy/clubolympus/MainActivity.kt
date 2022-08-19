package com.shevy.clubolympus

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.shevy.clubolympus.data.ClubOlympusContract.MemberEntry
import com.shevy.clubolympus.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<Cursor> {
    lateinit var binding: ActivityMainBinding
    private val MEMBER_LOADER = 123
    lateinit var memberCursorAdapter: MemberCursorAdapter

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

        memberCursorAdapter = MemberCursorAdapter(this, null, false)
        dataListView.adapter = memberCursorAdapter

        supportLoaderManager.initLoader(MEMBER_LOADER, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(
            MemberEntry._ID,
            MemberEntry.COLUMN_FIRST_NAME,
            MemberEntry.COLUMN_LAST_NAME,
            MemberEntry.COLUMN_SPORT
        )

        return CursorLoader(this, MemberEntry.CONTENT_URI, projection, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        memberCursorAdapter.swapCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        memberCursorAdapter.swapCursor(null)
    }
}