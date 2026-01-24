package neetcode

class TopKFrequentElements {
    // Simplest
//    fun topKFrequent(nums: IntArray, k: Int): IntArray {
//        val map = mutableMapOf<Int, Int>()
//        nums.forEach {
//            map[it] = map.getOrDefault(it, 0) + 1
//        }
//
//        return map.keys.sortedByDescending { map[it] }.take(k).toIntArray()
//    }

    // with bucket sort
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        val map = mutableMapOf<Int, Int>()
        nums.forEach {
             map[it] = map.getOrDefault(it, 0) + 1
        }

        println(nums.toString())

        val buckets = MutableList<MutableList<Int>>(nums.size+1) {mutableListOf()}
        for( (num, count) in map) {
            buckets[count].add(num)
        }

        val res = mutableListOf<Int>()

        for (i in buckets.size - 1 downTo 0) {
            for(num in buckets[i]) {
                res.add(num)
                if(res.size == k) return res.toIntArray()
            }
        }

        return res.toIntArray()
    }

    // will attempt tomorrow for heap based solution
}

fun main() {
    val topKFrequentElements = TopKFrequentElements()
    println(topKFrequentElements.topKFrequent(intArrayOf(1,1,1,2,2,3), k = 2).joinToString(","))
}