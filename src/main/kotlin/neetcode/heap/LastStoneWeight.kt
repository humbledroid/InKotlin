package neetcode.heap

import java.util.PriorityQueue
import kotlin.math.abs

class LastStoneWeight {
    fun lastStoneWeight(stones: IntArray): Int {
        val minHeap = PriorityQueue<Int>()

        stones.forEach {
            minHeap.offer(-it)
        }

        while(minHeap.size > 1){
            val first = minHeap.poll()
            val second = minHeap.poll()

            if(second > first){
                minHeap.offer(first-second)
            }
        }
        minHeap.offer(0)
        return abs(minHeap.peek())
    }
}