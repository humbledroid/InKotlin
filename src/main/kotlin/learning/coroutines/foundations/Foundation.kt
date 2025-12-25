package learning.coroutines.foundations

import kotlinx.coroutines.*
import learning.coroutines.Task
import learning.coroutines.runTasks
import kotlin.time.measureTime

/*
fun main() = runBlocking<Unit> {
    async {
        nurseryRhyme()
    }
}

suspend fun nurseryRhyme() {
    printAndPause("Twinkle, twinkle little var")
    printAndPause("Twinkle, 1")
    printAndPause("Twinkle, 2")
    printAndPause("Twinkle, 3")
    printAndPause("Twinkle, 4")
    printAndPause("Twinkle, 5")
    printAndPause("Twinkle, 6")
    printAndPause("Twinkle, 7")
}

suspend fun printAndPause(s: String) {
    println(s)
    delay(1000L)
}
*/

fun main(): Unit = measureTime {
    runBlocking {
        val job1 = async(context = Dispatchers.Default + CoroutineName("chip")) {
            buildDesk()
        }
        val job2 = async(context = Dispatchers.Default + CoroutineName("buzz")) {
            buildSelf()
        }

        val totalCost = job1.await().price + job2.await().price
        println(totalCost)
    }
}.let(::println)

fun println(line: String) {
    kotlin.io.println(line)
    Thread.sleep(1000)
}


suspend fun buildDesk() : Desk {
    println("DESK -> Cutting wood for desk")
    println("DESK -> Attaching legs to surface")
    println("DESK -> Waiting for the glue to dry")
    yield()

    println("DESK -> Staining the desk")
    println("DESK -> Waiting for the stains to dry")
    yield()

    println("DESK -> Polishing the desk")
    return Desk(300)
}

suspend fun buildSelf(): Shelf{
    println("SHELF -> Cutting wood for shelf")
    println("SHELF -> Assembling frame")
    println("SHELF -> Waiting for the glue to dry")
    yield()

    println("SHELF -> Inserting the shelves into the frame")
    println("SHELF -> Staining the shelf")
    println("SHELF -> Waiting for the stains to dry")
    yield()

    println("SHELF -> Polishing the Shelf")
    return Shelf(100)
}


abstract class Product(val price: Int)
class Desk(price: Int): Product(price)
class Shelf(price: Int): Product(price)