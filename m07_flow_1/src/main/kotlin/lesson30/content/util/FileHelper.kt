package lesson30.content.util

import java.io.File
import java.io.FileNotFoundException
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths

fun fileOf(filename: String): File = File(uriOf(filename))
fun pathOf(filename: String): Path = Paths.get(uriOf(filename))

private fun uriOf(filename: String): URI =
    Thread.currentThread().contextClassLoader.getResource(filename)?.toURI()
        ?: throw FileNotFoundException("File $filename was not found.")
