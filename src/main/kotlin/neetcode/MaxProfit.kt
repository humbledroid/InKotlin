package neetcode

class MaxProfit {
    fun maxProfit(prices: IntArray): Int {
        var maxProfit = 0

        for (i in prices.indices) {
            for (j in i + 1 until prices.size) {
                val profit = prices[j] - prices[i]
                maxProfit = maxOf(maxProfit, profit)
            }
        }

        return maxProfit
    }

}

fun main() {
    val maxProfit = MaxProfit()
    println(maxProfit.maxProfit(intArrayOf(5, 1, 5, 6, 7, 1, 10)))
}