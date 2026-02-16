package neetcode.revision

import java.util.*
import kotlin.math.max

class LargestRectangleRev {
    fun largestRectangleArea(heights: IntArray): Int {
        var maxArea = 0
        val stack = Stack<Pair<Int, Int>>()

        heights.forEachIndexed { currentIndex, currentHeight ->
            var start = currentIndex
            /**
             * will keep on popping elements from stack, till stack is not empty,
             * and we are getting a current height which is shorter than the
             * height of the top most item in stack, as we can not expand the area
             * further, and we store the index, in our stack at which we end up
             * popping the stack
             */

            while(stack.isNotEmpty() && currentHeight < stack.peek().second) {
                val (stackPeekIndex, stackPeekHeight) = stack.pop()
                maxArea = maxOf(maxArea, stackPeekHeight * (currentIndex - stackPeekIndex))
                start = stackPeekIndex
            }

            stack.push(start to currentHeight)
        }

        // cause the stack might still have some un-calculated areas
        val histogramWidth = heights.size

        for((index, height) in stack){
            maxArea = maxOf(maxArea, height * (histogramWidth - index))
        }

        return maxArea
    }
}

fun main() {
    val largestRectangleRev = LargestRectangleRev()
    println(largestRectangleRev.largestRectangleArea(intArrayOf(2,1,5,6,2,3)))
}