@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson53.state03

import kotlinx.coroutines.*
import kotlinx.coroutines.debug.DebugProbes
import org.slf4j.LoggerFactory
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private val logger = LoggerFactory.getLogger("Example")

fun main() {
    DebugProbes.install()
    runBlocking(CoroutineName("Restaurant")) {
        lateinit var sallyOrder: Job
        lateinit var peterOrder: Job

        logger.info("The restaurant is open for business!")

        val tableOrder = launch(CoroutineName("Table")) {
            logger.info("Guests are being seated at this table")
            DebugProbes.dumpCoroutines()

            sallyOrder = launch(CoroutineName("Sally")) {
                logger.info("Guest is placing an order.")
                launch { prepare("1 🍔 Hamburger", 5.seconds) }
                launch { prepare("1 🍟 Fries    ", 3.seconds) }
                launch { prepare("1 🥗 Salad    ", 2.seconds) }
                launch { prepare("1 🍰 Cake     ", 4.seconds) }
            }
            peterOrder = launch(CoroutineName("Peter")) {
                logger.info("Guest is placing an order.")
                launch { prepare("2 🥩 Steak    ", 4.seconds) }
                launch { prepare("2 🥔 Potato   ", 2.seconds) }
                launch { prepare("2 🫛 Peas     ", 1.seconds) }
                launch { prepare("2 🍪 Cookie   ", 3.seconds) }
            }
        }
        delay(5.seconds)
    }
}

suspend fun prepare(food: String, duration: Duration) {
    logger.info("{} - Preparing", food)
    delay(duration)
    logger.info("{} - Ready", food)
}
