package neetcode.backtracking

class Subsets {
    fun subsets(nums: IntArray): List<List<Int>> {
        val res = mutableListOf<List<Int>>(listOf())

        for (num in nums) {
            val n = res.size
            for (i in 0 until n) {
                val newSubset = res[i].toMutableList()
                newSubset.add(num)
                res.add(newSubset)
            }
        }

        return res
    }
}