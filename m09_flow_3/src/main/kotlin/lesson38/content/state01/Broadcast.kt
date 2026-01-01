package lesson38.content.state01

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.time.Duration.Companion.milliseconds

object TelevisionStation {
    private val _signal = MutableSharedFlow<String>()
    val signal: SharedFlow<String> = _signal.asSharedFlow()

    suspend fun broadcast() {
        (0..100 step 10).forEach { progress ->
            _signal.emit("Episode 38 - $progress%")
            delay(500.milliseconds)
        }
    }
}

val scope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    val larry = scope.launch {
        TelevisionStation.signal.collect { println("🦁 Larry is watching: $it") }
    }

    TelevisionStation.broadcast()
}
