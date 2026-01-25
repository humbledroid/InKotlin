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
        val frequencyMap = mutableMapOf<Int, Int>()
        nums.forEach {
            frequencyMap[it] = frequencyMap.getOrDefault(it, 0) + 1
        }

        val result = mutableListOf<Int>()

        val bucket = MutableList<MutableList<Int>>(nums.size+1){mutableListOf()}

        frequencyMap.forEach { (num, count) ->
            bucket[count].add(num)
        }
        // now we have the bucket, or sort of count sort bucket, where
        // higher index is the most frequent one

        for (i in bucket.size - 1 downTo 0) {
            bucket[i].forEach { n ->
                result.add(n)
                if(result.size == k) return result.toIntArray()
            }
        }

        return result.toIntArray()
    }
}

fun main() {
    val topKFrequentElements = TopKFrequentElements()
    println(topKFrequentElements.topKFrequent(intArrayOf(1,1,1,2,2,3), k = 2).joinToString(","))
}