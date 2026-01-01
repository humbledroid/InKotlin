package lesson39.content.state09

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

object TelevisionStation {
    private val _signal = MutableStateFlow<String>("Please stand by...")
    val signal: StateFlow<String> = _signal.asStateFlow()

    suspend fun broadcast() {
        (0..100).forEach { progress ->
            _signal.emit("Episode 39 - $progress%")
            delay(100.milliseconds)
        }
    }
}

val scope = CoroutineScope(Dispatchers.Default)

fun main() {
    scope.launch { TelevisionStation.broadcast() }

    while (readln().trim() != "exit") {
        println("❓ What's on? ${TelevisionStation.signal.value}")
    }
}
