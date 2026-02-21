package neetcode

import kotlin.math.max
import kotlin.math.min

class MedianOfSortedArray {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val (a, b) = if (nums1.size <= nums2.size) nums1 to nums2 else nums2 to nums1
        val half = (a.size + b.size + 1) / 2
        val isEven = (a.size + b.size) % 2 == 0

        var left = 0
        var right = a.size

        while (left <= right) {
            val aMid = (left + right) / 2
            val bMid = half - aMid

            val aLeft  = a.getOrElse(aMid - 1) { Int.MIN_VALUE }
            val aRight = a.getOrElse(aMid)     { Int.MAX_VALUE }
            val bLeft  = b.getOrElse(bMid - 1) { Int.MIN_VALUE }
            val bRight = b.getOrElse(bMid)     { Int.MAX_VALUE }

            when {
                aLeft <= bRight && bLeft <= aRight -> return if (isEven)
                    (maxOf(aLeft, bLeft) + minOf(aRight, bRight)) / 2.0
                else
                    maxOf(aLeft, bLeft).toDouble()
                aLeft > bRight -> right = aMid - 1
                else           -> left  = aMid + 1
            }
        }
        return 0.0
    }
}