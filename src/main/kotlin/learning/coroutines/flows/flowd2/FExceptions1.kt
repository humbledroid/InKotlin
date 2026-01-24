package learning.coroutines.flows.flowd2

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main() = runBlocking {
//    //try catch can also come handy
//    try {
//        flow {
//            emit("🥛")
//            emit("🍉")
//            emit("🍊")
//            emit("🍇")
//            emit("🥦")
//        }
//            .onEach {
//                println("Looking for: $it")
//            }
//            //this alone gets the exception and terminates the flows
////        .transform {
////            emit(findOnShelf(it))
////        }
//            .transform {
//                /**
//                 * A local try catch can be added to catch the exception
//                 * and supply a substitute or handle the exception as deems fit
//                 */
////            try {
////                emit(findOnShelf(it))
////            }catch (ex: OutOfStockException) {
////                emit(substitute(it))
////            }
//                emit(findOnShelf(it))
//            }
//            .collect {
//                println("Adding to cart: ${it.label}")
//            }
//    }catch (e: OutOfStockException) {
//        println("OutOfStockException: ${e.message}")
//    }
//
//    flow {
//        emit("🥛")
//        emit("🍉")
//        emit("🍊")
//        emit("🍇")
//        emit("🥦")
//    }
//        .onEach {
//            println("Looking for: $it")
//        }
//        .map { findOnShelf(it) }
//        .catch { throwable ->
//            if(throwable is OutOfStockException) emit(GroceryItem("$5 Coupon", price = -5_00))
//        }
//        .collect {
//            println("Adding to cart: ${it.label}")
//        }


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
        .map { findOnShelf(it) }
//        .retry(2) { throwable ->
//            println(" ------------------ ")
//            throwable == OutOfStockException("🍊")
//        }
        .retryWhen { throwable, attemp ->
            println("Retry $attemp -------------")
            throwable == OutOfStockException("🍊")
        }
        .collect {
            println("Adding to cart: ${it.label}")
        }

    println("Done with grocery shopping")
}
var count = 0
suspend fun findOnShelf(label: String): GroceryItem {
    count++
    delay(250.milliseconds)
    return when (label) {
        "🥛"  -> GroceryItem("🥛 Milk", 3_39)
        "🍉"  -> GroceryItem("🍉 Watermelon", 5_99)
        "🍊" -> if(count > 8) GroceryItem("🍊", 1_29) else throw OutOfStockException(label)
        "🍇"  -> GroceryItem("🍇 Grapes", 5_49)
        "🥦"  -> GroceryItem("🥦 Broccoli", 2_99)
        else -> throw OutOfStockException(label)
    }
}

data class GroceryItem(val label: String, val price: Int)
data class OutOfStockException(val label: String) : Exception("$label is out of stock.")
fun substitute(label: String) = GroceryItem("🍎 Apple", 1_29)
