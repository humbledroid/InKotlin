package neetcode.graph

class PacificAtlantic {
    fun pacificAtlantic(heights: Array<IntArray>): List<List<Int>> {
        val rows = heights.size
        val cols = heights[0].size
        val pac = HashSet<Pair<Int, Int>>()
        val atl = HashSet<Pair<Int, Int>>()

        fun dfs(r: Int, c: Int, visit: HashSet<Pair<Int, Int>>, prevHeight: Int) {
            val coord = r to c
            if (coord in visit ||
                r < 0 || c < 0 ||
                r == rows || c == cols ||
                heights[r][c] < prevHeight
            ) {
                return
            }

            visit.add(coord)

            dfs(r + 1, c, visit, heights[r][c])
            dfs(r - 1, c, visit, heights[r][c])
            dfs(r, c + 1, visit, heights[r][c])
            dfs(r, c - 1, visit, heights[r][c])
        }

        for (c in 0 until cols) {
            dfs(0, c, pac, heights[0][c])
            dfs(rows - 1, c, atl, heights[rows - 1][c])
        }

        for (r in 0 until rows) {
            dfs(r, 0, pac, heights[r][0])
            dfs(r, cols - 1, atl, heights[r][cols - 1])
        }

        return (0 until rows).flatMap { r ->
            (0 until cols).mapNotNull { c ->
                if ((r to c) in pac && (r to c) in atl) {
                    listOf(r, c)
                } else null
            }
        }
    }
}