package neetcode

class FindDuplicate {
    fun findDuplicate(nums: IntArray): Int {
        for (num in nums) {
            val negativeIndex = Math.abs(num) - 1
            if (nums[negativeIndex] < 0) {
                return Math.abs(num)
            }
            nums[negativeIndex] *= -1
        }

        return -1
    }
}

fun main() {
    val findDuplicate = FindDuplicate()
    println(findDuplicate.findDuplicate(intArrayOf(1,2,3,4,5,4)))
}