package neetcode.graph


class DSU(n: Int) {
    val parent: IntArray = IntArray(n) { it }
    val size: IntArray = IntArray(n) { 1 }

    fun find(node: Int): Int {
        var curr = node
        while (parent[curr] != curr) {
            parent[curr] = parent[parent[curr]] // path halving
            curr = parent[curr]
        }
        return curr
    }

    fun union(u: Int, v: Int): Boolean {
        val pu = find(u)
        val pv = find(v)
        if (pu == pv) return false
        if (size[pu] >= size[pv]) {
            size[pu] += size[pv]
            parent[pv] = pu
        } else {
            size[pv] += size[pu]
            parent[pu] = pv
        }
        return true
    }

    fun connected(u: Int, v: Int): Boolean = find(u) == find(v)
}

class SurroundedRegions {
    fun solve(board: Array<CharArray>) {
        val rows = board.size
        val cols = board[0].size
        val border = rows * cols
        val dsu = DSU(rows * cols + 1)

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (board[r][c] != 'O') continue
                val idx = r * cols + c

                if (r == 0 || c == 0 || r == rows - 1 || c == cols - 1) {
                    dsu.union(border, idx)
                }
                if (r + 1 < rows && board[r + 1][c] == 'O') {
                    dsu.union(idx, idx + cols)
                }
                if (c + 1 < cols && board[r][c + 1] == 'O') {
                    dsu.union(idx, idx + 1)
                }
            }
        }

        val borderRoot = dsu.find(border)
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (board[r][c] == 'O' && dsu.find(r * cols + c) != borderRoot) {
                    board[r][c] = 'X'
                }
            }
        }
    }
}