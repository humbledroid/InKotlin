package learning.coroutines.flows

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.time.DayOfWeek
import java.time.LocalDate



fun main() {
    val weekendDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

    fun weekdays(from: LocalDate, to: LocalDate): Flow<LocalDate> = flow {
        require(from <= to)

        var date: LocalDate = from

        while (date <= to) {
            if (date.dayOfWeek !in weekendDays) emit(date)
            date = date.plusDays(1)
        }
    }

    val start = LocalDate.parse("2025-06-01")
    val end = LocalDate.parse("2025-06-30")

   runBlocking {
       weekdays(from = start, to = end)
           .collect { println(it) }
   }
}
