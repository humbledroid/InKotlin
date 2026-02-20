package neetcode

class TimeMap() {
    private val storage = HashMap<String, MutableList<Pair<String, Int>>>()

    fun set(key: String, value: String, timestamp: Int) {
        storage.getOrPut(key) {
            mutableListOf()
        }.add(value to timestamp)
    }

    fun get(key: String, timestamp: Int): String {
        var result = ""
        val values = storage[key] ?: return result
        var left = 0
        var right = values.size - 1

        while (left <= right) {
            val mid = (left + right) / 2
            if (values[mid].second <= timestamp) {
                result = values[mid].first
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        return result
    }
}


/**
 * Your TimeMap object will be instantiated and called as such:
 * var obj = TimeMap()
 * obj.set(key,value,timestamp)
 * var param_2 = obj.get(key,timestamp)
 */