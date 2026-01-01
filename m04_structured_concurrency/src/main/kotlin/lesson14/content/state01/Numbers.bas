RANDOMIZE TIMER
plays = 0
guesses = 0

' STARTING BANNER

StartGame:
CLS
secret = INT(RND * 10) + 1
plays = plays + 1
guesses = 0
PRINT "-------------------------------"
PRINT " Number Guessing Game, Round"; plays
PRINT "-------------------------------"
PRINT "I'm thinking of a number between 1 and 10. Can you guess it?"
GOTO AskForGuess

' PROMPTS AND RESULTS

AllDone:
PRINT "You've played the maximum number of times!"
GOTO Finish

RoundOver:
IF guesses > 2 THEN PRINT "That was fun!"
GOTO ShowPlayCount

ShowPlayCount:
PRINT "You have played"; plays; "time(s)."
IF plays = 5 THEN GOTO AllDone ELSE GOTO AskToContinue

ShowResult:
IF guess = secret THEN
    PRINT "Correct! The number was"; secret
    GOTO RoundOver
ELSEIF guess < secret THEN
    PRINT "Too low!"
ELSE
    PRINT "Too high!"
END IF
GOTO AskForGuess

' GET INPUT FROM USER

AskForGuess:
INPUT "Your guess: ", guess
guesses = guesses + 1
GOTO ShowResult

AskToContinue:
INPUT "Keep going? ", done$
IF LCASE$(done$) = "yes" THEN GOTO StartGame ELSE GOTO Finish

' EXIT

Finish:
PRINT "Thanks for playing!"
PRINT "Good bye"
END
