package lesson28.content.state02

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
    val dates = flowOf(
        "2025-06-05",
        "2025-06-06",
        "2025-06-07",
        "2025-06-08",
        "2025-06-09",
    )

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
