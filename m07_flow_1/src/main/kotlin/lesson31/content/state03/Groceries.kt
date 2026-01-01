package lesson31.content.state03

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main() = runBlocking {
    try {
        flow {
            emit("🥛")
            emit("🍉")
            emit("🍊")
            emit("🍇")
            emit("🥦")
        }
            .onEach {
                println("Looking for: $it")
            }
            .transform {
                emit(findOnShelf(it))
            }
            .collect {
                println("Adding to cart: ${it.label}")
            }
    } catch (e: OutOfStockException) {
        println(e.message)
    }

    println("Done with grocery shopping")
}

suspend fun findOnShelf(label: String): GroceryItem {
    delay(250.milliseconds)
    return when (label) {
        "🥛"  -> GroceryItem("🥛 Milk", 3_39)
        "🍉"  -> GroceryItem("🍉 Watermelon", 5_99)
        "🍇"  -> GroceryItem("🍇 Grapes", 5_49)
        "🥦"  -> GroceryItem("🥦 Broccoli", 2_99)
        else -> throw OutOfStockException(label)
    }
}

data class GroceryItem(val label: String, val price: Int)
data class OutOfStockException(val label: String) : Exception("$label is out of stock.")

fun substitute(label: String) = GroceryItem("🍎 Apple", 1_29)
