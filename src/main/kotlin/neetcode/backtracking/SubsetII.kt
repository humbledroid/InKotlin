package neetcode.backtracking

class SubsetII {
    fun subsetsWithDup(nums: IntArray): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        nums.sort()

        fun backtrack(i: Int, subset: MutableList<Int>) {
            res.add(ArrayList(subset))

            for (j in i until nums.size) {
                if (j > i && nums[j] == nums[j - 1]) {
                    continue
                }
                subset.add(nums[j])
                backtrack(j + 1, subset)
                subset.removeAt(subset.size - 1)
            }
        }

        backtrack(0, mutableListOf())
        return res
    }
}