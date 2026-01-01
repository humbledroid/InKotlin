package lesson38.content.state11

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

object TelevisionStation {
    private val _topics = MutableStateFlow(emptyList<String>())
    val topics: StateFlow<List<String>> = _topics.asStateFlow()

    suspend fun broadcast() {
        _topics.update { it + "testing" }; delay(500.milliseconds)
        _topics.update { it + "development" }; delay(500.milliseconds)
        _topics.update { it + "delivery" }; delay(500.milliseconds)
        _topics.update { it + "code-review" }; delay(500.milliseconds)
        _topics.update { it + "tooling" }; delay(500.milliseconds)
    }
}

val scope = CoroutineScope(Dispatchers.Default)

suspend fun main() {
    val producer = scope.launch {
        TelevisionStation.topics.collect {
            println("🎙️ Topics covered in this episode: $it")
        }
    }

    TelevisionStation.broadcast()
}
