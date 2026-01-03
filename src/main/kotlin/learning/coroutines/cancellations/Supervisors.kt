package learning.coroutines.cancellations

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking {
        lateinit var sallyOrder: Job
        lateinit var peterOrder: Job


        val tableOrder = launch {
            sallyOrder = launch {
                launch { prepare("1 🍔 Hamburger", 5.seconds) }
                launch { prepare("1 🍟 Fries    ", 3.seconds) }
                launch { prepare("1 🥗 Salad    ", 2.seconds) }
                launch { prepare("1 🍰 Cake     ", 4.seconds) }
            }

            peterOrder = launch {
                /**
                 * this "steak" here got the exception, and thus was not processed
                 * and this is launched under superVisorScope, only this coroutine
                 * was affected, and all other children executes as they were supposed
                 * to execute
                 */

                /**
                 * AN EXCEPTION THROWN IN ANY ONE OF THE supervisors' children will
                 * NOT AFFECT ANY OF ITS OTHER CHILDREN, NOR WILL IT AFFECT THE PARENT
                 * OF THE SUPERVISORS
                 */
                supervisorScope {
                    launch { prepare("2 🥩 Steak    ", 2.5.seconds) }
                    launch { prepare("2 🥔 Potato   ", 2.seconds) }
                    launch { prepare("2 🫛 Peas     ", 1.seconds) }
                    launch { prepare("2 🍪 Cookie   ", 3.seconds) }
                }
            }

        }
    }
}

suspend fun prepare(food: String, duration: Duration): String {
    println("$food - Preparing")
    delay(duration)
    if ("🥩" in food) throw KitchenFireException("🔥 Grill caught on fire!")
    println("$food - Ready")
    return food.split(" ")[1]
}
