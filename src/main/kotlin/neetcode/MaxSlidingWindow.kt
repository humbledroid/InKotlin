package neetcode

class MaxSlidingWindow {
    // O(n)^2 doesnt work that great though
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

    // lets use deque to maintain maximum at the front of a
    // queue for each window
    fun maxSlidingWindowsDeque(nums: IntArray, k: Int): IntArray {
        val result = mutableListOf<Int>()

        val dequeue = ArrayDeque<Int>()
        var left = 0

        for(right in nums.indices) {
            val currentNum = nums[right]
            while(dequeue.isNotEmpty() && nums[dequeue.last()] < currentNum) {
                dequeue.removeLast()
            }

            dequeue.addLast(right)

            if(left > dequeue.first()){
                dequeue.removeFirst()
            }

            if((right+1) >= k){
                result.add(nums[dequeue.first()])
                left+=1
            }
        }

        return result.toIntArray()
    }
}

fun main() {
    val maxSlidingWindow = MaxSlidingWindow()
    println(maxSlidingWindow.maxSlidingWindowsDeque(intArrayOf(1,2,1,0,4,2,6), k = 3).joinToString(","))
}