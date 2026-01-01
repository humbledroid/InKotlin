# Key Takeaways 🔑

1. As we've seen before, coroutine can't be controlled by the test scheduler unless it's using a test dispatcher.
2. When a class has a `CoroutineScope` property that it builds its coroutines upon, you'll still need to set its dispatcher to use the test dispatcher if you want it to run in virtual time.
3. When testing a long-running coroutine, it needs to be configured properly, or else it can cause your _test_ to become long-running.
4. Don't use `advanceUntilIde()` with a coroutine that runs an infinite loop unless you've already cancelled it. 
5. Once the `runTest` function has finished running its lambda, it will advance the test scheduler until it's idle - effectively an automatic `advanceUntilIdle()`.
6. Classes that have a `CoroutineScope` property should also include a shutdown hook that cancels that scope. When testing those classes, be sure to call that hook before the end of the test, especially if there are long-running coroutines attached to it. Pro Tip - Consider putting that call within a `finally` block to make sure it's cancelled, even when your assertion library throws an exception.
7. When your test subject's constructor accepts a _scope_ instead of a _dispatcher_, don't pass in the receiver object from the `runTest` lambda (that is, `this`), because if it cancels that scope, the test itself will abort!
8. The `backgroundScope` property has a test dispatcher and test scheduler, and it also is automatically cancelled at the end of the test.
9. ViewModels and other UI-facing classes often use `Dispatchers.Main` to make updates to the user interface.
10. Rather than manually overriding the dispatcher of `viewModelScope`, surround your test code with `Dispatchers.setMain()` and `Dispatchers.resetMain()`. Use your testing framework's tooling to declaratively add these calls, if possible.  
