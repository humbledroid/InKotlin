# Key Takeaways 🔑

1. Cancellations in Kotlin are **cooperative**. This means that our coroutines need to occasionally take a break from their work and look up to see if they've been cancelled.
2. Suspending functions in the `kotlinx.coroutines` library, such as `delay()` or `yield()`, check for cancellations, so your coroutine can call them to see if it's been cancelled. Alternatively, you can call `ensureActive()`.
3. When a coroutine has been cancelled, the functions above will throw a `CancellationException`.
4. `CancellationException` is a subtype of `IllegalStateException`, `RuntimeException`, `Exception`, and `Throwable`, so be careful not to accidentally catch and swallow a `CancellationException`.
5. If you need to catch an `IllegalStateException` (or other supertype), be sure to handle `CancellationException` separately by _rethrowing_ it.
6. As with any other kinds of exceptions, you need to be careful to clean up any open resources upon cancellation. Use try-finally or the `use()` function to do that.
7. Avoid calling suspending functions within your `finally` blocks. If the coroutine has been cancelled, that suspending function might throw `CancellationException` again. If you absolutely need to, you can wrap the contents of your `finally` block with `withContext(NonCancellable) { ... }` to prevent `CancellationException` from affecting the code in your `finally` block.
