package lesson07.content.state02

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking(CoroutineName("Run Blocking")) {
        println(coroutineContext)
        launch {
            println(coroutineContext)
        }
    }
}
