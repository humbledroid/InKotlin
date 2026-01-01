package lesson30.content.state15

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

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
}
