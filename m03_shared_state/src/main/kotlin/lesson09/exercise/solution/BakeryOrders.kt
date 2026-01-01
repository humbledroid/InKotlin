package lesson09.exercise.solution

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

/* ****************************************************************************
   Jen's Bakery is processing (a rather absurd amount of) orders for cookies,
   cakes, and pies.

   The expected revenue is $250,005, and when you run this code without any
   changes, that's what you should see reported.

   Breaking things can be a fun way to learn! So for this exercise, introduce
   a race condition by launching coroutines within the `for` loop. Be sure
   that the coroutines run in parallel.

   When you're done, the reported revenue should be _less than_ $250,005. When
   that's the case, you've successfully introduced a race condition.

   ************************************************************************* */

fun main() {
    var revenue = 0L

    runBlocking {
        for (order in orders()) {
            launch(Dispatchers.Default) {
                revenue += calculatePrice(order.item, order.membership)
            }
        }
    }

    val formatted = "$%,d.00".format(revenue)
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
