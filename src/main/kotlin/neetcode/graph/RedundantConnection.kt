package neetcode.graph

class RedundantConnection {
    fun findRedundantConnection(edges: Array<IntArray>): IntArray {
        val n = edges.size
        val adj = Array(n + 1) { mutableListOf<Int>() }
        for ((u, v) in edges) {
            adj[u].add(v)
            adj[v].add(u)
        }

        val visit = BooleanArray(n + 1)
        val cycle = HashSet<Int>()
        var cycleStart = -1

        fun dfs(node: Int, par: Int): Boolean {
            if (visit[node]) {
                cycleStart = node
                return true
            }
            visit[node] = true
            for (nei in adj[node]) {
                if (nei == par) continue
                if (dfs(nei, node)) {
                    if (cycleStart != -1) {
                        cycle.add(node)
                    }
                    if (node == cycleStart) {
                        cycleStart = -1
                    }
                    return true
                }
            }
            return false
        }

        dfs(1, -1)

        for (i in edges.indices.reversed()) {
            val (u, v) = edges[i]
            if (u in cycle && v in cycle) {
                return intArrayOf(u, v)
            }
        }
        return intArrayOf()
    }
}