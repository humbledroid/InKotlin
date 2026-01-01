package lesson07.content.state01

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val desk = async(Dispatchers.Default + CoroutineName("Chip")) { buildDesk() }
        val shelf = async(Dispatchers.Default + CoroutineName("Buzz")) { buildShelf() }
        val totalSales = desk.await().price + shelf.await().price
    }
}

fun println(string: String) {
    kotlin.io.println(string)
    Thread.sleep(1000)
}

suspend fun buildDesk(): Desk {
    println("DESK  >> Cutting wood for the desk.")
    println("DESK  >> Attaching legs to the surface.")
    println("DESK  >> Waiting for the glue to dry.")
    yield()

    println("DESK  >> Staining the desk.")
    println("DESK  >> Waiting for the stain to dry.")
    yield()

    println("DESK  >> Polishing the desk.")
    return Desk(300)
}

suspend fun buildShelf(): Shelf {
    println("SHELF >> Cutting wood for the shelf.")
    println("SHELF >> Assembling the frame.")
    println("SHELF >> Waiting for the glue to dry.")
    yield()

    println("SHELF >> Inserting the shelves into the frame.")
    println("SHELF >> Staining the shelf.")
    println("SHELF >> Waiting for the stain to dry.")
    yield()

    println("SHELF >> Polishing the shelf.")
    return Shelf(150)
}

abstract class Product(val price: Int)
class Desk(price: Int) : Product(price)
class Shelf(price: Int) : Product(price)
