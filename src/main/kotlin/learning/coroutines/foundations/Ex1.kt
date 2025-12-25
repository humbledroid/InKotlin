package learning.coroutines.foundations

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch {
            haiku()
        }
    }
}

suspend fun haiku() {
    println("Kotlin flows like breeze")
    delay(1000)
    println("concise code with safety's grace")
    delay(1000)
    println("null fades into mist")
}