package lesson03.content.state01

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

val weekendDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

fun weekdays(from: LocalDate, to: LocalDate): Sequence<LocalDate> {
    require(from <= to)

    var start = from
    while (start.dayOfWeek in weekendDays) {
        start = start.plus(DatePeriod(days = 1))
    }

    return generateSequence(start) { date ->
        var next = date.plus(DatePeriod(days = 1))
        while (next <= to && next.dayOfWeek in weekendDays) {
            next = next.plus(DatePeriod(days = 1))
        }
        return@generateSequence if (next <= to) next else null
    }
}

fun main() {
    val start = LocalDate.parse("2025-06-01")
    val end = LocalDate.parse("2025-06-30")

    for (date in weekdays(from = start, to = end)) {
        println(date)
    }
}
