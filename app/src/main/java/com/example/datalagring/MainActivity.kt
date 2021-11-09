package com.example.datalagring

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.datalagring.databinding.MinLayoutBinding
import com.example.datalagring.managers.FileManager
import com.example.datalagring.managers.MyPreferenceManager
import com.example.datalagring.service.Database
import java.io.File
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var db: Database
    private lateinit var minLayout: MinLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        minLayout = MinLayoutBinding.inflate(layoutInflater)
        setContentView(minLayout.root)

        db = Database(this)

        MyPreferenceManager(this).updateBackgroundColor()

        initialize_data()

    }

    private fun initialize_data() {
        val fileManager = FileManager(this)
        val text = fileManager.readFileFromResFolder(R.raw.external)
        fileManager.write(text)
        db = Database(this)
    }

    private fun showResults(list: ArrayList<String>) {
        val res = StringBuffer("")
        for (s in list) {
            res.append("$s\n")
        }
        minLayout.result.text = res
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        menu.add(0, 1, 0, "All directors")
        menu.add(0, 2, 0, "All movies")
        menu.add(0, 3, 0, "All actors")
        menu.add(0, 4, 0, "All movies and directors")
        menu.add(0, 5, 0, "All movies and actors")
        menu.add(0, 6, 0, "Movies by Christopher Nolan")
        menu.add(0, 7, 0, "Actors in \"The Lord of The Rings Trilogy\"")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startActivity(Intent("com.example.datalagring.SettingsActivity"))
            1             -> showResults(db.allDirectors)
            2             -> showResults(db.allMovies)
            3             -> showResults(db.allActors)
            4             -> showResults(db.allMoviesAndDirectors)
            5             -> showResults(db.allMoviesAndActors)
            6             -> showResults(db.getMoviesByDirector("Christopher Nolan"))
            7             -> showResults(db.getActorsByMovie("The Lord of The Rings Trilogy"))
            else          -> return false
        }
        return super.onOptionsItemSelected(item)
    }
}