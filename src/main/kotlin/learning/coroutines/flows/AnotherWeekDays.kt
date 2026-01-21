package learning.coroutines.flows

import java.time.DayOfWeek
import java.time.LocalDate

val weekendDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

fun weekdays(from: LocalDate, to: LocalDate): Sequence<LocalDate> = sequence {
    require(from <= to)

    var date: LocalDate = from

    while (date <= to) {
        if (date.dayOfWeek !in weekendDays) yield(date)
        date = date.plusDays(1)
    }
}

fun main() {
    val start = LocalDate.parse("2025-06-01")
    val end = LocalDate.parse("2032-12-31")

    weekdays(from = start, to = end)
        .take(5)
        .forEach { date -> println(date) }
}
