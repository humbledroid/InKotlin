package lesson16.exercise.solution

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Update your solution for Lesson 15's exercise so that each group of tasks is
   in its own suspending function.

   suspend fun doKitchenPrep() { ... }
   suspend fun doCustomerPrep() { ... }
   suspend fun cleanUp() { ... }

   Remember:
   - All the tasks should be worked in parallel.
   - Show the message when each group of tasks is complete.
   - Show a message when the bakery is ready to open, after all tasks have
     been completed.

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
