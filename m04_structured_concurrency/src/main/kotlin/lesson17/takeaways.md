# Key Takeaways 🔑

1. We can use the natural breakdown of tasks in an app to guide our decisions about which coroutines should belong to the same scope.
2. In Compose apps, `viewModelScope` is a scope that's tied to the lifecycle of the ViewModel - i.e., typically the lifecycle of the screen.
3. Coroutines building on `viewModelScope` use `Dispatchers.Main` as their dispatcher by default. `Dispatchers.Main` represents the single thread that can update the user interface.
4. Code that will be run on `Dispatchers.Main` should avoid doing any CPU-heavy work or I/O that blocks the thread. Instead, we can use `withContext()` to switch to `Dispatchers.Default` or `Dispatchers.IO`
5. When using the Ktor HTTP client, there's no need to manually switch the context to `Dispatchers.IO` - it does that for us automatically.
6. Ktor server runs each request in a coroutine, so we can call suspending functions directly from the routing functions (e.g., `get()` and `post()`)

We'll return to the Book Tracker app in future units of the course.

# Project 🧑🏽‍💻

Because it's a larger multiplatform project with a few different modules, the Book Tracker app is in its own repository, separate from the main code for this course. Clone it here:

`git clone https://git.sr.ht/~typealias/book-tracker-app`

Start the Ktor service first, and then the Compose app.
