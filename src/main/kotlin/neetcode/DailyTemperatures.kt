package neetcode

import java.util.*


/**
 * You are given an array of integers temperatures where temperatures[i] represents the
 * daily temperatures on the ith day.
 *
 * Return an array result where result[i] is the number of days after the ith day
 * before a warmer temperature appears on a future day. If there is no day in the future
 * where a warmer temperature will appear for the ith day, set result[i] to 0 instead.
 */
class DailyTemperatures {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        val result = IntArray(temperatures.size) {0}
        val stack = Stack<Int>()

        for (i in temperatures.indices) {
            while(stack.isNotEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                val previous = stack.pop()
                result[previous] = i - previous
            }

            stack.push(i)
        }

        return result
    }
}

fun main() {
    val dailyTemperatures = DailyTemperatures()
    println(dailyTemperatures.dailyTemperatures(intArrayOf(73,74,75,71,69,72,76,73)).joinToString(","))
}