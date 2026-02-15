package neetcode

import java.util.*
import kotlin.math.max

class LargestRectangle {
    fun largestRectangleArea(heights: IntArray): Int {
        var maxArea = 0
        val stack = Stack<Pair<Int, Int>>()

        heights.forEachIndexed { index, height ->
            var start = index
            while(stack.isNotEmpty() && stack.peek().second > height) {
                val (i, h) = stack.pop()
                maxArea = max(maxArea, h * (index - i))
                start = index
            }

            stack.push(start to height)
        }

        val n = heights.size
        for ((i, h) in stack) {
            maxArea = maxOf(maxArea, h * (n - i))
        }

        return maxArea
    }
}

fun main() {
    val largestRectangle = LargestRectangle()
    println(largestRectangle.largestRectangleArea(intArrayOf(2,1,5,6,2,3)))
}