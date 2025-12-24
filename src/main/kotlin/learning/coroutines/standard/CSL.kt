package learning.coroutines.standard

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * # Sequence Builders in Kotlin: generateSequence vs sequence
 *
 * This file demonstrates two approaches to building lazy sequences in Kotlin:
 * 1. generateSequence - Standard library function
 * 2. sequence - Coroutine-based sequence builder
 *
 * ## generateSequence
 * - Part of Kotlin standard library (no coroutines needed)
 * - Works by providing an initial value and a next function
 * - The next function returns the next element or null to terminate
 * - Simpler but less flexible - each element must be computed from the previous one
 * - Stateless transformation: next = f(current)
 * - Cannot maintain complex state across iterations easily
 *
 * Syntax:
 * ```
 * generateSequence(seed) { current ->
 *     // compute next element from current
 *     // return null to stop
 * }
 * ```
 *
 * ## sequence (Coroutine-based)
 * - Part of kotlinx.coroutines library
 * - Uses suspend functions and yield() to emit values
 * - More flexible - can use any control flow (loops, conditionals, etc.)
 * - Can maintain state across iterations naturally using local variables
 * - Supports suspension - can integrate with other coroutine operations
 * - Better for complex iteration logic
 *
 * Syntax:
 * ```
 * sequence {
 *     // use any control flow
 *     yield(value) // emit a value
 * }
 * ```
 */

val weekdays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

fun weekdays(from: LocalDate, to: LocalDate): Sequence<LocalDate> {
    require(from <= to)

    var start = from

    while(start.dayOfWeek in weekdays) {
        start = start.plusDays(1)
    }

    return generateSequence(start) { date ->
        var next = date.plusDays(1)
        while (next <= to && next.dayOfWeek in weekdays) {
            next = next.plusDays(1)
        }

        return@generateSequence if (next <= to) next else null
    }
}

fun weekdaysSequence(from: LocalDate, to: LocalDate) = sequence  {
    require(from <= to)
    var date = from

    while (date <= to) {
        if(date.dayOfWeek !in weekdays) yield(date)
        date = date.plusDays(1)
    }
}


fun main() {
    val startDate = LocalDate.parse("1947-12-01")
    val endDate = LocalDate.parse("1947-12-31")

    for(date in weekdays(from = startDate, to = endDate)) {
        println(date)
    }

    println("========= Moving to sequence from Coroutines ==========")

    for(date in weekdaysSequence(from = startDate, to = endDate)) {
        println(date)
    }
}