package lesson51.content.state02

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private val logger = LoggerFactory.getLogger("Example")

fun main() {
    runBlocking {
        logger.info("runBlocking")
        lateinit var sallyOrder: Job
        lateinit var peterOrder: Job

        val tableOrder = launch {
            logger.info("tableOrder")
            sallyOrder = launch {
                logger.info("sallyOrder")
                launch { prepare("1 🍔 Hamburger", 5.seconds) }
                launch { prepare("1 🍟 Fries    ", 3.seconds) }
                launch { prepare("1 🥗 Salad    ", 2.seconds) }
                launch { prepare("1 🍰 Cake     ", 4.seconds) }
            }
            peterOrder = launch {
                logger.info("peterOrder")
                launch { prepare("2 🥩 Steak    ", 4.seconds) }
                launch { prepare("2 🥔 Potato   ", 2.seconds) }
                launch { prepare("2 🫛 Peas     ", 1.seconds) }
                launch { prepare("2 🍪 Cookie   ", 3.seconds) }
            }
        }
    }
}

suspend fun prepare(food: String, duration: Duration) {
    logger.info("{} - Preparing", food)
    delay(duration)
    logger.info("{} - Ready", food)
}
