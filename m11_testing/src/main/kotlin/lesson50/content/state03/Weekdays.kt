package lesson50.content.state03

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

val weekendDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

fun weekdays(from: LocalDate, to: LocalDate): Flow<LocalDate> = flow {
    require(from <= to)
//
//    var date: LocalDate = from
//
//    while (date <= to) {
//        if (date.dayOfWeek !in weekendDays) emit(date)
//        date = date.plus(DatePeriod(days = 1))
//    }
}
