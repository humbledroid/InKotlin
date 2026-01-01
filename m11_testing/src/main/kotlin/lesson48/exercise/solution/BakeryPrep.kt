@file:OptIn(ExperimentalAtomicApi::class)

package lesson48.exercise.solution

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.plusAssign
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

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

suspend fun main() {
    coroutineScope {
        launch { doKitchenPrep() }
        launch { doCustomerPrep() }
        launch { cleanUp() }
    }

    println("🥐 The bakery is ready to open!")
}

suspend fun doKitchenPrep() {
    coroutineScope {
        launch {
            delay(2.seconds)
            println("🍞 Dough is ready.")
        }
        launch {
            delay(500.milliseconds)
            println("🧁 Frosting is mixed.")
        }
        launch {
            delay(3.seconds)
            println("🥐 Croissants are baked.")
        }
    }
    println("✅ Kitchen prep is complete")
}

suspend fun doCustomerPrep() {
    coroutineScope {
        launch {
            delay(1.seconds)
            println("☕ Coffee is ready.")
        }
        launch {
            delay(800.milliseconds)
            println("📋 Menu is ready.")
        }
    }
    println("✅ Customer prep is complete")
}

suspend fun cleanUp() {
    coroutineScope {
        launch {
            delay(1.2.seconds)
            println("🧽 Counters are clean.")
        }
        launch {
            delay(1.seconds)
            println("🧼 Dishes are clean.")
        }
    }
    println("✅ Clean-up is complete")
}
