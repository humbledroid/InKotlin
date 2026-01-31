package learning.coroutines.multicorotuineflow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.coroutineContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking(Dispatchers.Default) {
    flow {
        repeat(100) {
            emit(it)
            println("Score emitted: $it")
            println(currentCoroutineContext())
        }
    }
        .buffer(5) // adding buffer with capacity will give the upstream emitter a buffer of 5, and this also splits the work on two different coroutines
        .collect {
            println(currentCoroutineContext()) // we can see the different coroutines processing upstream and collection
            delay(2.seconds)
            println("⚽ Current score: $it")
        }
}
