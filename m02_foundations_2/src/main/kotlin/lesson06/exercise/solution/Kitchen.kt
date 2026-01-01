package lesson06.exercise.solution

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime

/* ****************************************************************************
   Cole is cooking up dinner again! This time, he's got two friends helping him
   out in the kitchen, so they can get things done faster.

   The code below takes over 13 seconds to complete. But with his friends
   helping out, they can get things done faster. Update the `main()` function
   so that the coroutines run in parallel. You should be able to get it down
   to just over 9 seconds.
   ************************************************************************* */

fun main() {
    val time = measureTime {
        runBlocking {
            launch(Dispatchers.Default) { grillChicken() }
            launch(Dispatchers.Default) { steamVegetables() }
            launch(Dispatchers.Default) { bakePotato() }
        }
    }
    println()
    println("Cole and friends prepared dinner in $time.")
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

suspend fun bakePotato() {
    actively("🥔 Turn on the oven", 500.milliseconds)
    actively("🥔 Wash the potatoes", 1.seconds)
    actively("🥔 Poke holes in the potatoes", 500.milliseconds)
    passively("🥔 Wait for the oven to warm up", 2.seconds)
    actively("🥔 Put the potatoes into the oven", 500.milliseconds)
    passively("🥔 Wait for the potatoes to cook", 3.seconds)
    actively("🥔 Remove the potatoes from the oven", 500.milliseconds)
    actively("🥔 Serve up the potatoes", 1.seconds)
}

fun actively(string: String, duration: Duration) {
    println(string)
    Thread.sleep(duration.inWholeMilliseconds)
}

suspend fun passively(string: String, duration: Duration) {
    println(string)
    delay(duration)
}
