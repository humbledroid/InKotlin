package neetcode.graph

class FlightRoute {
    fun findItinerary(tickets: List<List<String>>): List<String> {
        val adj = HashMap<String, MutableList<String>>()

        tickets.sortedBy { it[1] }.forEach { (src, dst) ->
            adj.getOrPut(src) { mutableListOf() }.add(dst)
        }

        val res = mutableListOf("JFK")

        fun dfs(src: String): Boolean {
            if (res.size == tickets.size + 1) {
                return true
            }

            val destinations = adj[src] ?: return false

            for (i in destinations.indices) {
                val v = destinations.removeAt(i)
                res.add(v)

                if (dfs(v)) {
                    return true
                }

                destinations.add(i, v)
                res.removeAt(res.lastIndex)
            }
            return false
        }

        dfs("JFK")
        return res
    }
}