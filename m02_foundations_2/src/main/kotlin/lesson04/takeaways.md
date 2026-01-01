# Key Takeaways 🔑

1. A **scope** is a group of related coroutines.
2. Most coroutines that we create need to be part of a scope.
3. The `launch()` and `async()` builders have a `CoroutineScope` receiver parameter. In other words, you can only call `launch()` and `async()` upon a `CoroutineScope` object.
4. The receiver within a coroutine builder's lambda is a `CoroutineScope`. So, when you nest one builder within the lambda of another, you can call `launch()` or `async()` without explicitly specifying the scope.
