package neetcode.backtracking

class CombinationSum {
    fun combinationSum(nums: IntArray, target: Int): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        nums.sort()

        fun dfs(i: Int, cur: MutableList<Int>, total: Int) {
            if (total == target) {
                res.add(ArrayList(cur))
                return
            }

            for (j in i until nums.size) {
                if (total + nums[j] > target) {
                    return
                }
                cur.add(nums[j])
                dfs(j, cur, total + nums[j])
                cur.removeAt(cur.size - 1)
            }
        }

        dfs(0, mutableListOf(), 0)
        return res
    }
}