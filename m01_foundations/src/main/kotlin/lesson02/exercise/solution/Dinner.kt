package lesson02.exercise.solution

import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("Getting ready to eat dinner...")
        eatDinner("🍗 Grilled Chicken", "🥕 Vegetables", "🥔 Baked Potato", "🍰 Dessert")
        println("All done eating dinner!")
    }
}

suspend fun eatDinner(vararg food: String) {
    for (item in food) {
        println("Eating $item")
        kotlinx.coroutines.delay(1000)
    }
}
