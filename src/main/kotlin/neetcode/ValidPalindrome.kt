package neetcode

class ValidPalindrome {
    fun isPalindrome(s: String): Boolean {
        val cleanedString = s.filter { it.isLetterOrDigit() }.lowercase()

        var start = 0
        var end = cleanedString.length - 1

        while(start <= end) {
            val startC = cleanedString[start]
            val endC = cleanedString[end]
            if(startC != endC) {
                return false
            }
            start++
            end--
        }
        return true
    }
}

fun main(args: Array<String>) {
    val validPalindrome = ValidPalindrome()
    println(validPalindrome.isPalindrome("Was it a car or a cat I saw?"))
}