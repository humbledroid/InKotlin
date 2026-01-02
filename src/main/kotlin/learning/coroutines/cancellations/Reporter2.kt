package learning.coroutines.cancellations

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileWriter
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking {
    val file = File(filePath)

    val job = launch {
        println("Let's write to the file")
        FileWriter(file, false).use { writer ->
            repeat(10) {
                delay(1.seconds)
                writer.write("This is line $it\n")
                println("✍️ Wrote line $it")
            }
        }
    }

    delay(2.5.seconds)
    println("⛔ Cancelling job")
    job.cancelAndJoin()

    println("🗒️ Final file contents")
    println("----------------------")
    println(file.readText())
}