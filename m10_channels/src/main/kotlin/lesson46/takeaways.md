# Key Takeaways 🔑

1. Flows and Channels can work together, so you can always use the best tool for the job.
2. To send a channel's elements into a flow, use the `receiveAsFlow()` function.
3. `receiveAsFlow()` fans out the work to each collector, so if you have more than one collector, each element will be delivered to only _one_ collector.
4. Use `consumeAsFlow()` rather than `receiveAsFlow()` whenever you want to ensure that there's only ever one collector.
5. To send a flow's values into a channel, use the `produceIn()` function. This function starts a coroutine and has a buffer with the default buffer size.
6. Because of _context preservation_, inside a flow builder's body, we're not able to directly emit values from another coroutine or context.
7. The `channelFlow()` builder function allows us to emit values into a flow by sending them through a channel to the original context. Just remember to call `send()` rather than `emit()`.
8. The `callbackFlow()` builder function is useful for wrapping a callback-based API into a flow, especially when it requires some shutdown work. `callbackFlow()` is identical to `channelFlow()` except that it _requires_ us to call the `awaitClose()` function.
