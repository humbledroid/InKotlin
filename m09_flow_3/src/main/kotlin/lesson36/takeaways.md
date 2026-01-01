# Key Takeaways 🔑

1. **Cold flows** only emit values whenever a collector requests them, and each collector gets its _own_ experience with the flow, starting with the first value.
2. **Hot flows** emit the same values to each of its collectors, giving each collector the _same_ experience, allowing collectors to subscribe or unsubscribe at any time.
3. A cold flow is akin to a downloadable podcast episode, where each viewer can watch the episode on his or her own schedule, with progress in the episode being controlled by the viewer.
4. A hot flow is more akin to a television broadcast, where all viewers watch the episode at the same time, with progress in the episode being controlled by the television station.
