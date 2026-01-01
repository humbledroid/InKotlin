package lesson37.content.state03

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

object TelevisionStation {
    val signal = MutableSharedFlow<String>()

    suspend fun broadcast() {
        (0..100 step 10).forEach { progress ->
            signal.emit("Episode 37 - $progress%")
            delay(500.milliseconds)
        }
    }
}

fun main() = runBlocking {
    val larry = launch {
        TelevisionStation.signal.collect { println("🦁 Larry is watching: $it") }
    }

    val peter = launch {
        delay(2.5.seconds)
        TelevisionStation.signal.collect { println("🐼 Peter is watching: $it") }
    }

    val frank = launch {
        delay(4.seconds)
        TelevisionStation.signal.collect { println("🐸 Frank is watching: $it") }
    }

    TelevisionStation.broadcast()
}
