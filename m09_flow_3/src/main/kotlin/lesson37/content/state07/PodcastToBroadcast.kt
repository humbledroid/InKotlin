package lesson37.content.state07

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

object TelevisionStation {
    private val _signal = MutableSharedFlow<String>()
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
    }

    val frank = scope.launch {
        delay(4.seconds)
        TelevisionStation.signal.collect { println("🐸 Frank is watching: $it") }
    }

    val jack = scope.launch {
        delay(3.seconds)
        repeat(10) {
            (TelevisionStation.signal as? MutableSharedFlow<String>)?.emit("⚠️ This show is being taken over! ⚠️")
            delay(500.milliseconds)
        }
    }

    TelevisionStation.broadcast()
}
