package neetcode.revision

import neetcode.TopKFrequentElements
import java.util.PriorityQueue

class TopKFrequentElementRev {

    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        val frequencyMap = mutableMapOf<Int, Int>()
        nums.forEach {
            frequencyMap[it] = frequencyMap.getOrDefault(it, 0) + 1
        }

        val bucket = MutableList<MutableList<Int>>(nums.size+1) { mutableListOf() }

        val result = mutableListOf<Int>()

        frequencyMap.forEach { (num, count) ->
            bucket[count].add(num)
        }

        for(i in bucket.size - 1 downTo 0) {
            bucket[i].forEach {
                result.add(it)

                if(result.size == k) return result.toIntArray()
            }
        }

        return result.toIntArray()
    }


    fun topKFrequentHeap(nums: IntArray, k: Int): IntArray {
        val frequencyMap = mutableMapOf<Int, Int>()
        nums.forEach {
            frequencyMap[it] = frequencyMap.getOrDefault(it, 0) + 1
        }

        /**
         * here pair is num and count
         */
        val priorityQueue = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })

        frequencyMap.forEach { (num, count) ->
            priorityQueue.offer(Pair(num, count))
            if(priorityQueue.size > k) priorityQueue.poll()
        }

        return IntArray(k) {
            priorityQueue.poll().first
        }

    }

}

fun main() {
    val topKFrequentElements = TopKFrequentElementRev()
    println(topKFrequentElements.topKFrequentHeap(intArrayOf(1,1,1,2,2,3), k = 2).joinToString(","))
}