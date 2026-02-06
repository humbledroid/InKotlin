package neetcode

class PermutationInString {
    fun checkInclusion(s1: String, s2: String): Boolean {
        val windowsSize = s1.length
        val countArray = IntArray(26){0}
        s1.forEach { countArray[it - 'a']++ }
        var left = 0
        while(left+windowsSize <= s2.length){
            val localArray = IntArray(26){0}
            val substring = s2.substring(left, left+windowsSize)
            substring.forEach {
                localArray[it - 'a']++
            }
            if(localArray.contentHashCode() == countArray.contentHashCode()) {
                return true
            }
            left++
        }
        return false
    }
}

fun main() {
    val permutationInString = PermutationInString()
    println(permutationInString.checkInclusion("adc", "dcda"))
}