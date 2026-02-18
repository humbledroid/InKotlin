package neetcode.revision

class SearchA2DMatrixRev {
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        val rows = matrix.size
        val columns = matrix[0].size
        var left = 0
        var right = rows * columns - 1


        while(left <= right) {
            val middle = left + (right - left) / 2
            val row = middle / columns
            var column = middle % columns
            if(matrix[row][column] == target) {
                return true
            } else if(matrix[row][column] < target) {
                left = middle + 1
            } else {
                right = middle - 1
            }
        }

        return false
    }
}

fun main() {
    val searchA2DMatrix = SearchA2DMatrixRev()
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