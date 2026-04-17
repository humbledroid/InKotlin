package neetcode.graph

class CountComponents {
    fun countComponents(n: Int, edges: Array<IntArray>): Int {
        val adj = Array(n) { mutableListOf<Int>() }
        val visit = BooleanArray(n)
        for ((u, v) in edges) {
            adj[u].add(v)
            adj[v].add(u)
        }

        fun dfs(node: Int) {
            for (nei in adj[node]) {
                if (!visit[nei]) {
                    visit[nei] = true
                    dfs(nei)
                }
            }
        }

        var res = 0
        for (node in 0 until n) {
            if (!visit[node]) {
                visit[node] = true
                dfs(node)
                res++
            }
        }
        return res
    }
}