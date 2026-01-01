package lesson50.content.state11

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.time.Duration.Companion.milliseconds

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
