package neetcode.graph

import java.util.PriorityQueue

class NetworkDelay {
    fun networkDelayTime(times: Array<IntArray>, n: Int, k: Int): Int {
        val edges = HashMap<Int, MutableList<Pair<Int, Int>>>()
        for ((u, v, w) in times) {
            edges.computeIfAbsent(u) { mutableListOf() }.add(Pair(v, w))
        }

        val minHeap = PriorityQueue<Pair<Int, Int>>(compareBy { it.first })
        minHeap.offer(Pair(0, k))
        val visited = HashSet<Int>()
        var t = 0

        while (minHeap.isNotEmpty()) {
            val (time, node) = minHeap.poll()
            if (node in visited) continue
            visited.add(node)
            t = time

            edges[node]?.forEach { (nextNode, weight) ->
                if (nextNode !in visited) {
                    minHeap.offer(Pair(time + weight, nextNode))
                }
            }
        }

        return if (visited.size == n) t else -1
    }
}