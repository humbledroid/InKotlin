package neetcode

import kotlin.math.ceil

class MinEatingSpeed {
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        var left  = 1
        var right = piles.max()
        var result = right

        while(left <= right) {
            val k = (left + right) / 2

            var totalTime = 0L

            for(p in piles) {
                totalTime += ceil(p.toDouble()/k).toLong()
            }

            if(totalTime <= h) {
                result = k
                right = k - 1
            }else {
                left = k + 1
            }
        }
        return result
    }
}


fun main() {
    val minEatingSpeed = MinEatingSpeed()
    println(minEatingSpeed.minEatingSpeed(piles = intArrayOf(1,4,3,2), h = 9))
}