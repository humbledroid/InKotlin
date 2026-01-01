# Key Takeaways 🔑

1. **Channels** are used to _communicate_ between coroutines.
2. As with queues, an item passed through a channel is known as an **element**.
3. By default, a call to `send()` on a channel will _suspend_ the coroutine until there's a coroutine attempting to _receive_ from that same channel.
4. And likewise, a call to `receive()` on a channel will suspend the coroutine until there's a coroutine attempting to _send_ into that same channel.
5. A call to the `receive()` function receives only a _single_ element. To receive _multiple_ elements, you can call `receive()` multiple times, but it's better to use a `for` loop on the channel.
6. The `Channel` interface extends two channels interfaces - `SendChannel` and `ReceiveChannel`. This allows us to ensure that the channel is used as expected, where some coroutines could only _send_ into that channel, and other coroutines could only _receive_ from that channel.
7. It's possible for a single coroutine to send into multiple channels, or receive from multiple channels.
