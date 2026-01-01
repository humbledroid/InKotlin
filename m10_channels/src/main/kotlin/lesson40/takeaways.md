# Key Takeaways 🔑

1. Simple concurrent workflows can be implemented with regular coroutines using the `async()` and `await()` functions.
2. More complex workflows can benefit from _queues_, especially when work needs to be _rerouted_ back into previous parts of a workflow.
3. Queues require _polling_, but with channels, our workers can simply _suspend_ while waiting for a new value.
