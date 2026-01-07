package learning.coroutines.patterns

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking {
        val car = Car("🚗")
        launch { car.changeOil() }
        launch { car.performFreeServices() }
    }
}

suspend fun Car.changeOil() {
    println("$label - Changing oil...")
    delay(3.seconds)
    println("$label - Oil has been changed.")
}

suspend fun Car.performFreeServices() {
    println("$label - Let's perform some free services...")
    delay(0.5.seconds)
    println("$label - This will only take a moment...")
    coroutineScope {
        launch { checkLights() }
        launch { checkWiperFluid() }
        launch { addAirToTires() }
    }
}

suspend fun Car.checkLights() {
    delay(0.5.seconds)
    println("$label - Lights look good.")
}

suspend fun Car.checkWiperFluid() {
    delay(0.25.seconds)
    println("$label - Wiper fluid looks good.")
}

suspend fun Car.addAirToTires() {
    delay(1.seconds)
    println("$label - Air added to tires.")
}

data class Car(val label: String)