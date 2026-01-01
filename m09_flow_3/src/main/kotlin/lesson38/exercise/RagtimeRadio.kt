package lesson38.exercise

/* ****************************************************************************

   Using the list of songs from the exercise for Lesson 36, create an object
   named `RadioStation` with a `signal` state flow. The radio station needs to
   emit a random song into its state flow, being sure to delay the proper
   amount of time for the song.

   Then, create a `Playlist` object, which also has a state flow that holds a
   list of strings. Give this object a function - `add(songTitle: String)` -
   which can be used to add the song title to the list in its state flow.

   Finally, create a `main()` function that does these things:

   1. Whenever the `RadioStation` starts playing another song, print out the
   name of the song - e.g., `🎹 Now playing: Maple Leaf Rag`.
   2. Whenever the `Playlist` gets a new song, print out the full list of songs
   in the playlist - e.g., `📃 Playlist: [Elite Syncopations, Peacherine Rag]`
   3. Whenever the user hits the Enter key in the console, add the radio's
   currently-playing song to the playlist.

   ************************************************************************* */
