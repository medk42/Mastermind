# Mastermind
## What is this?
Basically a [Mastermind](https://en.wikipedia.org/wiki/Mastermind_(board_game) "Link to Wikipedia") solver.

It can be used either as a library (e.g. for a Mastermind game with GUI) or standalone, using command line.

## User guide (for standalone part):
### Running the program
1. Make sure you have `Java` installed
2. Navigate to `build` folder (`cd build`)
3. Start the program `Mastermind.jar` using Java (`java -jar Mastermind.jar`)
```
<Some_path>\Mastermind>cd build
<Some_path>\Mastermind\build>java -jar Mastermind.jar
Enter position count:
```

### Controlling the program
After you start the program, it will ask for two values - _number of positions_ (usually 5) and _number of colors_ (usually 8).

Please enter only positive values (larger than zero).

**Example:**
```
Enter position count: 5
Enter color count: 8
```

**Colors are represented as numbers from _0_ to _number of colors - 1_** (For example, if we set _number of colors_ to 8, we can use colors 0,...,7)

---

Now, the program will repeatedly give you its guess as a sequence of numbers (colors). For example sequence `0 0 1 7 4` means _"position 4 has color 7"_.

You need to rate its guess using  _"correct position"_ points and _"correct color"_ points according to the [rules](https://en.wikipedia.org/wiki/Mastermind_(board_game)#Gameplay_and_rules "Link to Wikipedia") of the game.

Please enter only non-negative values (larger or equal to zero).

**Example:**
```
---------------------------
Guess: 1 6 7 7 2
Rate the guess:
        Enter "correct position" count:2
        Enter "correct color" count:3
```

Based on your answers, the program will narrow down the possibilities and finally either show you the solution or tell you that there is no solution.

**Example 1:**
```
---------------------------
Found solution!
        Solution is 1 2 3 4 5
```
**Example 2:**
```
---------------------------
There is no solution.
```
### Possible errors (while running standalone)
`Please enter positive integer!`: you have to enter an integer number larger than zero

`Please enter non-negative integer!`: you have to enter an integer number larger or equal to zero

`Points incorrect! (too small or too high)`: the sum of the  _"correct position"_ points and _"correct color"_ points you entered is greater than _number of positions_ (based on the rules of the game, the sum implicitly has to be less or equal)

### Example game
I chose my secret code to be `1 2 3 4 5` and answered accordingly:
```
<Some_path>\Mastermind>cd build

<Some_path>\Mastermind\build>java -jar Mastermind.jar
Enter position count: 5
Enter color count: 8
---------------------------
Guess: 4 4 0 5 7
Rate the guess:
        Enter "correct position" count:0
        Enter "correct color" count:2
---------------------------
Guess: 5 1 7 3 6
Rate the guess:
        Enter "correct position" count:0
        Enter "correct color" count:3
---------------------------
Guess: 7 7 5 7 1
Rate the guess:
        Enter "correct position" count:0
        Enter "correct color" count:2
---------------------------
Guess: 3 0 3 1 5
Rate the guess:
        Enter "correct position" count:2
        Enter "correct color" count:1
---------------------------
Guess: 6 0 1 6 5
Rate the guess:
        Enter "correct position" count:1
        Enter "correct color" count:1
---------------------------
Guess: 1 2 3 4 5
Rate the guess:
        Enter "correct position" count:5
        Enter "correct color" count:0
---------------------------
Found solution!
        Solution is 1 2 3 4 5
```

