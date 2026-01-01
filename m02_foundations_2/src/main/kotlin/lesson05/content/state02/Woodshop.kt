package lesson05.content.state02

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() {
    runBlocking {
        launch { buildDesk() }
        launch { buildShelf() }
    }
}

suspend fun buildDesk() {
    println("DESK  >> Cutting wood for the desk.")
    println("DESK  >> Attaching legs to the surface.")
    println("DESK  >> Waiting for the glue to dry.")
    yield()

    println("DESK  >> Staining the desk.")
    println("DESK  >> Waiting for the stain to dry.")
    yield()

    println("DESK  >> Polishing the desk.")
}

suspend fun buildShelf() {
    println("SHELF >> Cutting wood for the shelf.")
    println("SHELF >> Assembling the frame.")
    println("SHELF >> Waiting for the glue to dry.")
    yield()

    println("SHELF >> Inserting the shelves into the frame.")
    println("SHELF >> Staining the shelf.")
    println("SHELF >> Waiting for the stain to dry.")
    yield()

    println("SHELF >> Polishing the shelf.")
}

