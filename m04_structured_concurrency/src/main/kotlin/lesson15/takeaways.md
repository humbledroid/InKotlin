# Key Takeaways 🔑

1. Structured Concurrency is created by _attaching one coroutine to the scope of another coroutine_.
2. Attaching a coroutine to the scope of another coroutine establishes a parent-child relationship between the coroutines.
3. A parent coroutine will wait for its children to complete _before_ it also completes.
4. A coroutine can be the _child_ of one coroutine and the _parent_ of another.
5. The scope object that a builder is called upon (e.g., `scope.launch { ... }`) is not the same scope object as you'll find in that builder's suspending lambda. The coroutine that the builder creates will include a scope for children to attach to.
6. In practice, the `Job` object usually also implements the `CoroutineScope` interface.
7. When nesting one coroutine builder within another, you can bypass structured concurrency by calling the nested coroutine builder on some _external_ scope, such as `GlobalScope`.
