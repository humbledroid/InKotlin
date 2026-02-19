package neetcode.revision

class MinimumInRotatedArray {
    fun findMin(nums: IntArray): Int {
        var left = 0
        var right = nums.size - 1
        while (left < right) {
            val mid = left + (right - left) / 2
            if (nums[mid] < nums[right]) {
                right = mid
            } else {
                left = mid + 1
            }
        }
        return nums[left]
    }
}

fun main() {
    val minimumInRotatedArray = MinimumInRotatedArray()
    println(minimumInRotatedArray.findMin(intArrayOf(3,4,5,6,1,2)))
}