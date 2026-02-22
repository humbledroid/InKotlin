package neetcode.revision

class MedianOfSortedArrayRev {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val (a, b) = if (nums1.size <= nums2.size) nums1 to nums2 else nums2 to nums1

        val half = (a.size + b.size + 1) / 2
        val isEven = (a.size + b.size) % 2 == 0

        var left = 0
        var right = 0

        while (left <= right) {
            val aMid = left + right / 2
            val bMid = half - aMid // whatever partition is left from A

            val aLeftPartition = a.getOrElse(aMid - 1) { Int.MIN_VALUE }
            val aRightPartition = a.getOrElse(aMid) { Int.MAX_VALUE }

            val bLeftPartition = b.getOrElse(bMid - 1) { Int.MIN_VALUE }
            val bRightPartition = b.getOrElse(bMid) { Int.MAX_VALUE }

            when {
                aLeftPartition <= bRightPartition && bLeftPartition <= aRightPartition ->
                    return if (isEven) maxOf(
                        aLeftPartition,
                        bLeftPartition
                    ) + minOf(aRightPartition, bRightPartition) / 2.0 else maxOf(
                        aLeftPartition,
                        bLeftPartition
                    ).toDouble()

                aLeftPartition > bRightPartition -> right = aMid - 1
                else -> left = aMid + 1
            }


        }

        return 1.0
    }
}