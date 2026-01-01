package lesson05.content.state01

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume

fun main() {
    runTasks(
        buildDesk(),
        buildShelf()
    )
}

fun buildDesk() = Task {
    println("DESK  >> Cutting wood for the desk.")
    println("DESK  >> Attaching legs to the surface.")
    println("DESK  >> Waiting for the glue to dry.")
    goWorkOnSomethingElse()

    println("DESK  >> Staining the desk.")
    println("DESK  >> Waiting for the stain to dry.")
    goWorkOnSomethingElse()

    println("DESK  >> Polishing the desk.")
}

fun buildShelf() = Task {
    println("SHELF >> Cutting wood for the shelf.")
    println("SHELF >> Assembling the frame.")
    println("SHELF >> Waiting for the glue to dry.")
    goWorkOnSomethingElse()

    println("SHELF >> Inserting the shelves into the frame.")
    println("SHELF >> Staining the shelf.")
    println("SHELF >> Waiting for the stain to dry.")
    goWorkOnSomethingElse()

    println("SHELF >> Polishing the shelf.")
}

class Task(block: suspend Task.() -> Unit) {
    private var continuation: Continuation<Unit> = block.createCoroutine(this, Continuation(EmptyCoroutineContext) {})
    var onSuspended: (() -> Unit)? = null

    suspend fun goWorkOnSomethingElse() = suspendCoroutineUninterceptedOrReturn { cont: Continuation<Unit> ->
        continuation = cont
        onSuspended?.invoke()
        COROUTINE_SUSPENDED
    }

    fun resume() = continuation.resume(Unit)
}

fun runTasks(vararg task: Task) {
    val tasks = ArrayDeque(task.toList())
    tasks.onEach { it.onSuspended = { tasks.add(it) } }

    while (tasks.isNotEmpty()) {
        tasks.removeFirst().resume()
    }
}
