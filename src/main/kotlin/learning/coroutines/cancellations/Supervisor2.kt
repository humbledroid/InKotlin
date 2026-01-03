package learning.coroutines.cancellations

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking {

    val scope = CoroutineScope(SupervisorJob())

    val sallyOrder = scope.launch {
        launch { prepare("1 🍔 Hamburger", 5.seconds) }
        launch { prepare("1 🍟 Fries    ", 3.seconds) }
        launch { prepare("1 🥗 Salad    ", 2.seconds) }
        launch { prepare("1 🍰 Cake     ", 4.seconds) }
    }

    val peterOrder = scope.launch {
        launch { prepare("2 🥩 Steak    ", 2.5.seconds) }
        launch { prepare("2 🥔 Potato   ", 2.seconds) }
        launch { prepare("2 🫛 Peas     ", 1.seconds) }
        launch { prepare("2 🍪 Cookie   ", 3.seconds) }
    }

    sallyOrder.join()
    peterOrder.join()
}


/**
 * 1 🍟 Fries     - Preparing
 * 2 🥩 Steak     - Preparing
 * 1 🍰 Cake      - Preparing
 * 2 🫛 Peas      - Preparing
 * 2 🍪 Cookie    - Preparing
 * 1 🍔 Hamburger - Preparing
 * 2 🥔 Potato    - Preparing
 * 1 🥗 Salad     - Preparing
 * 2 🫛 Peas      - Ready
 * 1 🥗 Salad     - Ready
 * 2 🥔 Potato    - Ready
 * Exception in thread "DefaultDispatcher-worker-9" learning.coroutines.cancellations.KitchenFireException: 🔥 Grill caught on fire!
 * 	at learning.coroutines.cancellations.SupervisorsKt.prepare-8Mi8wO0(Supervisors.kt:49)
 * 	at learning.coroutines.cancellations.SupervisorsKt$prepare$1.invokeSuspend(Supervisors.kt)
 * 	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
 * 	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:100)
 * 	at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:586)
 * 	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:829)
 * 	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:717)
 * 	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:704)
 * 	Suppressed: kotlinx.coroutines.internal.DiagnosticCoroutineContextException: [StandaloneCoroutine{Cancelling}@1ff03b6d, Dispatchers.Default]
 * 1 🍟 Fries     - Ready
 * 1 🍰 Cake      - Ready
 * 1 🍔 Hamburger - Ready
 *
 *
 * So this will the output, notice the scope with SuperVisorJob as two children
 * sallyOrder and peterOrder, as there was no exception from sallyOrder scope
 * and peterOrder had an exception which cancelled the steak coroutine, and notice in
 * the out that all the item in sallyOrder were ready, and in peter order
 * only potato and peas were ready, so what happened was
 *
 * 1. steak order got the exception
 * 2. it got cancelled, the exception reached to parent, that is
 * superVisorJob, which then cancelled all the running coroutine under that
 * children i.e., peter order, cause of which the only running coroutine
 * which wasn't finished `Cookie` got cancelled, thus didn't produce any result for
 * cookies, and all of this didn't affect the sally orders
 *
 *
 * Also, the program exits with status code 0, meaning it didn't crash
 */

