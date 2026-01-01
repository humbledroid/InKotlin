# Key Takeaways 🔑

1. Just as it can be helpful to know which _thread_ was running when a message was logged, it can be helpful to know which _coroutine_ was running at that time.
2. Turn on **debugging mode** with `-Dkotlinx.coroutines.debug`. In IntelliJ, add this to the `VM Options` in the Configuration.
3. When debugging mode is enabled, each created coroutine will get a unique `Long` integer ID, starting at `1` and increasing.
4. Debugging mode also alters the _names of threads_ so that they also include the ID of the coroutine that's running on them.
5. You can assign a name to a coroutine by passing an instance of `CoroutineName` to its builder. This name is included in the thread name when debugging mode is enabled.
6. `CoroutineName` is a context element, so _child coroutines will inherit it from the parent_, unless explicitly set on the child.
7. The coroutine name can be used apart from debugging mode - just use the brackets to pull it off of the coroutine context. For example, `currentCoroutineContext()[CoroutineName]`.
8. Generally, debugging mode should be used in non-production environments, so if you need to temporarily turn it on in production, monitor it to make sure problems aren't caused by the slight overhead that it introduces.
