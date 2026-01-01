package lesson32.content.state02

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    flow {
        withContext(CoroutineName("Sports")) {
            emit("⚽")
            emit("⚾")
            emit("🏀")
            emit("🏈")
        }
    }.collect {
        println("Let's play $it")
    }
}
