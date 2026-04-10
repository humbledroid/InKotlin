package neetcode.graph

class RottingOrange {
    fun orangesRotting(grid: Array<IntArray>): Int {
        val rows = grid.size
        val cols = grid[0].size
        val queue = ArrayDeque<Int>()
        var fresh = 0
        var time = 0

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                when (grid[r][c]) {
                    1 -> fresh++
                    2 -> queue.addLast(r * cols + c)
                }
            }
        }

        if (fresh == 0) return 0

        while (queue.isNotEmpty()) {
            var length = queue.size
            var rottedThisRound = false

            while (length-- > 0) {
                val encoded = queue.removeFirst()
                val r = encoded / cols
                val c = encoded % cols

                if (r + 1 < rows && grid[r + 1][c] == 1) {
                    grid[r + 1][c] = 2
                    queue.addLast((r + 1) * cols + c)
                    fresh--
                    rottedThisRound = true
                }
                if (r - 1 >= 0 && grid[r - 1][c] == 1) {
                    grid[r - 1][c] = 2
                    queue.addLast((r - 1) * cols + c)
                    fresh--
                    rottedThisRound = true
                }
                if (c + 1 < cols && grid[r][c + 1] == 1) {
                    grid[r][c + 1] = 2
                    queue.addLast(r * cols + c + 1)
                    fresh--
                    rottedThisRound = true
                }
                if (c - 1 >= 0 && grid[r][c - 1] == 1) {
                    grid[r][c - 1] = 2
                    queue.addLast(r * cols + c - 1)
                    fresh--
                    rottedThisRound = true
                }
            }

            if (rottedThisRound) time++
            if (fresh == 0) return time
        }

        return -1
    }
}