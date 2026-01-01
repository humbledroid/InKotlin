package lesson38.content.state02

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Duration.Companion.milliseconds

object TelevisionStation {
    private val _signal = MutableStateFlow<String>("Please stand by...")
    val signal: StateFlow<String> = _signal.asStateFlow()

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
