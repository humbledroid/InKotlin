package lesson27.exercise

/* ****************************************************************************

   This fast food restaurant has five different meal combos:

    - #1 Classic Burger Combo - 🍔🍟🥤
    - #2 Double Trouble Combo - 🍔🍔🍟🥤
    - #3 Chicken Combo - 🍗🍟🥤
    - #4 Veggie Power Combo - 🥗🥤
    - #5 Breakfast All-Day Combo - 🥯☕🍩

   Three customers came in and placed orders:

    1. The first customer ordered #2 and #4.
    2. The second customer ordered meal combos #1, #3, and #4.
    3. The third customer ordered #5, #1, and #4.

   Write a `main()` function that calls a `placeOrder()` function three times -
   once for each customer. The `placeOrder()` function should receive the
   meal combos that the customer ordered. Within that function, print these
   things to the screen:

    - The number of combos in the order.
    - The name of each combo in the order.
    - The individual items from all combos in the entire order.

   Use a collection or sequence operator chain to transform the meal combos
   into the individual items of each combo in the order.

   Oh - one more thing! The restaurant is out of 🍗 and 🥯... so if a customer
   orders one of those, substitute it with a coupon 💸.

   Here's an example of what the output for the first customer should look like.

   ----------------------------------------------------------------------------

   Fulfilling an order that has 2 combos in it.
       Combos in this order: Double Trouble Combo, Veggie Power Combo
       Items in this order: 🍔 🍔 🍟 🥤 🥗 🥤

   ************************************************************************* */
