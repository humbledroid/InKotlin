package neetcode.revision

import java.util.*

class DailyTemperaturesRev {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        val result = IntArray(temperatures.size) { 0 }
        val stack = Stack<Int>()
        temperatures.forEachIndexed { index, value ->
            while (stack.isNotEmpty() && temperatures[stack.peek()] < value ) {
                val previous = stack.pop()
                result[previous] = index - previous
            }

            stack.push(index)
        }

        return result
    }
}

fun main() {
    val dailyTemperaturesRev = DailyTemperaturesRev()
    println(dailyTemperaturesRev.dailyTemperatures(intArrayOf(73, 74, 75, 71, 69, 72, 76, 73)).joinToString(","))
}