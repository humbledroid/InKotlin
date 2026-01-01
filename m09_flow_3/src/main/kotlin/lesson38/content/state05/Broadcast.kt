package lesson38.content.state05

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object TelevisionStation {
    private val _progress = MutableStateFlow(0)
    val progress: StateFlow<Int> = _progress.asStateFlow()

    suspend fun broadcast() = coroutineScope {
        repeat(100_000) {
            launch {
                _progress.value++
            }
        }
    }
}

val scope = CoroutineScope(Dispatchers.Default)

suspend fun main() {
    val larry = scope.launch {
        TelevisionStation.progress.collect {
            println("🦁 Larry is watching: ${it.toDouble() / 1_000.0}% complete")
        }
    }

    TelevisionStation.broadcast()
}
