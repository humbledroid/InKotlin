# Key Takeaways 🔑

1. The `produce()` function is a convenient channel builder that can send elements into the channel that it created - roughly analogous to the `flow()` builder.
2. The `produce()` function _creates a coroutine_, so it needs to be called upon a `CoroutineScope`.
3. The `produce()` function also _creates a channel_.
4. Inside the lambda passed to `produce()`, call the `send()` function directly - that will send elements into the created channel.
5. It returns a `ReceiveChannel` that can be used by a downstream coroutine.
6. It also _closes_ the channel - more about that later in this unit.
7. Use a **select expression** to _receive from multiple channels_. Whichever channel provides a value first will be handled first.
8. Within a _select expression_, be sure to use `onReceive()` instead of `receive()`!
9. Within a select expression, when multiple channels have an element available, the _first one in the expression_ is chosen. To choose from those channels randomly instead, use the `selectUnbiased()` function rather than the `select()` function.
10. Select expressions also support other functions, including `onSend()`, `onJoin()`, and `onAwait()`. It also includes an `onTimeout()` function.
