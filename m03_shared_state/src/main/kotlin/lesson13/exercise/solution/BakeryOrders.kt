package lesson13.exercise.solution

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.plusAssign
import kotlin.random.Random

/* ****************************************************************************

   You probably guessed this exercise! Copy your solution from the exercise for
   Lesson 09, and update it so that it resolves the race condition by using
   atomics.

   ************************************************************************* */

@OptIn(ExperimentalAtomicApi::class)
fun main() {
    val revenue = AtomicLong(0L)

    runBlocking {
        for (order in orders()) {
            launch(Dispatchers.Default) {
                revenue += calculatePrice(order.item, order.membership)
            }
        }
    }

    val formatted = "$%,d.00".format(revenue.load())
    println("Today's revenue is $formatted")
}

fun orders(): Sequence<Order> = sequence {
    val randomizer = Random(seed = 0)
    repeat(18_289) {
        yield(
            Order(
                BakedGood.entries.random(randomizer),
                Membership.entries.random(randomizer)
            )
        )
    }
}

fun calculatePrice(item: BakedGood, membership: Membership): Long {
    return item.price - membership.discount
}

data class Order(val item: BakedGood, val membership: Membership)

enum class BakedGood(val price: Long) {
    COOKIE(5), CAKE(25), PIE(15),
}

enum class Membership(val discount: Long) {
    /** Non-members do not get a discount. */
    NON_MEMBER(0),

    /** Basic membership gives $1 off a baked good purchase. */
    BASIC_MEMBER(1),

    /** Premium membership gives $3 off a baked good purchase. */
    PREMIUM_MEMBER(3)
}
