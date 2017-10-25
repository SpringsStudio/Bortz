package studio.springs.bortz.engine;

import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameEngine {
    GameBoard board;
    GameState state;
    GamePieceFactory factory;

    public GameEngine() {
        factory = new GamePieceFactory();
        state = GameState.WHITE_MOVE;
        board = new GameBoard(3,4);

        try {
            board.placePiece(new Position(2, 1), factory.createPiece(PieceType.LION, PieceColor.WHITE));
        }
        catch (IllegalMoveException ex) {
            System.err.println("Something when wrong when initializing the game engine");
            System.exit(1);
        }
    }
}
