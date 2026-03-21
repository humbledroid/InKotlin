package neetcode.heap

import java.util.PriorityQueue

class TaskScheduler {
    fun leastInterval(tasks: CharArray, n: Int): Int {
        val count = IntArray(26)
        for (task in tasks) {
            count[task - 'A']++
        }

        val maxHeap = PriorityQueue<Int>(compareBy { it * -1 })
        for (cnt in count) {
            if (cnt > 0) {
                maxHeap.offer(cnt)
            }
        }

        var time = 0
        val deque = ArrayDeque<Pair<Int, Int>>()

        while (maxHeap.isNotEmpty() || deque.isNotEmpty()) {
            time++

            if (maxHeap.isEmpty()) {
                time = deque.first().second
            } else {
                val cnt = 1 + maxHeap.poll() * -1
                if (cnt != 0) {
                    deque.addLast(Pair(cnt * -1, time + n))
                }
            }

            if (deque.isNotEmpty() && deque.first().second == time) {
                maxHeap.offer(deque.removeFirst().first)
            }
        }
        return time
    }
}