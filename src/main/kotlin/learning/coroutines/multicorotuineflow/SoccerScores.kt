package learning.coroutines.multicorotuineflow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() {
    val scores = flow {
        repeat(100) {
            emit(it)
            println("Score emitted: $it")
        }
    }.flowOn(Dispatchers.IO)

    runBlocking(Dispatchers.Default) {
        scores.collect {
            delay(10.seconds)
            println("⚽ Current score: $it")
        }
    }
}

/**
 * Here when running the program we will see an effect, called, buffering, as soon as 64 items are emitted, the emitter will be suspsned
 * further, unless, the buffer is emptied after 10 second of consumption, and one by one after every 10 second, the buffer
 * gets emptied by 1, the 1 more value will get emitted by the emitted
 */
