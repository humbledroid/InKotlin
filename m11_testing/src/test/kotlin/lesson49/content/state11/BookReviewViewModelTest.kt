@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson49.content.state11

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherExtension(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext) = Dispatchers.setMain(dispatcher)
    override fun afterEach(context: ExtensionContext) = Dispatchers.resetMain()
}

class BookReviewViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @Test
    fun `submits a review to the repository`() = runTest {
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
