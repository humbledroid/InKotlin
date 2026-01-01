package lesson04.exercise

import kotlinx.coroutines.delay

/* ****************************************************************************
   Below is a suspending function. Call that suspending function from the
   `main()` function, using the `launch()` coroutine builder. Be sure that your
   solution compiles _and_ all three lines of the haiku are printed before the
   `main()` function finishes.
   ************************************************************************* */

fun main() {
    TODO("Add your solution here")
}

suspend fun haiku() {
    println("Kotlin flows like breeze")
    delay(1000)
    println("concise code with safety's grace")
    delay(1000)
    println("null fades into mist")
}
