package neetcode.heap

import java.util.PriorityQueue

class MedianFinder {
//    private val data = mutableListOf<Int>()
//
//    fun addNum(num: Int) {
//        data.add(num)
//    }
//
//    fun findMedian(): Double {
//        data.sort()
//        val n = data.size
//        return if (n % 2 == 1) {
//            data[n / 2].toDouble()
//        } else {
//            (data[n / 2] + data[n / 2 - 1]) / 2.0
//        }
//    }


    private val small = PriorityQueue<Int>(compareByDescending { it }) // max heap
    private val large = PriorityQueue<Int>() // min heap

    fun addNum(num: Int){
        if (large.isNotEmpty() && num > large.peek()){
            large.add(num)
        } else {
            small.add(num)
        }

        if(small.size > large.size + 1) {
            large.add(small.poll())
        }

        if(large.size > small.size + 1) {
            small.add(large.poll())
        }
    }

    fun findMedian(): Double {
        return when {
            small.size > large.size -> small.peek().toDouble()
            large.size > small.size -> large.peek().toDouble()
            else -> (small.peek() + large.peek()) / 2.0
        }
    }
}