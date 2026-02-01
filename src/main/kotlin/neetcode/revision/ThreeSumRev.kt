package neetcode.revision

class ThreeSumRev {

    fun threeSum(nums: IntArray): List<List<Int>> {
        val sortedInput = nums.sorted()
        val target = 0

        val result = mutableListOf<List<Int>>()

        for(i in 0 until sortedInput.size - 2) {
            if(i>0 && sortedInput[i-1] == sortedInput[i]) continue
            val balanceSum = target - sortedInput[i]
            /**
             * for 3 sum we know how to do two sum
             * so,
             * a+b+c = 0
             * a = sortedInput[i]
             * b+c = target - a , so here we know how to calculate b+c to be target-a via two sum
             */

            twoSum(a = sortedInput[i], target = balanceSum, result = result, subArray = sortedInput.subList(i+1, sortedInput.size))
        }

        return result
    }

    private fun twoSum(a: Int, target: Int, result: MutableList<List<Int>>, subArray: List<Int>) {
        var start = 0
        var end = subArray.size - 1
        while (start < end) {
            val currentSum = subArray[start] + subArray[end]
            when {
                currentSum == target -> {
                    result.add(listOf(a, subArray[start], subArray[end]))
                    start++
                    while(start < end && subArray[start-1] == subArray[start]) {
                        start++
                    }
                }
                currentSum > target -> {
                    end--
                }
                else -> {
                    start++
                }
            }
        }
    }
}

fun main() {
    val threeSum = ThreeSumRev()
    println(threeSum.threeSum(intArrayOf(1,2,3,4,5,6,7)).joinToString(","))
}