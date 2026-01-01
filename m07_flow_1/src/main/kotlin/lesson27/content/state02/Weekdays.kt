package lesson27.content.state02

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

fun main() {
    listOf(
        "2025-06-05",
        "2025-06-06",
        "2025-06-07",
        "2025-06-08",
        "2025-06-09",
        "2025-06-10",
        "2025-06-11",
        "2025-06-12",
        "2025-06-13",
        "2025-06-14",
    )
        .map { LocalDate.parse(it) }
        .filter { it.dayOfWeek !in setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY) }
        .take(5)
        .forEach { println(it) }
}
