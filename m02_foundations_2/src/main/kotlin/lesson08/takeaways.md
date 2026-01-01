# Key Takeaways 🔑

1. `GlobalScope` is an easy way to get a scope from anywhere in your code, but it's also the _least recommended way_ to get a scope, because it's easy to get memory leaks and resource leaks.
2. When nesting one coroutine builder within the lambda of another, the coroutines will be scoped together naturally.
3. The `CoroutineScope()` factory function allows you to create a scope.
4. The `coroutineScope()` function can be used to call a coroutine builder from within a suspending function.
5. Some frameworks provide scopes ready for you to use. For example, Android's `ViewModel` includes a scope named `viewModelScope`, which is tied to its lifecycle.

Remember - the goal of this lesson was to share ways you can get a scope, so that you can continue to call `launch()` or `async()` as you're learning about coroutines. Later in this course, when we look at _structured concurrency_, we'll cover more about the trade-offs involved with these options.
