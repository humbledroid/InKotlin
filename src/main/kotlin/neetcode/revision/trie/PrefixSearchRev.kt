package neetcode.revision.trie

import neetcode.trie.TrieNode

class PrefixSearchRev {
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
        var current = root
        word.forEach { c ->
            val i = c - 'a'
            if (current.children[i] == null) {
                return false
            }
            current = current.children[i]!!
        }
        return current.endOfWord
    }

    fun startsWith(prefix: String): Boolean {
        var current = root
        prefix.forEach { c ->
            val i = c - 'a'
            if (current.children[i] == null) {
                return false
            }
            current = current.children[i]!!
        }
        return true
    }
}