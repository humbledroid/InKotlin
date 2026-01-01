package lesson39.content.state10

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.milliseconds

fun broadcast() = flow {
    (0..100).forEach { progress ->
        emit("Episode 39 - $progress%")
        delay(100.milliseconds)
    }
}

val scope = CoroutineScope(Dispatchers.Default)

fun main() {
    val signal = broadcast().stateIn(scope, SharingStarted.Eagerly, "Please stand by...")

    while (readln().trim() != "exit") {
        println("❓ What's on? ${signal.value}")
    }
}
