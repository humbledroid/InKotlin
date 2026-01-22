package neetcode

class TwoSum {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        for (i in nums.indices) {
            for (j in i + 1 until nums.size) {
                if (nums[i] + nums[j] == target) {
                    return intArrayOf(i, j)
                }
            }
        }

        return intArrayOf()
    }
}

fun main() {
    val twoSum = TwoSum()
    println(twoSum.twoSum(intArrayOf(3,4,5,6), target = 7).joinToString())
}