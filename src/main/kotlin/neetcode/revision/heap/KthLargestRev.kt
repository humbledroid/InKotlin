package neetcode.revision.heap

import java.util.PriorityQueue

class KthLargestRev(val k: Int, nums: IntArray) {
    val minHeap = PriorityQueue<Int>()

    init {
        nums.forEach {
            minHeap.offer(it)
        }

        while(minHeap.size > k){
            minHeap.poll()
        }
    }

    fun add(`val`: Int): Int {
        minHeap.offer(`val`)
        if(minHeap.size > k){
            minHeap.poll()
        }

        return minHeap.peek()
    }
}