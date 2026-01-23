package neetcode

class GroupAnagram {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        strs.forEach { item ->
            val key = item.toCharArray().sorted().joinToString("")
            result.getOrPut(key) { mutableListOf() }.add(item)
        }

        return result.values.toList()
    }
}

fun main(){
    val groupAnagram = GroupAnagram()
    println(groupAnagram.groupAnagrams(arrayOf("act","pots","tops","cat","stop","hat")))
}