package neetcode

import java.util.LinkedHashMap

internal class LruCache<K, V>(private val capacity: Int) : LinkedHashMap<K, V>(capacity, 0.75f, true) {
    override fun removeEldestEntry(eldest: Map.Entry<K, V>?): Boolean {
        return size > capacity
    }
}

fun main() {
    val lruCache = LruCache<String, Int>(capacity = 2)

    lruCache["key"] = 1
    lruCache["key2"] = 3
    lruCache["key4"] = 3

    lruCache["key5"] = 3

    val k = lruCache["key4"]

    println(lruCache.entries.joinToString(","))

}