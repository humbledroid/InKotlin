# Key Takeaways 🔑

1. Sometimes we need multiple flows to converge into a single flow.
2. The `merge()` function is used to collect each value from each upstream flow, and emit it into the single downstream flow. Merging works great when you've got flows that have the _same_ kind of data from _different_ data sources.
3. The `combine()` function is used to call a mapping function with the _latest_ value from each upstream flow. The mapping function is called anytime _any_ of the upstream flows provides a new value, as long as each of them has emitted at least one value. The result of the mapping function is emitted into the downstream flow. Combining is helpful when elements from _different_ flows need to be _put together_.
4. The `zip()` function is used to pair up corresponding elements from two different flows. It calls a mapping function every time that _both_ upstream flows have provided a new value.
