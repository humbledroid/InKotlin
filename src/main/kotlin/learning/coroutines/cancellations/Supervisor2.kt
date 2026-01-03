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
