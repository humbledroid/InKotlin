package neetcode

import java.util.*

class Anagram {
    fun isAnagram(s: String, t: String): Boolean {

        if(s.length!=t.length) return false

        val intArray = IntArray(26)

        for (i in s.indices){
            intArray[s[i].code-'a'.code]++
            intArray[t[i].code-'a'.code]--
        }

        for (c in intArray){
            if(c!=0){
                return false
            }
        }

        return true
    }

    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val ans = mutableMapOf<String, MutableList<String>>()
        val intArray = IntArray(26)
        strs.forEach {
            Arrays.fill(intArray, 0)
            for (c in it){
                intArray[c-'a']++
            }

            val sb = StringBuilder("")
            for(i in 0 until 26){
                sb.append("#")
                sb.append(intArray[i])
            }
            val key = sb.toString()
            if(!ans.containsKey(key)){
                ans.put(key, mutableListOf())
            }
            ans[key]?.add(it)
        }

        return ans.values.toList()
    }

    fun detectCycle(head: ListNode?): ListNode? {
        if(head?.next == null){
            return  null
        }

        var slow = head
        var fast = head
        var start = head
        var hasCycle = false

        while(fast?.next!=null || fast?.next?.next!=null){
            slow = slow?.next
            fast = fast.next?.next
            if(slow==fast){
                hasCycle = true
                break
            }
        }

        if(hasCycle){
            while(slow!=start){
                slow = slow?.next
                start = start?.next
            }
            return start
        }
        return null
    }

    fun asteroidsDestroyed(mass: Int, asteroids: IntArray): Boolean {
        val sorted = asteroids.sorted()
        var massSaved = mass
        for (a in sorted) {
            if(massSaved < a){
                massSaved += a
            }else{
                return false
            }
        }
        return true
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun main(){
    val l = Anagram()
    println(l.groupAnagrams(arrayOf("eat","tea","tan","ate","nat","bat")))
}