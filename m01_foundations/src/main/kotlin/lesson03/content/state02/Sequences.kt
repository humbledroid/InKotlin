package lesson03.content.state02

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

val weekendDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

fun weekdays(from: LocalDate, to: LocalDate): Sequence<LocalDate> = sequence {
    require(from <= to)

    var date: LocalDate = from

    while (date <= to) {
        if (date.dayOfWeek !in weekendDays) yield(date)
        date = date.plus(DatePeriod(days = 1))
    }
}

fun main() {
    val start = LocalDate.parse("2025-06-01")
    val end = LocalDate.parse("2025-06-30")

    for (date in weekdays(from = start, to = end)) {
        println(date)
    }
}
