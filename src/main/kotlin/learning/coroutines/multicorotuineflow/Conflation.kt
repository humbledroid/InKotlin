package learning.coroutines.multicorotuineflow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
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
        .conflate()
        .collect {
            delay(10.seconds)
            println("⚽ Current score: $it")
        }
}

/**
 * In here the values will be emitted, but as there is conflate now, the value will be swapped for latest values
 * and this emission will start from 0 to 100, but the collector will be able to collect only two
 * values, one is initial value which is 0, and the latest value that's in place would be the last value emitted by
 * by the emitted, which would be 99
 *
 * Score emitted: 0
 * ....
 * ....
 * Score emitted: 97
 * Score emitted: 98
 * Score emitted: 99
 * ⚽ Current score: 0
 * ⚽ Current score: 99
 */