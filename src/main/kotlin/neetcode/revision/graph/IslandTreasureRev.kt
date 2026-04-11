package neetcode.revision.graph

class IslandTreasureRev {
    fun islandsAndTreasure(grid: Array<IntArray>): Unit {
        val rows = grid.size
        val cols = grid[0].size
        val INF = 2147483647
        val directions = arrayOf(
            intArrayOf(1, 0), intArrayOf(-1, 0),
            intArrayOf(0, 1), intArrayOf(0, -1)
        )

        val queue: ArrayDeque<IntArray> = ArrayDeque()
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == 0) {
                    queue.addLast(intArrayOf(r, c))
                }
            }
        }

        while (queue.isNotEmpty()) {
            val (r, c) = queue.removeFirst()
            for (dir in directions) {
                val nr = r + dir[0]
                val nc = c + dir[1]
                if (nr < 0 || nc < 0 || nr >= rows || nc >= cols) continue
                if (grid[nr][nc] != INF) continue
                grid[nr][nc] = grid[r][c] + 1
                queue.addLast(intArrayOf(nr, nc))
            }
        }
    }
}