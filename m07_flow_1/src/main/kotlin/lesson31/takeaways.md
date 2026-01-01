# Key Takeaways 🔑

1. When an exception is thrown from within a flow, that flow has some opportunities to deal with it.
2. An exception can be thrown from anywhere in the flow - emitters, collectors, and intermediate operators.
3. We can use try/catch locally, as usual.
4. When an exception escapes the bounds of an operator, it works its way _downward_ through the flow until it hits the collector.
5. When the collector receives an exception, it _throws_ it, giving the code around the flow a chance to deal with it.
6. To catch the exception outside the flow that throws it, wrap the collector with a try-catch.
7. The `catch {}` _operator_ is invoked when there's an uncaught exception coming from upstream.
8. The `retry {}` and `retryWhen {}` operators also receive uncaught exceptions from upstream, but their lambda must evaluate to a `Boolean`, indicating whether to restart the flow in response to the exception.
9. When `retry {}` or `retryWhen {}` triggers a restart of the flow, previously-emitted values could be emitted again.
10. Some flows cannot be restarted, such as those created by `consumeAsFlow()`.
