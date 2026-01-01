package lesson04.exercise.solution

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/* ****************************************************************************
   Below is a suspending function. Call that suspending function from the
   `main()` function, using the `launch()` coroutine builder. Be sure that your
   solution compiles _and_ that all three lines of the haiku are printed before
   the `main()` function finishes.
   ************************************************************************* */

fun main() {
    runBlocking {
        launch { haiku() }
    }
}

suspend fun haiku() {
    println("Kotlin flows like breeze")
    delay(1000)
    println("concise code with safety's grace")
    delay(1000)
    println("null fades into mist")
}
