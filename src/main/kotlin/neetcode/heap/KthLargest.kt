package neetcode.heap

import java.util.PriorityQueue

class KthLargest(val k: Int, nums: IntArray) {
//    private val input = nums.toMutableList()
//
//    fun add(`val`: Int): Int {
//        input.add(`val`)
//        input.sort()
//        return input[input.size - k]
//    }

    val minHeap = PriorityQueue<Int>()

    init {
        nums.forEach {
            minHeap.offer(it)
        }

        while(minHeap.size > k) {
            minHeap.poll()
        }
    }

    fun add(`val`: Int) : Int{
        minHeap.offer(`val`)
        if(minHeap.size > k){
            minHeap.poll()
        }

        return minHeap.peek()
    }
}
