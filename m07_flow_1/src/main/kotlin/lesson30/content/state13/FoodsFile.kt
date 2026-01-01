package lesson30.content.state13

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.stream.consumeAsFlow
import lesson30.content.util.pathOf
import java.nio.file.Files
import kotlin.time.Duration.Companion.seconds

suspend fun main() = coroutineScope {
    val job = launch {
        Files.lines(pathOf("foods.txt"))
            .consumeAsFlow()
            .cancellable()
            .onEach { Thread.sleep(250) }
            .collectIndexed { index, value -> println("$index: $value") }
    }

    delay(1.5.seconds)
    println("Cancelling")
    job.cancel()
}
