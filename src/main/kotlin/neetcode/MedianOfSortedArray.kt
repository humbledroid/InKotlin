package neetcode

import kotlin.math.max
import kotlin.math.min

class MedianOfSortedArray {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        var a = nums1
        var b = nums2
        val total = a.size + b.size
        val half = (total + 1) / 2

        if (b.size < a.size) {
            a = nums2
            b = nums1
        }
        val totalSize = a.size + b.size

        var left = 0
        var right = a.size

        while(left <= right){
            val aMid  = (left + right) / 2 // Mid of A
            val bMid = half - aMid

            val aLeft = if(aMid > 0) a[aMid - 1] else Int.MIN_VALUE
            val aRight = if(aMid < a.size) a[aMid] else Int.MAX_VALUE
            val bLeft = if(bMid > 0 ) b[bMid - 1] else Int.MIN_VALUE
            val bRight = if(bMid < b.size) b[bMid] else Int.MAX_VALUE

            // correct partition here
            if(aLeft <= bRight && bLeft <= aRight) {
                if(totalSize % 2 == 0){
                    return (max(aLeft, bLeft) + min(aRight, bRight))/2.toDouble()
                }else{
                    return max(aLeft, bLeft).toDouble()
                }
            }else if(aLeft > bRight) {
                right = aMid - 1
            } else {
                left = aMid + 1
            }

        }
        return 0.0
    }
}