package neetcode.heap

import java.util.PriorityQueue

class KthLargestNumber {
    // the sorting way :face_palm:
    fun findKthLargest(nums: IntArray, k: Int): Int {
//        nums.sort()
//        return nums[nums.size - k]
//    }

        val minHeap = PriorityQueue<Int>()

        nums.forEach { num ->
            minHeap.offer(num)
            if (minHeap.size > k) {
                minHeap.poll()
            }
        }
        return minHeap.peek()
    }
}