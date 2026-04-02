package neetcode.trie

class TrieNode {
    val children = arrayOfNulls<TrieNode>(26)
    var endOfWord = false
}

class PrefixTree {
    private val root = TrieNode()

    fun insert(word: String) {
        var current = root
        word.forEach { c ->
            val i = c - 'a'
            if(current.children[i] == null){
                current.children[i] = TrieNode()
            }
            current = current.children[i]!!
        }
        current.endOfWord = true
    }

    fun search(word: String): Boolean {
        var cur = root
        word.forEach { c ->
            val i = c - 'a'
            if (cur.children[i] == null) {
                return false
            }
            cur = cur.children[i]!!
        }
        return cur.endOfWord
    }

    fun startsWith(prefix: String): Boolean {
        var cur = root
        prefix.forEach { c ->
            val i = c - 'a'
            if (cur.children[i] == null) {
                return false
            }
            cur = cur.children[i]!!
        }
        return true
    }
}
