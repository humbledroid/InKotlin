package neetcode.revision.heap

import java.util.PriorityQueue

class KClosestPointsRev {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        val minHeap = PriorityQueue(compareBy<IntArray> { it[0] * it[0] + it[1] * it[1] })
        points.forEach {
            minHeap.offer(it)
        }
        val res = Array<IntArray>(k){ IntArray(2) }
        repeat(k) {
            res[it] = minHeap.poll()
        }

        return res
    }
}