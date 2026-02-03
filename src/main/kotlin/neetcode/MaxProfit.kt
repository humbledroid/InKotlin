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

    fun maxProfit2(prices: IntArray): Int {
        var maxProfit = 0
        var minBuy = Int.MAX_VALUE

        for (i in prices) {
            maxProfit = maxOf(maxProfit, i - minBuy)
            minBuy = minOf(minBuy, i)
        }
        return maxProfit
    }

}

fun main() {
    val maxProfit = MaxProfit()
    println(maxProfit.maxProfit2(intArrayOf(5, 1, 5, 6, 7, 1, 10)))
}