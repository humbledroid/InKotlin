package lesson31.content.state08

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlinx.coroutines.stream.consumeAsFlow
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.time.Duration.Companion.seconds

suspend fun main() = coroutineScope {
    val job = launch {
        Files.lines(Paths.get("foods.txt"))
            .consumeAsFlow()
            .onEach {
                if ("🍊" in it) {
                    throw OutOfStockException("🍊")
                }
            }
            .cancellable()
            .onEach { Thread.sleep(250) }
            .retry(3) { it is OutOfStockException }
            .collectIndexed { index, value -> println("$index: $value") }
    }

    delay(1.5.seconds)
    println("Cancelling")
    job.cancel()
}

data class OutOfStockException(val label: String) : Exception("$label is out of stock.")
