package neetcode

class LongestRepeatingCharacterReplacement {
    fun characterReplacement(s: String, k: Int): Int {
        var longest = 0

        for (i in s.indices) {
            val count = hashMapOf<Char, Int>()
            var maxF = 0
            for (j in i until s.length) {
                count[s[j]] = count.getOrDefault(s[j], 0) + 1
                maxF = maxOf(maxF, count[s[j]]!!)
                if((j-i+1)-maxF <= k) {
                    longest = maxOf(longest, j-i+1)
                }
            }
        }

        return longest
    }
}

fun main() {
    val longestRepeatingCharacterReplacement = LongestRepeatingCharacterReplacement()
    println(longestRepeatingCharacterReplacement.characterReplacement("ABAB", 2))
}