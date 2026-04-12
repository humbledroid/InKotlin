package neetcode.revision.graph

class Solution {

    fun pacificAtlantic(heights: Array<IntArray>): List<List<Int>> {
        val rows = heights.size
        val cols = heights[0].size
        val pac = Array(rows) { BooleanArray(cols) }
        val atl = Array(rows) { BooleanArray(cols) }

        fun dfs(r: Int, c: Int, visit: Array<BooleanArray>, prevHeight: Int) {
            if (r < 0 || c < 0 || r == rows || c == cols ||
                visit[r][c] || heights[r][c] < prevHeight
            ) {
                return
            }
            visit[r][c] = true
            val h = heights[r][c]
            dfs(r + 1, c, visit, h)
            dfs(r - 1, c, visit, h)
            dfs(r, c + 1, visit, h)
            dfs(r, c - 1, visit, h)
        }

        for (c in 0 until cols) {
            dfs(0, c, pac, Int.MIN_VALUE)
            dfs(rows - 1, c, atl, Int.MIN_VALUE)
        }
        for (r in 0 until rows) {
            dfs(r, 0, pac, Int.MIN_VALUE)
            dfs(r, cols - 1, atl, Int.MIN_VALUE)
        }

        val result = ArrayList<List<Int>>()
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (pac[r][c] && atl[r][c]) {
                    result.add(listOf(r, c))
                }
            }
        }
        return result
    }
}