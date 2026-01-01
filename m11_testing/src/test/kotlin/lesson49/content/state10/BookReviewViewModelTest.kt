@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson49.content.state10

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

class BookReviewViewModelTest {
    @Test
    fun `submits a review to the repository`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))

        val book = Book("12345", "The Malt Shop Caper")
        val repository = ReviewRepositoryDouble()
        val subject = BookReviewViewModel(book, repository)

        val rating = 5
        val comment = "This was a great book!"

        subject.setRating(rating)
        subject.setComment(comment)
        subject.submitReview()

        advanceUntilIdle()

        assertThat(repository.submitted)
            .isEqualTo(Review(book.id, rating, comment))

        Dispatchers.resetMain()
    }
}

class ReviewRepositoryDouble : ReviewRepository {
    var submitted: Review? = null
        private set

    override suspend fun submitReview(review: Review) {
        delay(1.seconds)
        submitted = review
    }
}
