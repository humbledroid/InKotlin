package lesson28.content.state07

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlin.time.Duration.Companion.milliseconds

val weekendDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

suspend fun main() {
    flow {
        var date: LocalDate = LocalDate.parse("2025-06-01")
        val end = LocalDate.parse("2025-06-30")

        while (date <= end) {
            if (date.dayOfWeek !in weekendDays) emit(date)
            date = date.plus(DatePeriod(days = 1))
        }
    }.collect { date ->
        delay(500.milliseconds)
        println(date)
    }

    println("Done")
}
