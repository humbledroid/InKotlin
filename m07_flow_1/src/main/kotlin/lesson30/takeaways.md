# Key Takeaways 🔑

1. The rules of structured concurrency are still in effect when using a Flow.
2. Similarly, cancellation continues to be cooperative, even when the coroutine is running a Flow.
3. When you need to discontinue a Flow, do so by using a limiting operator - such as `take()`, `takeWhile {}`, `first()`, `any {}`, `all {}`, or `none {}` - rather by cancelling the coroutine that's running the Flow.
4. The `emit()` function is cancellation-aware when using the `flow {}` builder function.
5. When using `flowOf()` or `asFlow()`, the `emit()` function will _not_ be cancellation-aware. However, we can make it so by adding the `cancellable()` operator to the flow chain.
6. As always, we can use try-finally to close any open resources when the coroutine is cancelled.
7. Alternatively, we can use the `onCompletion {}` operator to close resources or do any other finalizing work.
