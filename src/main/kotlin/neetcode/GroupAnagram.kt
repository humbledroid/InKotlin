package neetcode

class GroupAnagram {

    /**
    With sorting and using, sorted item as key
     */
    // fun groupAnagrams(strs: Array<String>): List<List<String>> {
    //     val result = mutableMapOf<String, MutableList<String>>()
    //     strs.forEach { item ->
    //         val key = item.toCharArray().sorted().joinToString("")
    //         result.getOrPut(key) { mutableListOf() }.add(item)
    //     }

    //     return result.values.toList()
    // }

    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val result = mutableMapOf<Int, MutableList<String>>()
        strs.forEach { item ->
            val key = makeAkey(item)
            result.getOrPut(key) { mutableListOf() }.add(item)
        }

        return result.values.toList()
    }

    private fun makeAkey(input: String): Int {
        val keyArray = IntArray(26) {0}
        input.forEach {
            keyArray[it - 'a']++
        }


        //return keyArray.joinToString("#")

        /**
         * here using int via hashcode as key, is significantly faster
         * as we don't have to iterate over the list, to make a string
         * out of it via a delimiter
         */
        return keyArray.contentHashCode()
    }
}

fun main(){
    val groupAnagram = GroupAnagram()
    println(groupAnagram.groupAnagrams(arrayOf("act","pots","tops","cat","stop","hat")))
}