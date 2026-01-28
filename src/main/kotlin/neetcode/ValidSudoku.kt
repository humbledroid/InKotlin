package neetcode

class ValidSudoku {
    fun isValidSudoku(board: Array<CharArray>): Boolean {

        val rows = Array(9) { HashSet<Char>() }
        val columns = Array(9) { HashSet<Char>() }

        val boxes = Array(9) { HashSet<Char>() }

        for(r in 0..8) {
            for (c in 0..8) {
                val char = board[r][c]

                if(char == '.') continue

                val boxIndex = (r/3) * 3 + (c/3)

                if(char in rows[r] || char in columns[c] || char in boxes[boxIndex]) return false

                rows[r].add(char)
                columns[c].add(char)
                boxes[boxIndex].add(char)
            }
        }

        return true
    }
}

fun main() {
    val validSudoku = ValidSudoku()
    val board = parseBoard(input)
    println(validSudoku.isValidSudoku(board))
}

val input = """
 [["1","2",".",".","3",".",".",".","."],
 ["4",".",".","5",".",".",".",".","."],
 [".","9","8",".",".",".",".",".","3"],
 ["5",".",".",".","6",".",".",".","4"],
 [".",".",".","8",".","3",".",".","5"],
 ["7",".",".",".","2",".",".",".","6"],
 [".",".",".",".",".",".","2",".","."],
 [".",".",".","4","1","9",".",".","8"],
 [".",".",".",".","8",".",".","7","9"]]
""".trimIndent()

fun parseBoard(input: String): Array<CharArray> {
    val rows = input.trim()
        .removePrefix("[")
        .removeSuffix("]")
        .split("],")
        .map { it.trim().removePrefix("[").removeSuffix("]") }

    return rows.map { row ->
        row.split(",")
            .map { it.trim().removeSurrounding("\"") }
            .map { it[0] }
            .toCharArray()
    }.toTypedArray()
}