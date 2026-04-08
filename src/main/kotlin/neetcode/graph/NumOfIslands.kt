package neetcode.graph

class NumOfIslands {
    fun numIslands(grid: Array<CharArray>): Int {
        val directions = arrayOf(intArrayOf(1, 0),
                                 intArrayOf(-1, 0),
                                 intArrayOf(0, 1),
                                 intArrayOf(0, -1))
        val rows = grid.size
        val cols = grid[0].size
        var islands = 0

        fun dfs(r: Int, c: Int) {
            if (r < 0 || c < 0 || r >= rows ||
                c >= cols || grid[r][c] == '0') {
                return
            }
            grid[r][c] = '0'
            for (dir in directions) {
                dfs(r + dir[0], c + dir[1])
            }
        }

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == '1') {
                    dfs(r, c)
                    islands++
                }
            }
        }

        return islands
    }
}