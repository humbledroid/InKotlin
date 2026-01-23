package neetcode

class GroupAnagram {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        strs.forEach { item ->
            val key = makeAkey(item)
            result.getOrPut(key) { mutableListOf() }.add(item)
        }

        return result.values.toList()
    }

    private fun makeAkey(input: String): String {
        val keyArray = IntArray(26) {0}
        input.forEach {
            keyArray[it - 'a']++
        }

        return keyArray.joinToString("#")
    }
}

fun main(){
    val groupAnagram = GroupAnagram()
    println(groupAnagram.groupAnagrams(arrayOf("act","pots","tops","cat","stop","hat")))
}