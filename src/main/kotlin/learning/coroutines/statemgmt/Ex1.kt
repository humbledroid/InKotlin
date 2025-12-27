package learning.coroutines.statemgmt

import kotlinx.coroutines.*
import kotlin.random.Random
//
//fun main() = runBlocking {
//    val revenue = orders().map {
//        async (Dispatchers.Default) {
//            calculatePrice(it.item, it.membership)
//        }
//    }.sumOf {
//        it.await()
//    }
//
//    val deferred: MutableList<Deferred<Long>> = mutableListOf()
//    for (order in orders()){
//        deferred.add(
//           async { calculatePrice(order.item, order.membership) }
//        )
//    }
//
//    val result = deferred.sumOf { it.await() }
//
//    val formatted = "$%,d".format(result)
//    println("Today's revenue is $formatted.")
//}

fun main() {
    val synchronized = Dispatchers.Default.limitedParallelism(1)
    var revenue = 0L

    runBlocking {
        for(order in orders()){
            launch(Dispatchers.Default) {
                val price = calculatePrice(order.item, order.membership)
                withContext(synchronized){
                    revenue += price
                }
            }
        }
    }

    val formatted = "$%,d".format(revenue)
    println("Today's revenue is $formatted.")
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