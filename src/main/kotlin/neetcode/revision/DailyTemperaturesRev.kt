package neetcode.revision

import java.util.*

class DailyTemperaturesRev {
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
    val dailyTemperaturesRev = DailyTemperaturesRev()
    println(dailyTemperaturesRev.dailyTemperatures(intArrayOf(73,74,75,71,69,72,76,73)).joinToString(","))
}