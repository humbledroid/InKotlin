package neetcode

class IsAnagram {
    fun isAnagram(s: String, t: String): Boolean {

        if(s.length != t.length) return false

//        val first = s.toCharArray()
//        val second = t.toCharArray().toMutableList()
//
//        first.forEach {
//            second.remove(it)
//        }
//
//        return second.isEmpty()

        // A failed attempt
//        val mutableMap = mutableMapOf<Char, Int>()
//
//        s.forEach { key ->
//            if(mutableMap.containsKey(key)) {
//                mutableMap[key] = mutableMap[key]!!.plus(1)
//            }else{
//                mutableMap[key] = 1
//            }
//        }
//
//        t.forEach { key ->
//            if(mutableMap.containsKey(key)) {
//                mutableMap[key] = mutableMap[key]!!.plus(1)
//            }else{
//                mutableMap[key] = 1
//            }
//        }
//
//        println(mutableMap.entries.joinToString())
//
//        println(mutableMap.filter { it.value.mod(2) != 0 }.entries.joinToString())
//        return mutableMap.filter { it.value.mod(2) != 0 }.isEmpty()

        val englishLowerCase = IntArray(26){0}

        s.forEach {
            englishLowerCase[it - 'a']++
        }

        t.forEach {
            englishLowerCase[it - 'a']--
        }

        englishLowerCase.forEach {
            if (it != 0) return false
        }
        return true
    }
}


fun main(args: Array<String>) {
    val isAnagram = IsAnagram()
    println(isAnagram.isAnagram("aa", "bb"))
}