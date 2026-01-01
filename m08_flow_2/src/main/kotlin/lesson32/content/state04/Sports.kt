package lesson32.content.state04

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

suspend fun main() {
    val sports = flow {
        withContext(Dispatchers.IO) { emit("⚽") }
        emit("⚾")
        emit("🏀")
        emit("🏈")
    }

    withContext(Dispatchers.Default) {
        sports.collect {
            println("Let's play $it")
        }
    }
}
