package com.example.datalagring.managers

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.*

/**
 * Just contains basic code snippets relevant for reading from/to different files
 */
class FileManager(private val activity: AppCompatActivity) {

	private val filename: String = "internal.txt"
	private val external_filename: String = "external.txt"

	private var dir: File = activity.filesDir
	private var file: File = File(dir, filename)

	private var externalDir: File? = activity.getExternalFilesDir(null)
	private var externalFile = File(externalDir, external_filename)

	private var databaseManager = DatabaseManager(activity.applicationContext)


	fun write(str: String) {
		PrintWriter(file).use { writer ->
			writer.println(str)
		}
	}

	fun readLine(): String? {
		BufferedReader(FileReader(file)).use { reader ->
			return reader.readLine()
		}
	}

	/**
	 * Open file: *res/raw/id.txt*
	 *
	 * @param fileId R.raw.filename
	 */
	fun readFileFromResFolder(fileId: Int): String {
		val content = StringBuffer("")
		try {
			val inputStream: InputStream = activity.resources.openRawResource(fileId)
			val reader = BufferedReader(InputStreamReader(inputStream)).use { reader ->
				var line = reader.readLine()
				while (line != null) {
					content.append(line)
					content.append("\n")
					line = reader.readLine()
				}

			}
		} catch (e: IOException) {
			e.printStackTrace()
		}
		return content.toString()
	}
}
