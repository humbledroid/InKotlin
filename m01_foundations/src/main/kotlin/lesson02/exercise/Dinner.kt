package lesson02.exercise

/* **************************************************************************************

   Now that dinner is ready, it's time to eat!

   Remember - it's healthy to eat slowly. To take your time eating, you'll want to add a
   delay between the food you're eating. You can do that by uncommenting the call to the
   `delay()` function below.

   But when you do that, you'll get a compiler error! Fix that compiler error to complete
   this exercise.

   *********************************************************************************** */

fun main() {
    println("Getting ready to eat dinner...")
    eatDinner("🍗 Grilled Chicken", "🥕 Vegetables", "🥔 Baked Potato", "🍰 Dessert")
    println("All done eating dinner!")
}

fun eatDinner(vararg food: String) {
    for (item in food) {
        println("Eating $item")
        //kotlinx.coroutines.delay(1000)
    }
}
