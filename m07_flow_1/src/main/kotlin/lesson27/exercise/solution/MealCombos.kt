package lesson27.exercise.solution

import lesson27.exercise.solution.MealCombo.*

/* ****************************************************************************

   This fast food restaurant has five different meal combos:

    - #1 Classic Burger Combo - 🍔🍟🥤
    - #2 Double Trouble Combo - 🍔🍔🍟🥤
    - #3 Chicken Combo - 🍗🍟🥤
    - #4 Veggie Power Combo - 🥗🥤
    - #5 Breakfast All-Day Combo - 🥯☕🍩

   Three customers came in and placed orders:

    1. The first customer ordered #2 and #4.
    2. The second customer ordered meal combos #1, #3, and #4.
    3. The third customer ordered #5, #1, and #4.

   Write a `main()` function that calls a `placeOrder()` function three times -
   once for each customer. The `placeOrder()` function should receive the
   meal combos that the customer ordered. Within that function, print these
   things to the screen:

    - The number of combos in the order.
    - The name of each combo in the order.
    - The individual items from all combos in the entire order.

   Use a collection or sequence operator chain to transform the meal combos
   into the individual items of each combo in the order.

   Oh - one more thing! The restaurant is out of 🍗 and 🥯... so if a customer
   orders one of those, substitute it with a coupon 💸.

   Here's an example of what the output for the first customer should look like.

   ----------------------------------------------------------------------------

   Fulfilling an order that has 2 combos in it.
       Combos in this order: Double Trouble Combo, Veggie Power Combo
       Items in this order: 🍔 🍔 🍟 🥤 🥗 🥤

   ************************************************************************* */

fun main() {
    placeOrder(COMBO_2, COMBO_4)
    placeOrder(COMBO_1, COMBO_3, COMBO_4)
    placeOrder(COMBO_5, COMBO_1, COMBO_4)
}

fun placeOrder(vararg meals: MealCombo) {
    val items = getItemsForMealCombo(meals.toList())

    println("Fulfilling an order that has ${meals.size} combos in it.")
    println("    Combos in this order: ${meals.joinToString { it.description }}")
    println("    Items in this order: ${items.joinToString(" ")}")
    println()
}

fun getItemsForMealCombo(meals: List<MealCombo>): List<String> {
    return meals
        .flatMap { it.items }
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
