package neetcode

class StringEncDec {
    fun encode(strs: List<String>): String {
        if (strs.isEmpty()) return ""
        val sizes = mutableListOf<String>()
        for (str in strs) {
            sizes.add(str.length.toString())
        }
        return sizes.joinToString(",") + "#" + strs.joinToString("")
    }

    fun decode(str: String): List<String> {
        if (str.isEmpty()) return emptyList()
        return buildList {
            val parts = str.split('#', limit = 2)
            val sizePerStrings = parts[0].split(",")
            val encodedString = parts[1]
            var indexToCutAt = 0

            for(size in sizePerStrings) {
                if(size.isEmpty()) continue
                val lengthOfCurrentCut = size.toInt()
                add(encodedString.substring(indexToCutAt, indexToCutAt+lengthOfCurrentCut))
                indexToCutAt += lengthOfCurrentCut
            }
        }
    }
}


fun main(args: Array<String>) {
    val stringEncDec = StringEncDec()
    val encoded = stringEncDec.encode(listOf("#"))
    println(encoded)
    println(stringEncDec.decode(encoded))

}