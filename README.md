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
        Enter "correct position" count: 2
        Enter "correct color" count: 3
```

---

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
I chose the secret code to be `1 2 3 4 5` and answered accordingly:
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


## Information for Programmers
### How does it work?
The program is designed using object oriented aproach. There are two classes, the main class `Mastermind` and a class `State`, which is used to hold data.

---

The object `State` contains one sequence of colors and an automatically calculated array containing the number of times each color occurs in the sequence (used for calculating points).

---

In order to use this solver, you first need to create a `Mastermind` object with _number of positions_ and _number of colors_ as parameters and then control the game using `getGuess()` and `rateGuess()` functions.

Call the `createGame()` function to create `Mastermind` object. Both parameters _number of positions_ or _number of colors_ need to be positive (larger than zero), funtion will return `null` otherwise. If both parameters are positive, function will call the `Mastermind` constructor, which will create and populate the `possibilities` ArrayList with all the possible combinations, based on the parameters.

---

Now, you can call the object's functions `getGuess()` and `rateGuess()`.

Function `getGuess()` returns random guess from the `possibilities` list. If there no more possibilities (`possibilities` list is empty), function returns `null` (this shouldn't happen in a correct implementation - see `main()`).

Function `rateGuess()` receives three parameters: guess, points for positions and points for colors. After some error checking, it iterates through the remaining possibilities (the `possibilities` list) removing those that don't fit the parameters (see _Deciding whether possibilities fit the parameters_ section). Function returns the current game state (see _Return codes from `rateGuess()`_ section).

The intended use is to first call the `getGuess()` function to get the guess, rate it and call `rateGuess()` function with the guess (from `getGuess()`) and points.

#### Deciding whether possibilities fit the parameters
Function `rateGuess()` receives three parameters: _guess_, _points for positions_ and _points for colors_.

For every _possiblity_ from `possibilities` list, `rateGuess()` will rate _guess_ based on _possibility_ as a hypothetical secret and compare points with the parameters. If they don't match, the _possibility_ doesn't fit and gets deleted.

In other words, "if _possibility_ was the secret, would _guess_ get the same score as the score passed in parameteres?"

#### Return codes from `rateGuess()`
Function `rateGuess()` returns value from enum type `GameState`. Possible values are:

`OK`: all parameters were correct and there are still at least two possible answers (in other words, continue playing)

`ERROR_INCORRECT_STATE`: the `State` parameter isn't on the `possibilities` list (you are supposed to send the object you got from `getGuess()` function)

`ERROR_INCORRECT_POINTS`: either one of the _points_ values was negative or the sum of both was greater than _number of positions_ (based on the rules of the game, the sum implicitly has to be less or equal)

`FOUND_SOLUTION`: program found the answer, you can call `getGuess()` function to get it (in other words, `possibilities` list has one value in it)

`NO_SOLUTION`: there is no possible solution based on the _points_ you awarded (in other words, `possibilities` list is empty)

### Summary (of the algorithm)
1. Generate all possibilities (based on _number of positions_ and _number of colors_).
2. With each guess and rating, remove the options that don't fit the rating.
3. You are left with either one (solution) or no possibilities.

### Implementation of `main()`
The `main()` function is an example of the usage of the `Mastermind` class. 

1. Get _number of positions_ and _number of colors_ from user.
2. Initialize game with the values.
3. Get guess from the game and show it to the user.
4. Get user's rating and send it to the game.
5. If the game returns `NO_SOLUTION`, tell the user there is no solution and exit.
6. If the game returns `FOUND_SOLUTION`, get it using `getGuess()`, show it to the user and exit.
7. Goto 3.
