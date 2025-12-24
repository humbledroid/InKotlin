package learning.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED

class Task(block: suspend Task.() -> Unit) {

    private var continuation: Continuation<Unit> = block.createCoroutine(this, Continuation(EmptyCoroutineContext) {})

    var onPaused: (() -> Unit)? = null

    suspend fun goWorkOnSomethingElse() = suspendCoroutineUninterceptedOrReturn { cont: Continuation<Unit> ->
        continuation = cont
        onPaused?.invoke()
        COROUTINE_SUSPENDED
    }

    fun resume() = continuation.resume(Unit)
}

fun runTasks(vararg task: Task) {
    val tasks = ArrayDeque(task.toList())
    tasks.onEach { it.onPaused = { tasks.add(it) } }

    while (tasks.isNotEmpty()) {
        tasks.removeFirst().resume()
    }
}


fun main() {
    runTasks(
        buildDesk(),
        buildSelf()
    )
}


fun buildDesk() = Task {
    println("DESK -> Cutting wood for desk")
    println("DESK -> Attaching legs to surface")
    println("DESK -> Waiting for the glue to dry")
    goWorkOnSomethingElse()

    println("DESK -> Staining the desk")
    println("DESK -> Waiting for the stains to dry")
    goWorkOnSomethingElse()

    println("DESK -> Polishing the desk")
}

fun buildSelf() = Task {
    println("SHELF -> Cutting wood for shelf")
    println("SHELF -> Assembling frame")
    println("SHELF -> Waiting for the glue to dry")
    goWorkOnSomethingElse()

    println("SHELF -> Inserting the shelves into the frame")
    println("SHELF -> Staining the shelf")
    println("SHELF -> Waiting for the stains to dry")
    goWorkOnSomethingElse()

    println("SHELF -> Polishing the Shelf")
}
