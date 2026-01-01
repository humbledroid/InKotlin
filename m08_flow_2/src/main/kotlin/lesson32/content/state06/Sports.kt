package lesson32.content.state06

import kotlinx.coroutines.Dispatchers
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
        .flowOn(Dispatchers.IO)
        .onEach { log("onEach 2", it) }

    runBlocking(Dispatchers.Default) {
        sports.collect { log("collect ", it) }
    }
}

suspend fun log(label: String, ball: String) {
    println("$label $ball ${currentCoroutineContext()}")
}
