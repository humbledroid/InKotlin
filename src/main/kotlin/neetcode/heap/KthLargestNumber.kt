package neetcode.heap

class KthLargestNumber {
    // the sorting way :face_palm:
    fun findKthLargest(nums: IntArray, k: Int): Int {
        nums.sort()
        return nums[nums.size - k]
    }
}