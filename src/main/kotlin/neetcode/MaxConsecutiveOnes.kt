package neetcode

class MaxConsecutiveOnes {
    fun longestOnes(nums: IntArray, k: Int): Int {
        var result = 0
        var left = 0
        val freqMap = mutableMapOf<Int, Int>()

        for (right in nums.indices){
            freqMap[nums[right]] = freqMap.getOrDefault(nums[right], 0) + 1
            while(freqMap.getOrDefault(0, 0) > k){
                freqMap[nums[left]] = freqMap.getOrDefault(nums[left], 0) - 1
                left++
            }
            result = maxOf(result, (right-left+1))
        }

        return result
    }

    fun longestOnes2(nums: IntArray, k: Int): Int {
        var result = 0
        var zeroCount = 0
        var left = 0

        for(right in nums.indices){
            if(nums[right] == 0){
                zeroCount++
            }

            while(zeroCount > k){
                if(nums[left] == 0){
                    zeroCount--
                }
                left++
            }

            result = maxOf(result, (right-left+1))
        }

        return result
    }
}


fun main() {
    val maxConsecutiveOnes = MaxConsecutiveOnes()
    println(maxConsecutiveOnes.longestOnes2(intArrayOf(0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1), 3))
}