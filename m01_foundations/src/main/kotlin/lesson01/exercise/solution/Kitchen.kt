package lesson01.exercise.solution

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume

/* ****************************************************************************
   Cole is cooking up dinner for a full house of guests. He's grilling chicken,
   steaming vegetables, and baking potatoes. But if he cooks them one at a time,
   in order, the chicken will get cold before the potatoes are done.

   Using the Task class and the runTasks() function, help Cole cook his food
   concurrently. Below are instructions for how to prepare each food. Be sure to
   put each task in its own function, using println() to print out each
   individual activity, and calling goWorkOnSomethingElse() when indicated.

    Grilling Chicken           Steaming Vegetables        Baking Potatoes
    -------------------------  -------------------------  -------------------------------
    Warm up the grill          Add water to a pot         Wash the potatoes
    Go work on something else  Put the pot on the stove   Poke holes in the potatoes
    Season the chicken         Go work on something else  Turn on the oven
    Add chicken to grill       Chop the vegetables        Go work on something else
    Go work on something else  Add vegetables to the pot  Put the potatoes into the oven
    Serve up the chicken       Go work on something else  Go work on something else
                               Drain the water            Remove the potatoes from the oven
                               Serve up the vegetables    Serve up the potatoes
   ************************************************************************* */

fun main() {
    runTasks(grillChicken(), steamVegetables(), bakePotato())
}

fun grillChicken() = Task {
    println("🍗 Warm up the grill")
    goWorkOnSomethingElse()
    println("🍗 Season the chicken")
    println("🍗 Add chicken to grill")
    goWorkOnSomethingElse()
    println("🍗 Serve up the chicken")
}

fun steamVegetables() = Task {
    println("🥕 Add water to a pot")
    println("🥕 Put the pot on the stove")
    goWorkOnSomethingElse()
    println("🥕 Chop the vegetables")
    println("🥕 Add vegetables to the pot")
    goWorkOnSomethingElse()
    println("🥕 Drain the water")
    println("🥕 Serve up the vegetables")
}

fun bakePotato() = Task {
    println("🥔 Wash the potatoes")
    println("🥔 Poke holes in the potatoes")
    println("🥔 Turn on the oven")
    goWorkOnSomethingElse()
    println("🥔 Put the potatoes into the oven")
    goWorkOnSomethingElse()
    println("🥔 Remove the potatoes from the oven")
    println("🥔 Serve up the potatoes")
}

/* ************************************************************************* */

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
