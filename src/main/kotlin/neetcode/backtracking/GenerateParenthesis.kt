package neetcode.backtracking

class GenerateParenthesis {
    fun generateParenthesis(n: Int): List<String> {
        val res = Array(n + 1) { mutableListOf<String>() }
        res[0] = mutableListOf("")

        for (k in 1..n) {
            for (i in 0 until k) {
                for (left in res[i]) {
                    for (right in res[k-i-1]) {
                        res[k].add("($left)$right")
                    }
                }
            }
        }

        return res[n]
    }
}