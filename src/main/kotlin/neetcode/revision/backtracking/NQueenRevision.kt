package neetcode.revision.backtracking

class NQueenRevision {
    fun solveNQueens(n: Int): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val board = Array(n) { CharArray(n) { '.' } }

        fun backtrack(r: Int, col: Int, posDiag: Int, negDiag: Int){
            if(r==n){
                result.add(board.map { it.joinToString("") })
                return
            }

            for (c in 0 until n){
                if((col and (1 shl c)) != 0 || (posDiag and (1 shl (r+c))) != 0 || (negDiag and (1 shl (r - c +n))) != 0){
                    continue
                }
                val newCol = col xor (1 shl c)
                val newPosDiag = posDiag xor (1 shl (r+c))
                val newNegDiag = negDiag xor (1 shl (r-c+n))
                board[r][c] = 'Q'
                backtrack(r+1, newCol, newPosDiag, newNegDiag)

                board[r][c] = '.'
            }
        }
        backtrack(0,0,0,0)
        return result
    }
}