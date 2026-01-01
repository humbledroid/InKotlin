# Key Takeaways 🔑

1. Actors are used to _encapsulate data_ in a _concurrent_ application.
2. An actor is created with a factory function named `actor()`.
3. An actor _runs on a coroutine_, so the `actor()` function is called upon a `CoroutineScope` object.
4. The `actor()` function returns a channel that you can use to send messages to the actor.
5. To mutate the state inside an actor, pass a message to it via its channel. Use different kinds of messages to cause different kinds of effects to its state.
6. The `actor()` function also accepts a `capacity` so that you can configure its channel's buffer.
7. An actor runs on a _single coroutine_, so it processes a _single message at a time_, making it thread-safe.
8. The Actor API is marked as obsolete, but many Kotlin developers still use it.
