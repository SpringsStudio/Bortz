package studio.springs.bortz.engine;

import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameEngine {
    private GameBoard board;
    private GameState state;
    private Position selectedSquare;
    private GamePieceFactory factory;

    public GameEngine() {
        factory = new GamePieceFactory();
        state = GameState.WHITE_MOVE;
        board = new GameBoard(3,4);

        try {
            board.placePiece(new Position(2, 1), factory.createPiece(PieceType.LION, PieceColor.WHITE));
            board.placePiece(new Position(2, 4), factory.createPiece(PieceType.LION, PieceColor.BLACK));
        }
        catch (IllegalMoveException ex) {
            System.err.println("Something when wrong when initializing the game engine");
            System.exit(1);
        }
    }
    public void selectBoardSquare(Position pos) throws IllegalMoveException{
        switch (state) {
            case WHITE_MOVE:
                if (board.getPiece(pos) != null && board.getPiece(pos).getColor() == PieceColor.WHITE) {
                    selectedSquare = pos;
                }
                break;
            case WHITE_PIECE_SELECTED:
                if (selectedSquare.Equals(pos)) {
                    state = GameState.WHITE_MOVE;
                }
                else {
                    board.movePiece(selectedSquare, pos);
                    state = GameState.BLACK_MOVE;
                }
                break;
            case BLACK_MOVE:
                if (board.getPiece(pos) != null && board.getPiece(pos).getColor() == PieceColor.BLACK) {
                    selectedSquare = pos;
                }
                break;
            case BLACK_PIECE_SELECTED:
                if (selectedSquare.Equals(pos)) {
                    state = GameState.BLACK_MOVE;
                }
                else {
                    board.movePiece(selectedSquare, pos);
                    state = GameState.WHITE_MOVE;
                }
                break;

        }

    }
}
