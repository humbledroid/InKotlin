package learning.coroutines.structured

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking<Unit>{
    val scope = this
    scope.launch {
        delay(2.seconds)
        println("Hello World")
    }
    scope.launch {
        delay(2.seconds)
        println("Hello World 2")
    }
    scope.launch {
        delay(2.seconds)
        println("Hello World 3")
    }
}