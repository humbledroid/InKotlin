package lesson33.content.state05

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking(Dispatchers.Default) {
    flow {
        repeat(100) {
            emit(it)
            println("Score emitted: $it")
        }
    }
        .buffer(5)
        .onEach { println("onEach has value: $it") }
        .flowOn(Dispatchers.IO)
        .collect {
            delay(10.seconds)
            println("⚽ Current score: $it")
        }
}
