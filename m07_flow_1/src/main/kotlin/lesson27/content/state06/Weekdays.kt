package lesson27.content.state06

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

val weekendDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

fun weekdays(from: LocalDate): Sequence<LocalDate> = sequence {
    var date: LocalDate = from

    while (true) {
        if (date.dayOfWeek !in weekendDays) yield(date)
        date = date.plus(DatePeriod(days = 1))
    }
}

fun main() {
    val start = LocalDate.parse("2025-06-01")

    weekdays(from = start)
        .forEach { date -> println(date) }
}
