package neetcode.graph

private class DSU2(n: Int) {
    private val parent = IntArray(n + 1) { it }
    private val size = IntArray(n + 1) { 1 }
    var comps = n
        private set

    fun find(node: Int): Int {
        if (parent[node] != node) {
            parent[node] = find(parent[node])
        }
        return parent[node]
    }

    fun union(u: Int, v: Int): Boolean {
        val pu = find(u)
        val pv = find(v)
        if (pu == pv) return false

        comps--
        if (size[pu] < size[pv]) {
            parent[pu] = pv
            size[pv] += size[pu]
        } else {
            parent[pv] = pu
            size[pu] += size[pv]
        }
        return true
    }
}

class ValidGraph {
    fun validTree(n: Int, edges: Array<IntArray>): Boolean {
        if (edges.size > n - 1) return false

        val dsu = DSU2(n)
        for ((u, v) in edges) {
            if (!dsu.union(u, v)) return false
        }
        return dsu.comps == 1
    }
}