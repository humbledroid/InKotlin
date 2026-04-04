package neetcode.revision.trie

import neetcode.trie.TrieNode

class WordDictionaryRev {

//    private val store = mutableListOf<String>()
//
//    fun addWord(word: String) {
//        store.add(word)
//    }
//
//    fun search(word: String): Boolean {
//        for (storedWord in store) {
//            if (storedWord.length != word.length) continue
//            var match = true
//            for (i in storedWord.indices) {
//                if (storedWord[i] != word[i] && word[i] != '.') {
//                    match = false
//                    break
//                }
//            }
//            if (match) return true
//        }
//        return false
//    }

    private val root = TrieNode()

    fun addWord(word: String) {
        var current = root
        for (c in word) {
            val index = c - 'a'
            if (current.children[index] == null) {
                current.children[index] = TrieNode()
            }
            current = current.children[index]!!
        }
        current.endOfWord = true
    }

    fun search(word: String): Boolean {
        return dfs(word, 0, root)
    }

    private fun dfs(word: String, j: Int, root: TrieNode): Boolean {
        var current = root
        for (i in j until word.length) {
            val c = word[i]
            if (c == '.') {
                for (child in current.children) {
                    if (child != null && dfs(word, i + 1, child)) {
                        return true
                    }
                }
                return false
            } else {
                val index = c - 'a'
                if (current.children[index] == null) {
                    return false
                }
                current = current.children[index]!!
            }
        }
        return current.endOfWord
    }
}
