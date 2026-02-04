package neetcode

class LongestSubstringWithoutRCharacter {
    fun lengthOfLongestSubstring(s: String): Int {
        if(s.isEmpty()) return 0
        val seenSet = HashSet<Char>()
        var left = 0
        var maxLength = 0

        for (right in s.indices) {
            while(s[right] in seenSet) {
                seenSet.remove(s[left])
                left++
            }

            seenSet.add(s[right])
            maxLength = maxOf(maxLength, (right-left)+1)
        }
        return maxLength
    }
}

fun main() {
    val longestSubstringWithoutRCharacter = LongestSubstringWithoutRCharacter()
    println(longestSubstringWithoutRCharacter.lengthOfLongestSubstring("zxyzxyz"))
}