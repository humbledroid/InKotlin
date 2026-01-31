package neetcode


/**
 * Given an integer array nums, return all the triplets
 * [nums[i], nums[j], nums[k]] where nums[i] + nums[j] + nums[k] == 0,
 * and the indices i, j and k are all distinct.
 *
 * The output should not contain any duplicate triplets.
 * You may return the output and the triplets in any order.
 */

class ThreeSum {

    fun threeSum(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val targetSum = 0

        val sortedInput = nums.sorted()

        for(i in 0 until sortedInput.size - 2) {
            if (i > 0 && sortedInput[i] == sortedInput[i - 1]) continue
            val a = sortedInput[i]
            val balanceTarget = targetSum - a
            twoSum(a,sortedInput.subList(i + 1, sortedInput.size).toIntArray(), balanceTarget, result)
        }
        return result
    }
    private fun twoSum(a: Int, nums: IntArray, target: Int, results: MutableList<List<Int>>) {
        var start = 0
        var end = nums.size - 1
        while (start < end) {
            val currentSum = nums[start] + nums[end]
            when {
                currentSum == target -> {
                    results.add(listOf(a, nums[start], nums[end]))
                    start += 1
                    while (start < end && nums[start] == nums[start - 1]) start++
                }
                currentSum > target -> {
                    end -= 1
                }
                else -> {
                    start += 1
                }
            }
        }
    }
}

fun main() {
    val threeSum = ThreeSum()
    println(threeSum.threeSum(intArrayOf(-1,0,1,2,-1,-4)).joinToString(","))
}