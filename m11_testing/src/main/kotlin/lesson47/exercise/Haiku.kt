package lesson47.exercise

import kotlinx.coroutines.delay

/* ****************************************************************************

   Below is a suspending function from an earlier exercise, which prints a
   haiku. The `println()` function has been hijacked to increment the
   `callCount` property whenever it's called.

   Write a test to ensure that the haiku includes exactly three lines (that is,
   that `println()` has been called exactly three times).

   Be sure to add your test to the test _source set_. In other words, write
   your test code in /test/kotlin/lesson47/exercise/HaikuTest.kt.

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
