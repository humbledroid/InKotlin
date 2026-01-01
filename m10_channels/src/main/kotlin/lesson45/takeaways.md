# Key Takeaways 🔑

1. Once there are no more elements to send into a channel, that channel can be **closed** by calling its `close()` function.
2. The sending side of a channel is sometimes referred to as a **producer**, and the receiving side is known as a **consumer**.
3. The `close()` function is part of the `SendChannel` interface.
4. The code that _sends_ elements into a channel is responsible for _closing_ that channel whenever there are no more elements to send.
5. A consumer can't explicitly close a channel, but it can **cancel** a channel, signaling that it will not receive any more elements.
6. After _closing_ a channel, any elements in the channel's buffer will still be delivered.
7. Once a consumer has received all of a closed channel's elements, its `for` loop will stop looping.
8. After _cancelling_ a channel, any elements in the channel's buffer will be dropped, and any coroutine calling that channel's `receive()` function will also be cancelled.
9. The `produce()` function automatically calls `close()` whenever its lambda has completed.
10. A consumer can automatically cancel a channel - just use the `consumeEach()` function rather than a regular `for` loop.
11. All the rules of structured concurrency are still in play when using channels. Channels have no bearing on the coroutine hierarchy.
12. When a channel is closed, we're no longer able to send elements to it.
13. The `trySend()` function won't throw an exception if it's not able to send the element into a channel. It's also a _non-suspending_ function, so it can be called from anywhere.
14. When a channel is closed, the receiving coroutine can still receive any elements in that channel's buffer, but once the buffer has been fully drained, it cannot receive any more elements from it.
15. The `receiveCatching()` suspending function can be used to try receiving from a channel, and it won't throw if the channel has been closed.
16. To perform any lightweight, last-minute clean-up on elements that were unable to be delivered, use the `onUndeliveredElement` parameter of the `Channel` factory function.
