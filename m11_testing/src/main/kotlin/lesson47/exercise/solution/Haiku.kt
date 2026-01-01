package lesson47.exercise.solution

import kotlinx.coroutines.delay

/* ****************************************************************************

   Below is a suspending function from an earlier exercise, which prints a
   haiku. The `println()` function has been hijacked to increment the
   `callCount` property whenever it's called.

   Write a test to ensure that the haiku includes exactly three lines (that is,
   that `println()` has been called exactly three times).

   ************************************************************************* */

/* ****************************************************************************
   ⚠️ The provided solution for this exercise can be found in the test source
   set here: /test/kotlin/lesson47/exercise/solution/HaikuTest.kt.
   ************************************************************************* */

suspend fun haiku() {
    println("Kotlin flows like breeze")
    delay(1000)
    println("concise code with safety's grace")
    delay(1000)
    println("null fades into mist")
}

var callCount = 0

fun println(message: String) {
    callCount += 1
    kotlin.io.println(message)
}
