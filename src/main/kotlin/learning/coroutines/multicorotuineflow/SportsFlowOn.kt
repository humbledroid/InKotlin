package learning.coroutines.multicorotuineflow

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking


fun main() {
    val sports = flow {
        listOf("⚽", "⚾", "🏀", "🏈").forEach {
            log("flow    ", it)
            emit(it)
        }
    }
        .onEach { log("onEach 1", it) }
        // up to here, everything thing will be tied to coroutine name "George"
        .flowOn(CoroutineName("George"))
        // also to do multiple coroutine here, we can supply another dispatchers
        // using .flowOn(Dispatchers.IO)
        // and down from here to collector everything will be tied to "Dennis" coroutine name
        .onEach { log("onEach 2", it) }

    runBlocking(CoroutineName("Dennis")) {
        sports.collect { log("collect ", it) }
    }
}

suspend fun log(label: String, ball: String) {
    println("$label $ball ${currentCoroutineContext()[CoroutineName]}")
}

/**
 *
 * fun main() {
 *     val sports = flow {
 *         listOf("⚽", "⚾", "🏀", "🏈").forEach {
 *             log("flow    ", it)
 *             emit(it)
 *         }
 *     }
 *         .onEach { log("onEach 1", it) }
 *         .flowOn(Dispatchers.IO)
 *         .onEach { log("onEach 2", it) }
 *
 *     runBlocking(Dispatchers.Default) {
 *         sports.collect { log("collect ", it) }
 *     }
 * }
 *
 * suspend fun log(label: String, ball: String) {
 *     println("$label $ball ${currentCoroutineContext()}")
 * }
 *
 */
