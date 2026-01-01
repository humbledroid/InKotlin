# Key Takeaways 🔑

1. Testing suspending functions is similar to testing regular, non-suspending functions, except that a suspending function needs to be called from another suspending function.
2. `runBlocking()` can technically be used when a test function needs to call a suspending function, but a better choice is the `runTest()` function, which fast-forwards through calls to `delay()`.
3. Structured concurrency gives us guarantees that all the coroutines launched by the test subject will be completed before `runTest()` completes, as long as those coroutines are launched on `runTest()`'s scope. 
