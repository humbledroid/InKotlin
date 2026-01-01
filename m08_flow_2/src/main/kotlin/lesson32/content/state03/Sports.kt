package lesson32.content.state03

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

suspend fun main() {
    val sports = flow {
        emit("⚽")
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
