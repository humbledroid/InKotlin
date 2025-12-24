package learning.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED

/**
 * COOPERATIVE MULTITASKING WITH COROUTINES - SEQUENCE DIAGRAM
 * ===========================================================
 *
 * runTasks          Desk Task                    Shelf Task
 *    |                  |                              |
 *    |-- resume() ----->|                              |
 *    |                  |-- Cutting wood               |
 *    |                  |-- Attaching legs             |
 *    |                  |-- Waiting for glue           |
 *    |                  |-- goWorkOnSomethingElse() ---|
 *    |                  |   (suspends, saves continuation point)
 *    |                  |   (adds self back to queue)  |
 *    |<-COROUTINE_SUSPENDED-|                          |
 *    |                  |                              |
 *    |-- resume() ----------------------------------->|
 *    |                  |                              |-- Cutting wood
 *    |                  |                              |-- Assembling frame
 *    |                  |                              |-- Waiting for glue
 *    |                  |                              |-- goWorkOnSomethingElse()
 *    |                  |                              |   (suspends, saves continuation point)
 *    |                  |                              |   (adds self back to queue)
 *    |<-COROUTINE_SUSPENDED--------------------------------|
 *    |                  |                              |
 *    |-- resume() ----->|                              |
 *    |                  |-- Staining the desk          |
 *    |                  |-- Waiting for stains         |
 *    |                  |-- goWorkOnSomethingElse() ---|
 *    |<-COROUTINE_SUSPENDED-|                          |
 *    |                  |                              |
 *    |-- resume() ----------------------------------->|
 *    |                  |                              |-- Inserting shelves
 *    |                  |                              |-- Staining shelf
 *    |                  |                              |-- Waiting for stains
 *    |                  |                              |-- goWorkOnSomethingElse()
 *    |<-COROUTINE_SUSPENDED--------------------------------|
 *    |                  |                              |
 *    |-- resume() ----->|                              |
 *    |                  |-- Polishing the desk         |
 *    |                  |-- (completes)                |
 *    |                  |                              |
 *    |-- resume() ----------------------------------->|
 *    |                  |                              |-- Polishing the Shelf
 *    |                  |                              |-- (completes)
 *    |                  |                              |
 *   (done)             (done)                        (done)
 */

/**
 * Task: A wrapper for cooperative multitasking using coroutines.
 *
 * This class demonstrates how to build a simple cooperative task scheduler using
 * low-level coroutine primitives. Each task can voluntarily yield control to allow
 * other tasks to run.
 *
 * Key Concepts:
 * - createCoroutine: Creates a coroutine from a suspend lambda, returning its initial continuation
 * - Continuation: Represents a suspension point in a coroutine - where it can be paused and resumed
 * - suspendCoroutineUninterceptedOrReturn: Low-level primitive to manually suspend a coroutine
 * - COROUTINE_SUSPENDED: Marker indicating the coroutine has been suspended (didn't complete immediately)
 */
class Task(block: suspend Task.() -> Unit) {

    /**
     * The continuation representing where this task should resume from.
     * Initially points to the start of the task. After each suspension, updated to the next resumption point.
     *
     * createCoroutine() converts the suspend lambda into a Continuation that can be controlled manually.
     * The receiver (this) allows the task block to call goWorkOnSomethingElse().
     */
    private var continuation: Continuation<Unit> = block.createCoroutine(this, Continuation(EmptyCoroutineContext) {})

    /**
     * Callback invoked when this task suspends. Used by the scheduler to re-queue this task.
     */
    var onPaused: (() -> Unit)? = null

    /**
     * Suspends the current task, allowing other tasks to run.
     *
     * How it works:
     * 1. suspendCoroutineUninterceptedOrReturn captures the current continuation (where to resume from)
     * 2. We save this continuation for later resumption
     * 3. Invoke onPaused callback to notify the scheduler (which adds us back to the queue)
     * 4. Return COROUTINE_SUSPENDED to indicate we didn't complete immediately
     *
     * This is cooperative - the task must explicitly call this to yield control.
     */
    suspend fun goWorkOnSomethingElse() = suspendCoroutineUninterceptedOrReturn { cont: Continuation<Unit> ->
        continuation = cont  // Save where to resume from
        onPaused?.invoke()   // Notify scheduler (adds task back to queue)
        COROUTINE_SUSPENDED  // Signal that we suspended (don't continue executing)
    }

    /**
     * Resumes this task from its last suspension point.
     * If the task was never started, this starts it from the beginning.
     */
    fun resume() = continuation.resume(Unit)
}

/**
 * Simple cooperative task scheduler using a round-robin approach.
 *
 * How it works:
 * 1. Initialize a queue with all tasks
 * 2. Configure each task's onPaused callback to re-add itself to the end of the queue when it suspends
 * 3. Loop: dequeue a task, resume it
 *    - If the task suspends (calls goWorkOnSomethingElse), it adds itself back to the queue
 *    - If the task completes, it doesn't get re-queued
 * 4. Continue until all tasks have completed (queue is empty)
 *
 * This is COOPERATIVE multitasking - tasks must voluntarily yield control by calling goWorkOnSomethingElse().
 * If a task never yields, it will run to completion and block all other tasks.
 */
fun runTasks(vararg task: Task) {
    // Queue of tasks to execute
    val tasks = ArrayDeque(task.toList())

    // Configure each task: when it suspends, add it back to the end of the queue
    tasks.onEach { it.onPaused = { tasks.add(it) } }

    // Round-robin execution: keep resuming tasks until all complete
    while (tasks.isNotEmpty()) {
        tasks.removeFirst().resume()  // Resume the next task
        // If task suspended, it's already re-queued via onPaused callback
        // If task completed, it won't be re-queued
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
    goWorkOnSomethingElse()  // Suspend here, let other tasks run

    println("DESK -> Staining the desk")
    println("DESK -> Waiting for the stains to dry")
    goWorkOnSomethingElse()  // Suspend here, let other tasks run

    println("DESK -> Polishing the desk")
}

fun buildSelf() = Task {
    println("SHELF -> Cutting wood for shelf")
    println("SHELF -> Assembling frame")
    println("SHELF -> Waiting for the glue to dry")
    goWorkOnSomethingElse()  // Suspend here, let desk continue

    println("SHELF -> Inserting the shelves into the frame")
    println("SHELF -> Staining the shelf")
    println("SHELF -> Waiting for the stains to dry")
    goWorkOnSomethingElse()  // Suspend here, let desk continue

    println("SHELF -> Polishing the Shelf")
}

/**
 * EXPECTED OUTPUT:
 * ================
 * DESK -> Cutting wood for desk
 * DESK -> Attaching legs to surface
 * DESK -> Waiting for the glue to dry
 * SHELF -> Cutting wood for shelf
 * SHELF -> Assembling frame
 * SHELF -> Waiting for the glue to dry
 * DESK -> Staining the desk
 * DESK -> Waiting for the stains to dry
 * SHELF -> Inserting the shelves into the frame
 * SHELF -> Staining the shelf
 * SHELF -> Waiting for the stains to dry
 * DESK -> Polishing the desk
 * SHELF -> Polishing the Shelf
 *
 * Notice how the tasks interleave - we work on the desk until we need to wait,
 * then switch to the shelf, then back to the desk, etc. This simulates doing
 * productive work while waiting for blocking operations to complete.
 */
