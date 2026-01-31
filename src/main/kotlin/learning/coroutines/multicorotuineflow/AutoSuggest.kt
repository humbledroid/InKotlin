package learning.coroutines.multicorotuineflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.milliseconds



@OptIn(FlowPreview::class)
fun main(){
    val searchTerm = "adventure"

    val textBoxValue = flow {
        searchTerm.indices.forEach {
            val typed = searchTerm.take(it+1)
            delay(100.milliseconds)
            println("🧑🏽‍💻 User has typed \"$typed\"")
            emit(typed)
        }
    }

    runBlocking {
        textBoxValue.debounce(250.milliseconds).collect {
            launch(Dispatchers.Default) {
                println("🔍 Searching for \"$it\"...")
                val results = Service.searchFor(it)
                println("📚 Found ${results.size} results for $it")
            }
        }
    }
}


object Service {
    private var callCount = 0

    suspend fun searchFor(term: String): List<Book> {
        println("📞 Service was called ${++callCount} times.")
        delay(300.milliseconds)
        val results = bookCatalog.filter { it.title.contains(term, ignoreCase = true) }
        return results
    }


    private val bookCatalog: List<Book> = listOf(
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
}

data class Book(val id: String, val title: String, val author: String, val cover: String = "/img/$id.jpg")
