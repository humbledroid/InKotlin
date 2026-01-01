package lesson37.content.state11

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

object TelevisionStation {
    private val _signal = MutableSharedFlow<String>(
        replay = 3,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val signal: SharedFlow<String> = _signal.asSharedFlow()

    suspend fun broadcast() {
        (0..100 step 10).forEach { progress ->
            _signal.emit("Episode 37 - $progress%")
            delay(500.milliseconds)
        }
    }
}

val scope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    val larry = scope.launch {
        TelevisionStation.signal.collect { println("🦁 Larry is watching: $it") }
    }

    val peter = scope.launch {
        delay(2.5.seconds)
        TelevisionStation.signal.collect { println("🐼 Peter is watching: $it") }
        println("🐼 Peter is done watching.")
    }

    val frank = scope.launch {
        delay(4.seconds)
        TelevisionStation.signal.collect { println("🐸 Frank is watching: $it") }
    }

    TelevisionStation.broadcast()
}
