package neetcode.revision.heap

import java.util.PriorityQueue

class TaskSchedulerRev {
    fun leastInterval(tasks: CharArray, n: Int): Int {
        val count = IntArray(26)
        for (task in tasks) {
            count[task - 'A']++
        }
        val maxHeap = PriorityQueue<Int>(compareBy { it * -1 })

        count.forEach {
            if (it > 0) {
                maxHeap.offer(it)
            }
        }
        var time = 0
        val deque = ArrayDeque<Pair<Int, Int>>()

        while (maxHeap.isNotEmpty() || deque.isNotEmpty()) {
            time++
            if (maxHeap.isEmpty()) {
                time = deque.first().second
            } else {
                val currentCount = 1 + maxHeap.poll() * -1
                if (currentCount != 0) {
                    deque.addLast(Pair(currentCount * -1, time + n))
                }
            }
            if (deque.isNotEmpty() && deque.first().second == time) {
                maxHeap.offer(deque.removeFirst().first)
            }
        }
        return time
    }
}