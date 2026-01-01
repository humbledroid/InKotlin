# Key Takeaways 🔑

1. The receiver object of `runTest()`'s lambda is a special kind of `CoroutineScope` named `TestScope`.
2. `TestScope` includes a property named `testScheduler`, whose type is `TestScheduler`.
3. The `TestScheduler` is what allows `runTest()` to control the _timing_ of the coroutines' work, including the ability to _fast-forward through delays_.
4. The context of `TestScope` includes a special `CoroutineDispatcher` named `TestDispatcher`.
5. `TestDispatcher` objects have a reference to the `TestScheduler`.
6. In order to control the timing of a coroutine, that coroutine needs to have a `TestDispatcher`. This is one reason why it's a good idea to inject dispatchers rather than hard-coding them.
7. When calling the `StandardTestDispatcher()` function within `runTest()`, pass in the `testScheduler` - if more than one instance of `TestScheduler` is discovered at runtime, you'll get an `IllegalStateException`.
8. To control the timing of coroutines during the test, first wrap the call to the test subject with a coroutine builder, so that the test subject and the `runTest()` lambda can run concurrently.
9. Call `advanceTimeBy()` to move virtual time forward by some amount of time. This allows you to make assertions at different points along the timeline.
10. Call `advanceUntilIdle()` to move virtual time forward until there's no more work for any of the coroutines to do.
11. Use the `currentTime` property to get the amount of elapsed virtual time at any point along the timeline.
12. When you call `advanceTimeBy()`, coroutines will run their work up to - but not including - the new point in time.
13. To run coroutine work at an _exact point_ in time, first use `advanceTimeBy()` to advance the time to that point, and then when you're ready, call `runCurrent()`.
