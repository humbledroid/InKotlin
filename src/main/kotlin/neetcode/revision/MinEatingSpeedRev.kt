package neetcode.revision

import kotlin.math.ceil


class MinEatingSpeedRev {
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        var left = 1
        var right = piles.max()
        var result = right

        while (left <= right) {

            val mid = left + (right - left) / 2
            var totalTime = 0L

            piles.forEach {
                totalTime += ceil(it.toDouble() / mid).toLong()
            }

            if (totalTime <= h) {
                result = mid
                right = mid - 1
            } else {
                left = mid + 1
            }
        }

        return result
    }
}


fun main() {
    val minEatingSpeed = MinEatingSpeedRev()
    println(minEatingSpeed.minEatingSpeed(piles = intArrayOf(1, 4, 3, 2), h = 9))
}