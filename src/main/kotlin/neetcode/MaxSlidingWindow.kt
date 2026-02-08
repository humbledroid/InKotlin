package neetcode

class MaxSlidingWindow {
    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        val result = mutableListOf<Int>()

        for (i in 0..nums.size - k) {
            var maxI = nums[i]

            for (j in i until i+k) {
                maxI = maxOf(maxI, nums[j])
            }

            result.add(maxI)
        }


        return result.toIntArray()
    }
}

fun main() {
    val maxSlidingWindow = MaxSlidingWindow()
    println(maxSlidingWindow.maxSlidingWindow(intArrayOf(1,2,1,0,4,2,6), k = 3))
}