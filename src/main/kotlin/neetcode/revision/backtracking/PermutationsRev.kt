package neetcode.revision.backtracking

class PermutationsRev {
    val result = ArrayList<List<Int>>()
    fun permute(nums: IntArray): List<List<Int>> {
        val used = BooleanArray(nums.size)
        dfs(nums, ArrayList(), used)
        return result
    }
    fun dfs(nums: IntArray, list: ArrayList<Int>, used: BooleanArray) {
        if (list.size == nums.size) {
            result.add(ArrayList(list))
            return
        }
        for (i in nums.indices) {
            if (used[i]) continue
            list.add(nums[i])
            used[i] = true
            dfs(nums, list, used)
            used[i] = false
            list.removeAt(list.size-1)
        }
    }
}