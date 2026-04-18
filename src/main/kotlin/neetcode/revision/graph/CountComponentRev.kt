package neetcode.revision.graph


/**
 * Attempting an improvement is switching to Union-Find,
 * which avoids recursion overhead (no stack overflow risk on large graphs) and skips building an adjacency list:
 */
class CountComponentRev {
    fun countComponents(n: Int, edges: Array<IntArray>): Int {
        val parent = IntArray(n) { it }
        val rank = IntArray(n)
        var components = n

        fun find(x: Int): Int {
            if (parent[x] != x) parent[x] = find(parent[x]) // path compression
            return parent[x]
        }

        for ((u, v) in edges) {
            val pu = find(u)
            val pv = find(v)
            if (pu != pv) {
                // union by rank
                when {
                    rank[pu] < rank[pv] -> parent[pu] = pv
                    rank[pu] > rank[pv] -> parent[pv] = pu
                    else -> { parent[pv] = pu; rank[pu]++ }
                }
                components--
            }
        }
        return components
    }
}