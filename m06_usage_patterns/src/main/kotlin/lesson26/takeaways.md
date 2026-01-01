# Key Takeaways 🔑

1. When you need to use a library that provides a callback-based API, you can wrap it with a thin adapter layer to convert it to a coroutine-based API.
2. To do this, call the `suspendCoroutine {}` function, or preferably the `suspendCancellableCoroutine {}` function.
3. These functions accept a lambda that has a `Continuation` parameter. That continuation object is used to resume the coroutine once a result is provided.
4. To throw an exception, call the `resumeWithException()` function on the continuation, providing the exception that you want thrown.
