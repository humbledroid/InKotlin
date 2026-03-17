package neetcode.heap

class KthLargest(val k: Int, nums: IntArray) {
    private val input = nums.toMutableList()
    
    fun add(`val`: Int): Int {
        input.add(`val`)
        input.sort()
        return input[input.size - k]
    }
}
