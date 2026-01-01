package lesson05.exercise.solution

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

/* ****************************************************************************
   - Copy and paste your solution from Lesson 1's exercise.
   - Then, remove the `Task` class and the `runTasks()` function.
   - Finally, update your solution so that it uses the coroutines library.
   ************************************************************************* */

fun main() {
    runBlocking {
        launch { grillChicken() }
        launch { steamVegetables() }
        launch { bakePotato() }
    }
}

suspend fun grillChicken() {
    println("🍗 Warm up the grill")
    yield()
    println("🍗 Season the chicken")
    println("🍗 Add chicken to grill")
    yield()
    println("🍗 Serve up the chicken")
}

suspend fun steamVegetables() {
    println("🥕 Add water to a pot")
    println("🥕 Put the pot on the stove")
    yield()
    println("🥕 Chop the vegetables")
    println("🥕 Add vegetables to the pot")
    yield()
    println("🥕 Drain the water")
    println("🥕 Serve up the vegetables")
}

suspend fun bakePotato() {
    println("🥔 Wash the potatoes")
    println("🥔 Poke holes in the potatoes")
    println("🥔 Turn on the oven")
    yield()
    println("🥔 Put the potatoes into the oven")
    yield()
    println("🥔 Remove the potatoes from the oven")
    println("🥔 Serve up the potatoes")
}
