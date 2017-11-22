## Bortz

Bortz is a shogi-based game for Android platform. The goal for this project was to create simple and open-source game that resembles classic shogi (japanese chess), but in form more accessible for casual palyer. Thus, the dobutsu shogi version was chosen for further development. It is basically shogi for children, with smaller board and restriction to only one-square move for pieces.

## Getting Started
### Prerequisites

You should have [Android Studio 3.0.1](https://developer.android.com/studio/index.html) installed.

### Installation

Simply git clone, then open as a project in Android Studio and run.

NOTE: You should use Bortz/code folder as a root for project.

## Gameplay

Bortz now offers only player vs AI mode. To access it choose Training option from Main Menu and then Fight AI. This will automatically set up a new game board. 

Additionally, player can set a theme and/or guides for pieces. To access this funcionality go to Settings from Main Menu and then mark your theme of choice and set guides accordingly to preferences.

Board is 3x4 in size, each player has initially four pieces (Lion, Giraffe, Elephant and Chic). Winning condition is capturing opponent's Lion piece or reaching opponent's side of board by own Lion piece (and not being threatened by opponent's piece at that time). Each piece can move only one square per turn, the movement regimes are following:

* Lion - every possible direction,
* Giraffe - ahead, backward, left and right,
* Elephant - diagonally ahead and backwards,
* Chick - ahead only,
* Chicken (evolved Chick, more on that later) - same as Giraffe plus diagonally ahead.

The captured piece becomes capturer's piece ready to be placed on ANY non-taken position on board when it is capturer's turn. You can find pieces captured by yourself below the board. If a Chick reaches the end of a board it evolves to Chicken which has enhanced movement regimes. Remember that captured Chicken becomes Chick if placed back on board.

## Contributors

* Szymon Mikulicz
* Marcel Piszak

## License

Project is licensed under the GNU General Public License version 3. For details see [LICENSE](https://github.com/SpringsStudio/Bortz/blob/master/LICENSE)
