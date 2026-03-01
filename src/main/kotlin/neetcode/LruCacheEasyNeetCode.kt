package neetcode

class LRUCache(capacity: Int) {

    class LruCache<K, V>(private val capacity: Int) : LinkedHashMap<K, V>(capacity, 0.75f, true) {
        override fun removeEldestEntry(eldest: Map.Entry<K, V>?): Boolean {
            return size > capacity
        }
    }

    val lruCache = LruCache<Int, Int>(capacity = capacity)

    fun get(key: Int): Int {
        return lruCache.get(key) ?: -1
    }

    fun put(key: Int, value: Int) {
        lruCache[key] = value
    }

}
