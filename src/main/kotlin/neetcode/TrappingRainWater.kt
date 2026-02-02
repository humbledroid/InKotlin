package neetcode

import kotlin.math.min

class TrappingRainWater {
    fun trap(height: IntArray): Int {
        var result = 0

        val maxLeft = IntArray(height.size) { 0 }
        val maxRight = IntArray(height.size) { 0 }
        for (i in 1 until height.size) {
            maxLeft[i] = maxOf(height[i - 1], maxLeft[i-1])
        }

        for (i in height.size-2 downTo 0) {
            maxRight[i] = maxOf(height[i + 1], maxRight[i+1])
        }

        for (i in height.indices) {
            result += if((min(maxLeft[i], maxRight[i]) - height[i]) >= 0) min(maxLeft[i], maxRight[i]) - height[i] else 0
        }

        return result
    }

    fun trapWithTwoPointers(height: IntArray): Int {
        var result = 0
        var start = 0
        var end = height.lastIndex
        var leftMax = 0
        var rightMax = height[end]

        while(start < end) {
            if(leftMax < rightMax) {
                start++
                leftMax = maxOf(leftMax, height[start])
                result += leftMax - height[start]
            }else{
                end--
                rightMax = maxOf(rightMax, height[end])
                result += rightMax - height[end]
            }
        }

        return result
    }
}

fun main() {
    val trapWater = TrappingRainWater()
    println(trapWater.trapWithTwoPointers(intArrayOf(0,1,0,2,1,0,1,3,2,1,2,1)))
}