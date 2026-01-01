package lesson30.content.state14

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.seconds

suspend fun main() = coroutineScope {
    val job = launch {
        val foodFlow = flowOf("🍔", "🍟", "🍪")
            .cancellable()
            .onCompletion { throwable ->
                when (throwable) {
                    null                     -> println("Finished normally")
                    is CancellationException -> println("Cancelled")
                    else                     -> println("Problem: ${throwable.message}")
                }
            }
            .onEach {
                println("$it is cooking")
                Thread.sleep(1_000)
            }

        foodFlow.collect { println("$it is ready to serve!") }
    }

    delay(1.5.seconds)
    println("Cancelling order")
    job.cancel()
}
