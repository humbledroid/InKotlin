package neetcode

import kotlin.math.min


fun main() {
    fun maxArea(heights: IntArray): Int {
        var max = 0
        var start = 0
        var end = heights.lastIndex

        while (start < end) {
            val length = end - start
            val startHeight = heights[start]
            val endHeight = heights[end]
            val breadth = min(startHeight, endHeight)

            max = maxOf(max, (length * breadth))

            if(startHeight < endHeight) {
                start++
            }else {
                end--
            }
        }

        return max
    }

    val maxArea = maxArea(intArrayOf(1,7,2,5,4,7,3,6))
    println(maxArea)
}