package learning.coroutines.structured

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking(Dispatchers.Default) {
        println("Started")
        println("Scope is $this")
        doKitchenPrep()
        doCustomerPrep()
        doCleanup()
    }

    println("🥐 The bakery is ready to open!")
}

fun CoroutineScope.doCleanup(){
    launch {
        launch {
            delay(1.2.seconds)
            println("🧽 Counters are clean.")
        }
        launch {
            delay(1.seconds)
            println("🧼 Dishes are clean.")
        }
    }.invokeOnCompletion { println("✅ Clean-up is complete") }
}

fun CoroutineScope.doCustomerPrep() {
    launch {
        launch {
            delay(1.seconds)
            println("☕ Coffee is ready.")
        }
        launch {
            delay(800.milliseconds)
            println("📋 Menu is ready.")
        }
    }.invokeOnCompletion { println("✅ Customer prep is complete") }
}


fun CoroutineScope.doKitchenPrep() {
    launch {
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
    }.invokeOnCompletion { println("✅ Kitchen prep is complete") }
}