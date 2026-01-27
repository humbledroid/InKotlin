package neetcode

class PrefixSuffixSum {
    fun prefixSum(input: IntArray): IntArray {
        val prefixSum = IntArray(input.size)
        prefixSum[0] = input[0]

        for(i in 1 until input.size) {
            prefixSum[i] = input[i] + prefixSum[i - 1]
        }

        return prefixSum
    }

    fun suffixSum(input: IntArray): IntArray {
        val suffixSum = IntArray(input.size)
        suffixSum[input.size-1] = input[input.size-1]

        for (i in input.size - 2 downTo 0) {
            suffixSum[i] = input[i] + suffixSum[i + 1]
        }

        return suffixSum
    }
}


fun main(args: Array<String>) {
    val prefixSuffixSum = PrefixSuffixSum()
    val input = intArrayOf(1,2,4,6)

    val correctPrefixSum = intArrayOf(1,3,7,13)
    val correctSuffixSum = intArrayOf(13,12,10,6)

    val prefixResult = prefixSuffixSum.prefixSum(input)
    val suffixResult = prefixSuffixSum.suffixSum(input)


    println(prefixResult.joinToString(","))
    println("------------------")
    println(suffixResult.joinToString(","))


    println(correctSuffixSum.contentEquals(suffixResult))
    println(correctPrefixSum.contentEquals(prefixResult))

}