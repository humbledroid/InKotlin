package lesson50.content.state13

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.milliseconds

object GroceryStore {
    private val _stock = MutableSharedFlow<String>()
    val stock = _stock.asSharedFlow()

    private val _income = MutableSharedFlow<Int>()
    val income = _income.asSharedFlow()

    suspend fun shop(vararg items: String): Unit = flowOf(*items)
        .onEach { println("Looking for: $it") }
        .map { findOnShelf(it) }
        .collect { item ->
            _stock.emit(item.label)
            _income.emit(item.price)
        }

    private suspend fun findOnShelf(label: String): GroceryItem {
        delay(250.milliseconds)
        return when (label) {
            "🥛"  -> GroceryItem("🥛 Milk", 3_39)
            "🍉"  -> GroceryItem("🍉 Watermelon", 5_99)
            "🍊"  -> GroceryItem("🍊 Orange", 1_29)
            "🍇"  -> GroceryItem("🍇 Grapes", 5_49)
            "🥦"  -> GroceryItem("🥦 Broccoli", 2_99)
            else -> throw OutOfStockException(label)
        }
    }
}

data class GroceryItem(val label: String, val price: Int)
data class OutOfStockException(val label: String) :
    Exception("$label is out of stock.")
