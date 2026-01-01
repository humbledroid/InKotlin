package lesson32.content.state05

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
        .flowOn(CoroutineName("George"))
        .onEach { log("onEach 2", it) }

    runBlocking(CoroutineName("Dennis")) {
        sports.collect { log("collect ", it) }
    }
}

suspend fun log(label: String, ball: String) {
    println("$label $ball ${currentCoroutineContext()[CoroutineName]}")
}
