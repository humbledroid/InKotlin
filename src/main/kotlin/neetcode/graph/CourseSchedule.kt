package neetcode.graph

import java.util.LinkedList
import java.util.Queue

class CourseSchedule {
    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        val indegree = IntArray(numCourses) { 0 }
        val adj = Array(numCourses) { mutableListOf<Int>() }

        for (prereq in prerequisites) {
            val (src, dst) = prereq
            indegree[dst]++
            adj[src].add(dst)
        }

        val q: Queue<Int> = LinkedList()
        for (n in 0 until numCourses) {
            if (indegree[n] == 0) {
                q.add(n)
            }
        }

        var finish = 0
        while (q.isNotEmpty()) {
            val node = q.poll()
            finish++
            for (nei in adj[node]) {
                indegree[nei]--
                if (indegree[nei] == 0) {
                    q.add(nei)
                }
            }
        }

        return finish == numCourses
    }
}