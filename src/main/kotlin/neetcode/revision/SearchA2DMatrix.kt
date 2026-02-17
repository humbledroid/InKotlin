package neetcode.revision

class SearchA2DMatrix {
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        val rows = matrix.size
        val cols = matrix[0].size
        var l = 0
        var r = rows * cols - 1

        while (l <= r) {
            val m = l + (r - l) / 2
            val row = m / cols
            val col = m % cols
            if (matrix[row][col] == target) {
                return true
            } else if (matrix[row][col] < target) {
                l = m + 1
            } else {
                r = m - 1
            }
        }
        return false
    }
}

fun main() {
    val searchA2DMatrix = SearchA2DMatrix()
    println(
        searchA2DMatrix.searchMatrix(
            arrayOf(
                intArrayOf(1, 2, 4, 8),
                intArrayOf(10, 11, 12, 13),
                intArrayOf(14, 20, 30, 40)
            ), 10
        )
    )
}