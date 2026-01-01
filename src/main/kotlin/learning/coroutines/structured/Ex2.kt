package learning.coroutines.structured

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking(Dispatchers.Default) {
        println("Started")
        println("Scope is $this")
        launch {
            doKitchenPrep()
        }
        launch {
            doCustomerPrep()
        }
        launch {
            doCleanup()
        }
    }

    println("🥐 The bakery is ready to open!")
}

suspend fun doCleanup(){
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