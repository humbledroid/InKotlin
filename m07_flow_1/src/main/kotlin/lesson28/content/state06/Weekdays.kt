package lesson28.content.state06

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlin.time.Duration.Companion.milliseconds

val weekendDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

fun weekdays(from: LocalDate, to: LocalDate): Flow<LocalDate> = flow {
    require(from <= to)

    var date: LocalDate = from

    while (date <= to) {
        if (date.dayOfWeek !in weekendDays) emit(date)
        date = date.plus(DatePeriod(days = 1))
    }
}

suspend fun main() {
    val start = LocalDate.parse("2025-06-01")
    val end = LocalDate.parse("2025-06-30")

    weekdays(from = start, to = end)
        .collect { date ->
            delay(500.milliseconds)
            println(date)
        }
}
