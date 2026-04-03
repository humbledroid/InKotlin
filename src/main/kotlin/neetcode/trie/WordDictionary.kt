package neetcode.trie

class WordDictionary {

    private val store = mutableListOf<String>()

    fun addWord(word: String) {
        store.add(word)
    }

    fun search(word: String): Boolean {
        for (storedWord in store) {
            if (storedWord.length != word.length) continue
            var match = true
            for (i in storedWord.indices) {
                if (storedWord[i] != word[i] && word[i] != '.') {
                    match = false
                    break
                }
            }
            if (match) return true
        }
        return false
    }
}
