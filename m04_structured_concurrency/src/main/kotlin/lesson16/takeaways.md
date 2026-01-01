# Key Takeaways 🔑

1. We don't have to nest our coroutine builders within the lambda of another coroutine builder just to get Structured Concurrency.
2. When we need to build a coroutine from a _blocking_ function, we can pass the `CoroutineScope` to it, typically as its receiver.
3. When we need to build a coroutine from a _suspending_ function, we can use the `coroutineScope {}` function.
4. `coroutineScope {}` can be thought of as the _suspending_ version of `runBlocking {}`.
