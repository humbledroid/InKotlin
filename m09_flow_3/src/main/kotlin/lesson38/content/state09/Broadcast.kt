package lesson38.content.state09

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class Broadcast(var progress: Int)

object TelevisionStation {
    private val _signal = MutableStateFlow(Broadcast(0))
    val signal: StateFlow<Broadcast> = _signal.asStateFlow()

    suspend fun broadcast() {
        repeat(10) {
            delay(50.milliseconds)
            _signal.update { broadcast ->
                broadcast.progress += 10
                println("Updating progress to ${broadcast.progress}")
                broadcast
            }
        }
    }
}

val scope = CoroutineScope(Dispatchers.Default)

suspend fun main() {
    val peter = scope.launch {
        TelevisionStation.signal.collect {
            println("🐼 Peter is watching: ${it.progress}% complete")
        }
    }

    TelevisionStation.broadcast()
}
