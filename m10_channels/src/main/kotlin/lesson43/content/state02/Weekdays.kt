package lesson43.content.state02

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlin.time.Duration.Companion.milliseconds

val weekendDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main() = coroutineScope {
    val channel = produce {
        var date: LocalDate = LocalDate.parse("2025-06-01")
        val end = LocalDate.parse("2025-06-30")

        while (date <= end) {
            if (date.dayOfWeek !in weekendDays) send(date)
            date = date.plus(DatePeriod(days = 1))
        }
    }

    for (date in channel) {
        delay(500.milliseconds)
        println(date)
    }

    println("Done")
}
