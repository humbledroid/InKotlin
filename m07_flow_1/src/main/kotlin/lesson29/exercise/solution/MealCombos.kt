package lesson29.exercise.solution

import kotlinx.coroutines.flow.*
import lesson29.exercise.solution.MealCombo.*

/* ****************************************************************************

   Update your solution from Lesson 27's lesson so that it uses Flow operators
   instead of Collection or Sequence operators. The output of your solution
   should be the same as in Lesson 27.

   Note that you can use `asFlow()` to convert collection types into a flow,
   and `toList()` to convert a flow to a list.

   ************************************************************************* */

suspend fun main() {
    placeOrder(COMBO_2, COMBO_4)
    placeOrder(COMBO_1, COMBO_3, COMBO_4)
    placeOrder(COMBO_5, COMBO_1, COMBO_4)
}

suspend fun placeOrder(vararg meals: MealCombo) {
    val items = getItemsForMealCombo(meals.asFlow())

    println("Fulfilling an order that has ${meals.size} combos in it.")
    println("    Combos in this order: ${meals.joinToString { it.description }}")
    println("    Items in this order: ${items.toList().joinToString(" ")}")
    println()
}

fun getItemsForMealCombo(meals: Flow<MealCombo>): Flow<String> {
    return meals
        .transform { it.items.forEach { item -> emit(item) } }
        .map { if (it in outOfStockItems) "💸" else it }
}

val outOfStockItems = setOf("🍗", "🥯")

enum class MealCombo(val description: String, vararg foodItems: String) {
    COMBO_1("Classic Burger Combo", "🍔", "🍟", "🥤"),
    COMBO_2("Double Trouble Combo", "🍔", "🍔", "🍟", "🥤"),
    COMBO_3("Chicken Combo", "🍗", "🍟", "🥤"),
    COMBO_4("Veggie Power Combo", "🥗", "🥤"),
    COMBO_5("Breakfast All-Day Combo", "🥯", "☕", "🍩");

    val items = foodItems.toList()
}
