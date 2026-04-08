package neetcode.graph

class Node(var `val`: Int) {
    var neighbors: ArrayList<Node?> = ArrayList()
}

class CloneGraph {
    fun cloneGraph(node: Node?): Node? {
        if (node == null) return null

        val oldToNew = HashMap<Node, Node>()

        fun dfs(cur: Node): Node {
            oldToNew[cur]?.let { return it }

            val copy = Node(cur.`val`)
            oldToNew[cur] = copy

            val neighbors = cur.neighbors
            val copyNeighbors = copy.neighbors
            for (nei in neighbors) {
                if (nei != null) copyNeighbors.add(dfs(nei))
            }
            return copy
        }

        return dfs(node)
    }
}