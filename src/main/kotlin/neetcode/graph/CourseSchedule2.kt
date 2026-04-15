package neetcode.graph

class CourseSchedule2 {
    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        val adj = Array(numCourses) { mutableListOf<Int>() }
        val indegree = IntArray(numCourses)
        for ((nxt, pre) in prerequisites) {
            indegree[nxt]++
            adj[pre].add(nxt)
        }

        val output = mutableListOf<Int>()

        fun dfs(node: Int) {
            output.add(node)
            indegree[node]--
            for (nei in adj[node]) {
                indegree[nei]--
                if (indegree[nei] == 0) {
                    dfs(nei)
                }
            }
        }

        for (i in 0 until numCourses) {
            if (indegree[i] == 0) {
                dfs(i)
            }
        }

        return if (output.size == numCourses) output.toIntArray() else intArrayOf()
    }
}