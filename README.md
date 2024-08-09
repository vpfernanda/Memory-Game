# Memory Game
This project was first developed during my technician informatics course, at CEFET-MG. I used Java as a programming language to create this game.

## How it works
The game has 20 unrevealed cards; the main goal is to find an identical card, pair to pair. On the left side, you will find a counter of mistakes and hits.  Once all the cards have been revealed, the game is finished (and the window is closed).

## Take a look...
<img src="screenshots/sc1.jpg" alt="Game window with all cards unrevealed" width=75%/>
<img src="screenshots/sc2.jpg" alt="Correct attempt in the game" width=75%>

## Next steps 
The code was made in 2013 when I was a young programmer. I have not refactored or improved the code since then. I intend to make this little project better, and implement the following features:
_____UPDATE______ (August 9th - 2024)
I have been working on refactoring, improvements, and new features since I first uploaded this project to GitHub. Below I described the current state of each feature intended: 
- More than one mode to play (Easy / Medium / Hard / Levels) -> The class "Graphic.java" is already able to be extended by other classes which will represent each mode to play. Also, a new JFrame was needed for the user to select the mode, which has already been implemented.
- Cronometer to make the game more dynamic, and a maximum number of attempts (losing a "life" when the user cannot solve the memory game in time). -> The chronometer is already functional through an instance of GameTimer (GameTimer.java). Right now, this object can start, stop, and reset the time count. The time countdown has not been implemented yet, however, it is intended to be.
- Persistence of some user information, such as one's name and performance. -> Pending.
- Different themes for the cards (the current theme is "Animals"). -> A new theme was added: Food. Other themes soon will be created. 

## Be free to suggest
I invite you to open an issue and tell me what else I can implement to make this game funnier! :-) 
Soon I will be implementing more features.

## Do you want to play?
Feel free to clone this repo and play the game! Have fun. 
 

