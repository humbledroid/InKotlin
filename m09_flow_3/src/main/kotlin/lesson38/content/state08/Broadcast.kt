package lesson38.content.state08

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

object TelevisionStation {
    private val _shared = MutableSharedFlow<Int>()
    val shared: SharedFlow<Int> = _shared.asSharedFlow()

    private val _state = MutableStateFlow(0)
    val state: StateFlow<Int> = _state.asStateFlow()

    suspend fun broadcast() {
        repeat(100) {
            delay(500.milliseconds)
            _shared.emit(it / 10)
            _state.emit(it / 10)
        }
    }
}

val scope = CoroutineScope(Dispatchers.Default)

suspend fun main() {
    val larry = scope.launch {
        TelevisionStation.shared.collect {
            println("🦁 Larry is watching: ${it}% complete")
        }
    }

    val peter = scope.launch {
        TelevisionStation.state.collect {
            println("🐼 Peter is watching: ${it}% complete")
        }
    }

    TelevisionStation.broadcast()
}
