package learning.coroutines.flows

import kotlinx.coroutines.flow.*

suspend fun main() {

    fun getItemsForMealCombo(meals: Flow<MealCombo>): Flow<String> {
        return meals
            .transform { it.items.forEach { item -> emit(item) } }
            .map {
                if (it in outOfStockItems) "💸" else it
            }
    }

    suspend fun placeOrder(vararg meals: MealCombo) {
        val items = getItemsForMealCombo(meals.asFlow())

        println("Fulfilling an order that has ${meals.size} combos in it.")
        println("    Combos in this order: ${meals.joinToString { it.description }}")
        println("    Items in this order: ${items.toList().joinToString(" ")}")
        println()
    }


    placeOrder(MealCombo.COMBO_2, MealCombo.COMBO_4)
    placeOrder(MealCombo.COMBO_1, MealCombo.COMBO_3, MealCombo.COMBO_4)
    placeOrder(MealCombo.COMBO_5, MealCombo.COMBO_1, MealCombo.COMBO_4)
}


