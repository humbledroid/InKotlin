package learning.coroutines.statemgmt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.plusAssign

/**
 * Solution using Mutex, to synchronize the critical section
 */

fun main() {
    var revenue = 0L
    val mutex = Mutex()

    runBlocking {
        for(order in orders()){
            launch(Dispatchers.Default) {
                val price = calculatePrice(order.item, order.membership)
                mutex.withLock {
                    revenue += price
                }
            }
        }
    }

    val formatted = "$%,d".format(revenue)
    println("Today's revenue is $formatted.")
}
