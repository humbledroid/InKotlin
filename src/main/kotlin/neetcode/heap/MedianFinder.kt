package neetcode.heap

class MedianFinder {
    private val data = mutableListOf<Int>()

    fun addNum(num: Int) {
        data.add(num)
    }

    fun findMedian(): Double {
        data.sort()
        val n = data.size
        return if (n % 2 == 1) {
            data[n / 2].toDouble()
        } else {
            (data[n / 2] + data[n / 2 - 1]) / 2.0
        }
    }
}