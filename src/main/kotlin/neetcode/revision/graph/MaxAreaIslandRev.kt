package neetcode.revision.graph

class MaxAreaIslandRev {
    fun maxAreaOfIsland(grid: Array<IntArray>): Int {
        val rows = grid.size
        val cols = grid[0].size

        fun dfs(r: Int, c: Int): Int {
            if (r !in 0..<rows || c < 0 || c >= cols || grid[r][c] == 0) return 0
            grid[r][c] = 0
            return 1 + dfs(r + 1, c) + dfs(r - 1, c) + dfs(r, c + 1) + dfs(r, c - 1)
        }

        var area = 0
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == 1) {
                    val a = dfs(r, c)
                    if (a > area) area = a
                }
            }
        }
        return area
    }
}