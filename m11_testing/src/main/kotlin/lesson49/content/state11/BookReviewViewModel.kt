package lesson49.content.state11

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookReviewViewModel(
    book: Book,
    private val repository: ReviewRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReviewScreenUiState(book))
    val uiState = _uiState.asStateFlow()

    fun setRating(rating: Int) {
        _uiState.update { it.copy(rating = rating) }
    }

    fun setComment(comment: String) {
        _uiState.update { it.copy(comment = comment) }
    }

    fun submitReview() {
        viewModelScope.launch {
            try {
                val currentBook = _uiState.value.book ?: return@launch
                val rating = _uiState.value.rating ?: return@launch

                val review = Review(
                    bookId = currentBook.id,
                    rating = rating,
                    comment = _uiState.value.comment
                )

                repository.submitReview(review)
                _uiState.update { it.copy(isSaved = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Failed to submit review: ${e.message}") }
            }
        }
    }
}

data class ReviewScreenUiState(
    val book: Book? = null,
    val rating: Int? = null,
    val comment: String = "",
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)

data class Review(val bookId: String, val rating: Int, val comment: String)
data class Book(val id: String, val title: String)

interface ReviewRepository {
    suspend fun submitReview(review: Review)
}
