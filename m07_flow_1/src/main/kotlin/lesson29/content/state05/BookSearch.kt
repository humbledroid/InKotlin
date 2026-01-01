package lesson29.content.state05

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlin.time.Duration.Companion.milliseconds

suspend fun main() {
    search("Adventure")
        .onStart { println("🔍 Starting search...") }
        .onCompletion { println("🔍 Done searching.") }
        .onEmpty { println("❌ No results found.") }
        .collect { println("📕 Found matching book: ${it.title} by ${it.author}") }
}


fun search(term: String) = flow {
    for (book in books) {
        delay(250.milliseconds)
        if (book.title.contains(term) || book.author.contains(term)) {
            emit(book)
        }
    }
}

private val books = listOf(
    Book("1661", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle"),
    Book("1342", "Pride and Prejudice", "Jane Austen"),
    Book("1400", "Great Expectations", "Charles Dickens"),
    Book("41445", "Frankenstein", "Mary Shelley"),
    Book("100", "Complete Works", "William Shakespeare"),
    Book("17157", "Gulliver's Travels", "Jonathan Swift"),
    Book("11", "Alice's Adventures in Wonderland", "Lewis Carroll"),
    Book("74", "The Adventures of Tom Sawyer", "Mark Twain"),
    Book("35", "The Time Machine", "H. G. Wells"),
    Book("103", "Around the World in Eighty Days", "Jules Verne"),
)

data class Book(val id: String, val title: String, val author: String)
