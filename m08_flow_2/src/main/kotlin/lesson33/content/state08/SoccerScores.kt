package lesson33.content.state08

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking(Dispatchers.Default) {
    flow {
        repeat(100) {
            emit(it)
            println("Score emitted: $it")
        }
    }
        .buffer(0, BufferOverflow.DROP_LATEST)
        .collect {
            delay(10.seconds)
            println("⚽ Current score: $it")
        }
}
