package org.izv.dam.psp.filescomposableviewmodelrepository.model.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

class FileRepository(private val context: Context) {
    val fileName = "content.txt"

    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }

    suspend fun readContent(): List<String> {
        return withContext(Dispatchers.IO) {
            delay(2000)
            val file = getFile()
            if (file.exists()) {
                file.readLines()
            } else {
                emptyList()
            }
        }
    }

    suspend fun writeLine(line: String) {
        return withContext(Dispatchers.IO) {
            delay(1000)
            val file = getFile()
            file.appendText(line + "\n")
        }
    }
}