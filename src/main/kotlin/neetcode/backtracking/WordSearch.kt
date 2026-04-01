package neetcode.backtracking

class WordSearch {
    fun exist(board: Array<CharArray>, word: String): Boolean {
        val rows = board.size
        val cols = board[0].size

        fun dfs(r: Int, c: Int, i: Int): Boolean {
            if (i == word.length) return true
            if (r < 0 || c < 0 || r >= rows || c >= cols ||
                board[r][c] != word[i] || board[r][c] == '#'
            ) {
                return false
            }
            val temp = board[r][c]
            board[r][c] = '#'
            val res = dfs(r + 1, c, i + 1) || dfs(r - 1, c, i + 1) || dfs(r, c + 1, i + 1) || dfs(r, c - 1, i + 1)
            board[r][c] = temp
            return res
        }

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (dfs(r, c, 0)) {
                    return true
                }
            }
        }
        return false
    }
}

fun main() {
    val wordSearch = WordSearch()
    println(wordSearch.exist(
        arrayOf(
            charArrayOf('A', 'B', 'C', 'D'),
            charArrayOf('S', 'A', 'A', 'T'),
            charArrayOf('A', 'C', 'A', 'E')
        ), word = "CAT"
    ))
}