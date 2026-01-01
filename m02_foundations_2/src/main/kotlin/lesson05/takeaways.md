# Key Takeaways 🔑

1. `launch()` is used for _fire-and-forget_ side-effecting work that doesn't return a result.
2. `async()` is used for work that produces a _result_.
3. `launch()` returns a `Job` object, which essentially represents the coroutine, providing functions and properties to inspect and manage the coroutine.
4. `async()` returns a `Deferred<T>` object, which is a subtype of `Job`. In addition to the features provided by `Job`, it also has a suspending function named `await()`, which will suspend the coroutine until its result is available.
