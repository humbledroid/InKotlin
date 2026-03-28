package neetcode.backtracking

/**
 * See fig [permutations.png]
 */

class Permutations {
//    fun permute(nums: IntArray): List<List<Int>> {
//        if (nums.isEmpty()) return listOf(listOf())
//
//        val perms = permute(nums.sliceArray(1 until nums.size))
//        val res = mutableListOf<List<Int>>()
//        for (p in perms) {
//            for (i in 0..p.size) {
//                val pCopy = p.toMutableList()
//                pCopy.add(i, nums[0])
//                res.add(pCopy)
//            }
//        }
//        return res
//    }

    private val res = mutableListOf<List<Int>>()

    fun permute(nums: IntArray): List<List<Int>> {
        backtrack(nums, 0)
        return res
    }

    private fun backtrack(nums: IntArray, idx: Int) {
        if (idx == nums.size) {
            res.add(nums.toList())
            return
        }
        for (i in idx until nums.size) {
            nums.swap(idx, i)
            backtrack(nums, idx + 1)
            nums.swap(idx, i)
        }
    }

    private fun IntArray.swap(i: Int, j: Int) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }
}