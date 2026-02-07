package neetcode

class MinimumWindow {
    fun minWindow(s: String, t: String): String {
        if(t.isEmpty()) return ""

        val frequencyMap = hashMapOf<Char, Int>()
        t.forEach {
            frequencyMap[it] = frequencyMap.getOrDefault(it, 0) + 1
        }
        val window = hashMapOf<Char, Int>()
        var have = 0

        val need = frequencyMap.size

        val indexesForSubstring = IntArray(2) {-1}
        var resLen = Int.MAX_VALUE
        var left = 0

        for(i in s.indices) {
            val currentChar = s[i]
            window[currentChar] = window.getOrDefault(currentChar, 0) + 1

            if(frequencyMap.containsKey(currentChar) && window[currentChar] == frequencyMap[currentChar]) {
                have++
            }

            while(have == need) {
                if((i - left + 1) < resLen){
                    indexesForSubstring[0] = left
                    indexesForSubstring[1] = i
                    resLen = i - left + 1
                }

                window[s[left]] = window.getOrDefault(s[left], 0) - 1

                if(frequencyMap.containsKey(s[left]) && (window[s[left]] ?: 0 ) < frequencyMap.getValue(s[left])) {
                    have--
                }

                left++
            }
        }
        return if(indexesForSubstring[0] == -1) "" else s.substring(indexesForSubstring[0], indexesForSubstring[1]+1)
    }
}

fun main() {
    val minimumWindow = MinimumWindow()
    println(minimumWindow.minWindow(s = "OUZODYXAZV", t = "XYZ"))
}