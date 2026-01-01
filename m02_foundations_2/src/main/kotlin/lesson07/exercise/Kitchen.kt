package lesson07.exercise

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************
   Cole and his two friends - Jane and Will - are cooking dinner again, but
   they're having trouble keeping track of who's working on each thing.

   The code below prints out the CoroutineName on each line of output. Update
   the `main()` function so that this code prints out the name of the person
   cooking each kind of food.

   - Cole wants to cook the chicken.
   - Jane wants to steam the vegetables.
   - Will wants to bake the potatoes.

   When you run the code, it should print output that includes lines that look
   like these:

   Jane >> 🥕 Add water to a pot
   Cole >> 🍗 Turn on the grill
   Will >> 🥔 Turn on the oven
   ************************************************************************* */

fun main() {
    runBlocking {
        launch(Dispatchers.Default) { grillChicken() }
        launch(Dispatchers.Default) { steamVegetables() }
        launch(Dispatchers.Default) { bakePotatoes() }
    }
}

suspend fun grillChicken() {
    actively("🍗 Turn on the grill", 500.milliseconds)
    passively("🍗 Wait for the grill to warm up", 1.seconds)
    actively("🍗 Season the chicken", 1.seconds)
    actively("🍗 Add chicken to grill", 500.milliseconds)
    passively("🍗 Wait for the chicken to cook", 2.seconds)
    actively("🍗 Serve up the chicken", 1.seconds)
}

suspend fun steamVegetables() {
    actively("🥕 Add water to a pot", 1.seconds)
    actively("🥕 Put the pot on the stove", 500.milliseconds)
    passively("🥕 Wait for the water to boil", 1.seconds)
    actively("🥕 Chop the vegetables", 2.seconds)
    actively("🥕 Add vegetables to the pot", 500.milliseconds)
    passively("🥕 Wait for the vegetables to cook", 2.seconds)
    actively("🥕 Drain the water", 500.milliseconds)
    actively("🥕 Serve up the vegetables", 1.seconds)
}

suspend fun bakePotatoes() {
    actively("🥔 Turn on the oven", 500.milliseconds)
    actively("🥔 Wash the potatoes", 1.seconds)
    actively("🥔 Poke holes in the potatoes", 500.milliseconds)
    passively("🥔 Wait for the oven to warm up", 2.seconds)
    actively("🥔 Put the potatoes into the oven", 500.milliseconds)
    passively("🥔 Wait for the potatoes to cook", 3.seconds)
    actively("🥔 Remove the potatoes from the oven", 500.milliseconds)
    actively("🥔 Serve up the potatoes", 1.seconds)
}

suspend fun actively(string: String, duration: Duration) {
    println("${coroutineContext.name} >> $string")
    Thread.sleep(duration.inWholeMilliseconds)
}

suspend fun passively(string: String, duration: Duration) {
    println("${coroutineContext.name} >> $string")
    delay(duration)
}

private val CoroutineContext.name get() = this[CoroutineName]?.name.orEmpty()
