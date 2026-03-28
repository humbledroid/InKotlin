package neetcode.backtracking

class Permutations {
    fun permute(nums: IntArray): List<List<Int>> {
        if (nums.isEmpty()) return listOf(listOf())

        val perms = permute(nums.sliceArray(1 until nums.size))
        val res = mutableListOf<List<Int>>()
        for (p in perms) {
            for (i in 0..p.size) {
                val pCopy = p.toMutableList()
                pCopy.add(i, nums[0])
                res.add(pCopy)
            }
        }
        return res
    }
}