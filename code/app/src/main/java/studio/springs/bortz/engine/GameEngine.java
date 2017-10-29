package studio.springs.bortz.engine;

import java.util.Queue;

import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameEngine {
    private GameBoard board;
    private GameState state;
    private Position selectedSquare;

    public GameEngine() {
        state = GameState.WHITE_MOVE;
        board = new GameBoard(3,4);

        try {
            board.placePiece(new Position(1, 0), GamePieceFactory.createPiece(PieceType.LION, PieceColor.WHITE));
            board.placePiece(new Position(1, 3), GamePieceFactory.createPiece(PieceType.LION, PieceColor.BLACK));
            board.placePiece(new Position(2, 0), GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.WHITE));
            board.placePiece(new Position(0, 3), GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.BLACK));
            board.placePiece(new Position(0, 0), GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.WHITE));
            board.placePiece(new Position(2, 3), GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.BLACK));
            board.placePiece(new Position(1, 1), GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.WHITE));
            board.placePiece(new Position(2, 2), GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.BLACK));
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
                    state = GameState.WHITE_PIECE_SELECTED;
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
                    state = GameState.BLACK_PIECE_SELECTED;
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
    public Queue<GameChange> getChanges(){
        return board.changes;
    }
}
