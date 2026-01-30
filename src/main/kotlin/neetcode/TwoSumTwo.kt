package neetcode

class TwoSumTwo {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        var start = 0
        var end = numbers.size - 1
        while (start < end) {
            val currentSum = numbers[start] + numbers[end]
            when {
                currentSum == target -> {
                    return intArrayOf(start+1, end+1)
                }
                currentSum > target -> {
                    end -= 1
                }
                else -> {
                    start += 1
                }
            }
        }

        return intArrayOf()
    }
}


fun main() {
    val twoSumTwo = TwoSumTwo()
    println(twoSumTwo.twoSum(intArrayOf(1,2,3,4), 3).contentToString())
}