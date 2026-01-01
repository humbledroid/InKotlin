# Key Takeaways 🔑

<li>When testing flows, it's not natural to assert its items inside a collector, because the same collector runs once for each item in the flow.</li>
<li>Testing flows becomes much easier when first converting the flow to a channel. This allows us to make assertions _outside_ the collector, with each line of code suspending while waiting for the next item.</li>
<li>The _Turbine_ library converts flows into channels, and also adds some assertions to handle exceptions and completions.</li>
<li>Turbine includes a `test()` function that can be invoked on a flow. It accepts a lambda where we can make assertions about the elements that are emitted.</li>
<li>The `awaitItem()` function suspends waiting for the next item in the flow, much like a channel's `receive()` function.</li>
<li>The `awaitComplete()` function asserts that the flow completed successfully.</li>
<li>The `awaitError()` function asserts that the flow completed with an exception. It also returns the `Throwable` so that we can make assertions about it.</li>
<li>The `cancelAndIgnoreRemainingEvents()` function can be used at the end of a test where we only need to test items at the beginning of a flow.</li>
<li>The `skipItems()` function can be used to make assertions about items later in a flow, without regarding those before them.</li>
<li>Instead of using the `test()` function, we can use Turbine objects directly. This is especially helpful when testing multiple flows at one time.</li>
