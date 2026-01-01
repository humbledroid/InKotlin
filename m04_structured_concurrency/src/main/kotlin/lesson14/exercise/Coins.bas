' This BASIC program uses the GOTO statement to jump around the code.
'
' For this exercise, port this code to Kotlin. Since Kotlin doesn't support
' GOTO, you'll need to refactor this to use structured programming. It's fine
' to use the `break` and `continue` keywords, if you'd like.
'
' In the code below, `INPUT` gets a value from the user via the console.
' `INPUT coin` -> `val coin = readLine()?.toIntOrNull()`
'
' And `INPUT variable$` gets a string value from the user via the console.
' `INPUT again$` -> `val again = readLine()`

total = 0

PRINT "Welcome to the Coin Counter!"

EnterCoin:
PRINT "Enter a coin value to insert (1, 5, 10, or 25):"
INPUT coin

IF coin = 1 THEN GOTO AddPenny
IF coin = 5 THEN GOTO AddNickel
IF coin = 10 THEN GOTO AddDime
IF coin = 25 THEN GOTO AddQuarter ELSE GOTO InvalidCoin

AddPenny:
total = total + 1
GOTO AskAgain

AddNickel:
total = total + 5
GOTO AskAgain

AddDime:
total = total + 10
GOTO AskAgain

AddQuarter:
total = total + 25
GOTO AskAgain

InvalidCoin:
PRINT "That's not a valid coin value. Please enter 1, 5, 10, or 25."
GOTO EnterCoin

AskAgain:
PRINT "The current total is:"; total
PRINT "Add another coin? (yes/no)"
INPUT again$
IF LCASE$(again$) = "yes" THEN GOTO EnterCoin ELSE GOTO Finish

Finish:
PRINT "Final total:"; total
PRINT "Thanks for using the Coin Counter!"
END
