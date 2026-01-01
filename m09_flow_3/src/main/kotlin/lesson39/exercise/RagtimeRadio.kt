package lesson39.exercise

/* ****************************************************************************

   Copy and paste your solution from the previous exercise, and then update it
   so that the `RadioStation` object starts with a cold flow that emits the
   songs in round-robin fashion - that is, emit each song in turn, and when you
   get to the end of the list of songs, start over at the beginning again. That
   way the radio will always play a song.

   Then, convert that cold flow into a `StateFlow` using the `stateIn()`
   function.

   Make sure that the rest of the functionality from the last exercise
   continues to work - i.e., printing out the songs, printing out the playlist,
   and adding the currently-playing song to the playlist.

   ************************************************************************* */
