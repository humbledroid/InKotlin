package neetcode

class ValidSudoku {
    fun isValidSudoku(board: Array<CharArray>): Boolean {

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