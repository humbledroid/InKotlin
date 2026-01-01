# Key Takeaways 🔑

1. **Flattening** is an operation that _removes nesting_. For example, a list of list of strings can be flattened to just a list of strings.
2. Similarly, flows can _also_ be nested, and they can be flattened to remove that nesting.
3. Because flows involve _concurrency_, there are _multiple_ ways to flatten them.
4. The simplest way to flatten a nested flow is to use concatenation with either `flattenConcat()` or `flatMapConcat()`. These operators flatten the flow by processing each nested flow _one at a time_, from _top to bottom_.
5. When you want to process the nested flows _concurrently_, use `flattenMerge()` or `flatMapMerge()`. You can specify how many nested flows can run at the same time by providing an argument for their first parameter - `concurrency`.
6. As with `collectLatest()`, `mapLatest()`, and `transformLatest()`, there's also a `flatMapLatest()` that can be used to interrupt the nested flow whenever a new nested flow arrives.
