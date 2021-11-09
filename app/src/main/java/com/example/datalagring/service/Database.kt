package com.example.datalagring.service

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.datalagring.R
import com.example.datalagring.managers.DatabaseManager
import com.example.datalagring.managers.FileManager

class Database(context: Context) : DatabaseManager(context) {


	init {
		try {
			this.clear()
			val fileManager = FileManager(context as AppCompatActivity)
			val text = fileManager.readFileFromResFolder(R.raw.external)
			val elements = text.split("\n")
			val rows = elements.size - 2

			for(i in 0..rows) {
				val categories = elements[i].split(",")
				val movie = categories[0]
				val director = categories[1]
				val actors = ArrayList<String>()
				for (j in 2 until categories.size) {
					this.insert_actor_movie(categories[j], movie.toUpperCase())
				}
				this.insert_director_movie(director, movie.toUpperCase())
			}

		} catch (e: Exception) {
			e.printStackTrace()
		}
	}


	val allDirectors: ArrayList<String>
		get() = performQuery(TABLE_DIRECTOR, arrayOf(DIRECTOR_NAME))

	val allMovies: ArrayList<String>
		get() = performQuery(TABLE_MOVIE, arrayOf(ID, MOVIE_TITLE), null)

	val allActors: ArrayList<String>
		get() = performQuery(TABLE_ACTOR, arrayOf(ID, ACTOR_NAME))


	val allMoviesAndDirectors: ArrayList<String>
		get() {
			val select = arrayOf("$TABLE_MOVIE.$MOVIE_TITLE", "$TABLE_DIRECTOR.$DIRECTOR_NAME")
			val from = arrayOf(TABLE_DIRECTOR, TABLE_MOVIE, TABLE_DIRECTOR_MOVIE)
			val join = JOIN_DIRECTOR_MOVIE

			return performRawQuery(select, from, join)
		}

	val allMoviesAndActors: ArrayList<String>
		get() {
			val select = arrayOf("$TABLE_MOVIE.$MOVIE_TITLE", "$TABLE_ACTOR.$ACTOR_NAME")
			val from = arrayOf(TABLE_ACTOR, TABLE_MOVIE, TABLE_ACTOR_MOVIE)
			val join = JOIN_ACTOR_MOVIE

			return performRawQuery(select, from, join)
		}

	fun getMoviesByDirector(director: String): ArrayList<String> {
		val select = arrayOf("$TABLE_MOVIE.$MOVIE_TITLE")
		val from = arrayOf(TABLE_DIRECTOR, TABLE_MOVIE, TABLE_DIRECTOR_MOVIE)
		val join = JOIN_DIRECTOR_MOVIE
		val where = "$TABLE_DIRECTOR.$DIRECTOR_NAME='$director'"

		return performRawQuery(select, from, join, where)
	}

	fun getMoviesByActor(actor: String): ArrayList<String> {
		val select = arrayOf("$TABLE_MOVIE.$MOVIE_TITLE")
		val from = arrayOf(TABLE_ACTOR, TABLE_MOVIE, TABLE_ACTOR_MOVIE)
		val join = JOIN_ACTOR_MOVIE
		val where = "$TABLE_ACTOR.$ACTOR_NAME='$actor'"

		return performRawQuery(select, from, join, where)
	}

	fun getDirectorsByMovie(title: String): ArrayList<String> {
		val select = arrayOf("$TABLE_DIRECTOR.$DIRECTOR_NAME")
		val from = arrayOf(TABLE_DIRECTOR, TABLE_MOVIE, TABLE_DIRECTOR_MOVIE)
		val join = JOIN_DIRECTOR_MOVIE
		val where = "$TABLE_MOVIE.$MOVIE_TITLE='$title'"

		return performRawQuery(select, from, join, where)
	}

	fun getActorsByMovie(title: String): ArrayList<String> {
		val select = arrayOf("$TABLE_ACTOR.$ACTOR_NAME")
		val from = arrayOf(TABLE_ACTOR, TABLE_MOVIE, TABLE_ACTOR_MOVIE)
		val join = JOIN_ACTOR_MOVIE
		val where = "$TABLE_MOVIE.$MOVIE_TITLE='${title.toUpperCase()}'"

		return performRawQuery(select, from, join, where)
	}
}
