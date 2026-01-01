# Key Takeaways 🔑

1. The Flow API imposes certain invariants to prevent surprises.
2. **Context preservation** is an invariant that ensures operators will not change the context of their downstream flow.
3. The `flowOn()` operator changes the context of its _upstream_ flow, but preserves the context of its downstream flow.
4. By passing a dispatcher to `flowOn()` that's different from the dispatcher used by the collector, our flow will run on multiple coroutines.
5. When the emitter and collector run on different coroutines, they run _concurrently_, which introduces timing issues - it makes it possible for the emitter to produce values faster than the collector can receive.
