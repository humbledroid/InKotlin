package lesson28.content.state03

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun main() {
    val dates = flowOf(
        "2025-06-05",
        "2025-06-06",
        "2025-06-07",
        "2025-06-08",
        "2025-06-09",
    )

    runBlocking {
        dates.collect {
            delay(250.milliseconds)
            println(it)
        }

        println("--------------------")

        dates.collect {
            delay(1.seconds)
            println(it)
        }
    }
}
