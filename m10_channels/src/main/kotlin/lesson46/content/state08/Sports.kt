package lesson46.content.state08

import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    channelFlow {
        launch {
            send("⚽")
            send("⚾")
            send("🏀")
            send("🏈")
        }
    }.collect {
        println("Let's play $it")
    }
}
