# Key Takeaways 🔑

1. `CoroutineExceptionHandler` is a _context element_ that acts as a _last-resort exception handler_, designed to log, report, or alert about the exception rather than recover from it.
2. A handler can be created by calling the `CoroutineExceptionHandler()` factory function, or by manually implementing the `CoroutineExceptionHandler` interface.
3. A coroutine exception handler is only effective when used by a `launch()` builder at the _root of the coroutine hierarchy_, or at the _root of a supervised scope_. Although I didn't mention it in the video, `CoroutineExceptionHandler` is _inherited_ by child coroutines, just like any other context elements, so even if you pass it to a non-root coroutine, it could become effective when inherited by a coroutine further down the hierarchy that's just below a supervisor scope.
4. On the JVM, you can use `Thread.UncaughtExceptionHandler` to provide a default last-resort exception handler for your coroutines.
5. On the JVM, you can apply additional default last-resort exception handlers by using Java's _Service Loader_ tooling.
