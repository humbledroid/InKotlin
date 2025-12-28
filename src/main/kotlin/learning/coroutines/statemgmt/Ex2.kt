package learning.coroutines.statemgmt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.plusAssign

/**
 *
 * Solution using Atomics, works in same way with Java atomics
 */

@OptIn(ExperimentalAtomicApi::class)
fun main() {
    val revenue = AtomicLong(0L)

    runBlocking {
        for(order in orders()){
            launch(Dispatchers.Default) {
                val price = calculatePrice(order.item, order.membership)
                revenue += price
            }
        }
    }

    val formatted = "$%,d".format(revenue.load())
    println("Today's revenue is $formatted.")
}
