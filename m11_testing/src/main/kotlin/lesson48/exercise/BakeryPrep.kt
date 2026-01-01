@file:OptIn(ExperimentalAtomicApi::class)

package lesson48.exercise

import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.plusAssign

/* ****************************************************************************

   Go back to your solution for Lesson 16, copy it, and paste it below.

   Then, write a test for it, asserting the `callCount` at each point when a
   new message is printed (e.g., at 500ms, then at 800ms, then at 1000ms, and
   so on).

   ************************************************************************* */

val callCount = AtomicInt(0)

fun println(message: String) {
    callCount += 1
    kotlin.io.println(message)
}

/* ****************************************************************************

   Paste your solution from Lesson 16 below.

   If your IDE rewires the `println()` calls to use fully-qualified package
   names when you paste it in, just remove them so that it uses the `println()`
   function above.

   e.g., if you get `exercises.lesson48.solution.println("📋 Menu is ready.")`,
   just change it to `println("📋 Menu is ready.")`

   ************************************************************************* */
