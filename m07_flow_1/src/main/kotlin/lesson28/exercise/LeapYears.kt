package lesson28.exercise

/* ****************************************************************************

   Do you know how to calculate whether a particular year is a leap year?

   Julius Caesar introduced the idea of adding an extra day every fourth year,
   which is known as a leap year. Much later, the Gregorian Reform updated it
   so that years exactly divisible by 100 are _not_ leap years... unless that
   year is exactly divisible by 400, in which case it _is_ a leap year!

    - If the year number is exactly divisible by 4, then it's a leap year...
    - Unless it's exactly divisible by 100 and _not_ exactly divisible by 400.

   Write a function `leapYears(from: Int, to: Int)` that returns a `Flow<Int>`
   of all leap years from `from` through `to` (inclusive).

   Then, collect that flow for the years 1582-2011, and print out each one.
   There should be a total of 104 leap years between those two dates.

   ************************************************************************* */
