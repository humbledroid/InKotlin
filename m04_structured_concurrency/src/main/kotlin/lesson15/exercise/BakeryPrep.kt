package lesson15.exercise

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************
   There's a lot to do to prepare the bakery for customers before they can
   open, and all the employees are just grabbing tasks to work on them. Even
   worse, they seem to be opening the bakery before the tasks are done!

   Help them organize their tasks into these groups:

   - Kitchen Prep
       - Kneading dough
       - Mixing frosting
       - Baking croissants
   - Customer Prep
       - Brewing coffee
       - Printing menu
   - Cleaning Up
       - Cleaning counters
       - Washing dishes

   After each group is done, print out that the group has finished.
   For example:

   ✅ Kitchen prep is complete

    ************************************************************************* */

fun main() {
    runBlocking {
        GlobalScope.launch {
            delay(2.seconds)
            println("🍞 Dough is ready.")
        }
        GlobalScope.launch {
            delay(500.milliseconds)
            println("🧁 Frosting is mixed.")
        }
        GlobalScope.launch {
            delay(1.seconds)
            println("☕ Coffee is ready.")
        }
        GlobalScope.launch {
            delay(3.seconds)
            println("🥐 Croissants are baked.")
        }
        GlobalScope.launch {
            delay(1.2.seconds)
            println("🧽 Counters are clean.")
        }
        GlobalScope.launch {
            delay(800.milliseconds)
            println("📋 Menu is ready.")
        }
        GlobalScope.launch {
            delay(1.seconds)
            println("🧼 Dishes are clean.")
        }
    }

    println("🥐 The bakery is ready to open!")
}
