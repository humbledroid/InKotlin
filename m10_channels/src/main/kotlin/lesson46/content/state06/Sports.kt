package lesson46.content.state06

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    flow {
        launch {
            emit("⚽")
            emit("⚾")
            emit("🏀")
            emit("🏈")
        }
    }.collect {
        println("Let's play $it")
    }
}
