package lesson30.content.state12

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import lesson30.content.util.fileOf
import kotlin.text.Charsets.UTF_8
import kotlin.time.Duration.Companion.seconds

suspend fun main() = coroutineScope {
    val job = launch {
        val file = fileOf("foods.txt").bufferedReader(UTF_8)
        try {
            flow { file.readLines().forEach { emit(it) } }
                .onEach { Thread.sleep(250) }
                .collectIndexed { index, value -> println("$index: $value") }
        } finally {
            println("Closing the file")
            file.close()
        }
    }

    delay(1.5.seconds)
    println("Cancelling")
    job.cancel()
}
