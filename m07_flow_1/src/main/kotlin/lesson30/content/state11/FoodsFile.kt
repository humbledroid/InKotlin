package lesson30.content.state11

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
        flow {
            val file = fileOf("foods.txt").bufferedReader(UTF_8)
            try {
                file.readLines().forEach { emit(it) }
            } finally {
                println("Closing the file")
                file.close()
            }
        }
            .onEach { Thread.sleep(250) }
            .collectIndexed { index, value -> println("$index: $value") }
    }

    delay(1.5.seconds)
    println("Cancelling")
    job.cancel()
}
