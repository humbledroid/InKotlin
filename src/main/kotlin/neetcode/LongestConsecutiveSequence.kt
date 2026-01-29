package neetcode

class LongestConsecutiveSequence {
    fun longestConsecutive(nums: IntArray): Int {
        val setOfNumbers = nums.toSet()
        var max = 0
        for(num in setOfNumbers) {
            if((num-1) in setOfNumbers ) continue
            var length = 1
            while(num+length in setOfNumbers) {
                length++
            }

            max = maxOf(max, length)
        }

        return max
    }
}


fun main() {
    val longestConsecutiveSequence = LongestConsecutiveSequence()
    println(longestConsecutiveSequence.longestConsecutive(intArrayOf(2,20,4,10,3,4,5)))
}