package neetcode.heap

import java.util.PriorityQueue

class Twitter {
    private var count = 0
    private val tweetMap = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
    private val followMap = mutableMapOf<Int, MutableSet<Int>>()

    fun postTweet(userId: Int, tweetId: Int) {
        if (!tweetMap.containsKey(userId)) {
            tweetMap[userId] = mutableListOf()
        }
        val tweets = tweetMap[userId]!!
        tweets.add(Pair(count, tweetId))
        if (tweets.size > 10) {
            tweets.removeAt(0)
        }
        count--
    }

    fun getNewsFeed(userId: Int): List<Int> {
        val res = mutableListOf<Int>()
        if (!followMap.containsKey(userId)) {
            followMap[userId] = mutableSetOf()
        }
        followMap[userId]!!.add(userId)
        val minHeap = PriorityQueue<List<Int>> { a, b -> a[0].compareTo(b[0]) }
        if (followMap[userId]!!.size >= 10) {
            val maxHeap = PriorityQueue<List<Int>> { a, b -> a[0].compareTo(b[0]) }
            for (fId in followMap[userId]!!) {
                if (!tweetMap.containsKey(fId)) continue
                val tweets = tweetMap[fId]!!
                if (tweets.isEmpty()) continue
                val idx = tweets.size - 1
                val (c, tId) = tweets[idx]
                maxHeap.offer(listOf(-c, tId, fId, idx - 1))
                if (maxHeap.size > 10) {
                    maxHeap.poll()
                }
            }
            while (maxHeap.isNotEmpty()) {
                val (negCount, tId, fId, nextIdx) = maxHeap.poll()
                val realCount = -negCount
                minHeap.offer(listOf(realCount, tId, fId, nextIdx))
            }
        } else {
            for (fId in followMap[userId]!!) {
                if (!tweetMap.containsKey(fId)) continue
                val tweets = tweetMap[fId]!!
                if (tweets.isEmpty()) continue
                val idx = tweets.size - 1
                val (c, tId) = tweets[idx]
                minHeap.offer(listOf(c, tId, fId, idx - 1))
            }
        }

        while (minHeap.isNotEmpty() && res.size < 10) {
            val (c, tId, fId, idx) = minHeap.poll()
            res.add(tId)
            if (idx >= 0) {
                val (olderCount, olderTid) = tweetMap[fId]!![idx]
                minHeap.offer(listOf(olderCount, olderTid, fId, idx - 1))
            }
        }

        return res
    }

    fun follow(followerId: Int, followeeId: Int) {
        if (!followMap.containsKey(followerId)) {
            followMap[followerId] = mutableSetOf()
        }
        followMap[followerId]!!.add(followeeId)
    }

    fun unfollow(followerId: Int, followeeId: Int) {
        if (followMap.containsKey(followerId)) {
            followMap[followerId]!!.remove(followeeId)
        }
    }
}