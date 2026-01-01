# Key Takeaways 🔑

1. By default, a channel's _sender suspends waiting for a receiver_, and a channel's _receiver suspends waiting for a
   sender_. This strategy is known as **rendezvous**, because the sender and receiver have to _meet at the channel_ in
   order to hand off an element.
2. The rendezvous strategy is safe to use in a single-direction workflow, where each worker simply hands off the element
   to the next worker. But once a worker can route an element back to a previous worker, the rendezvous strategy can
   result in _deadlocks_.
3. Channels can have buffers, which can effectively act as an inbox for the receiving coroutine. To give a channel a
   buffer, just specify the `capacity` parameter, using a number that indicates how many elements you want the buffer to
   hold.
4. There are some constants you can use with the `capacity` parameter - `RENDEZVOUS` causes the default behavior,
   `CONFLATED` drops older values, `BUFFERED` uses the default buffer size, and `UNLIMITED` uses `Int.MAX_VALUE`.
5. The `Channel()` factory function also includes an `onBufferOverflow` parameter, which works the same as we've seen
   earlier in this course.
6. To increase throughput, you can create multiple coroutines that receive from the same channel. This results in *
   *fan-out**, where _each element_ is delivered to a _single coroutine_, so that those coroutines can process different
   elements concurrently.
7. When using fan-out to process elements concurrently, make sure the downstream channels have a buffer capacity large
   enough to accommodate all those elements coming through, especially when they fan back into a single channel!
