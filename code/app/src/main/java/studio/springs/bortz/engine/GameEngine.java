package studio.springs.bortz.engine;

import java.util.Queue;

import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameEngine {
    private GameBoard board;
    private GameState state;

    public GameEngine() {
        state = new GameState();
        board = new GameBoard(3,4);

        try {
            board.placePiece(new Position(1, 0), GamePieceFactory.createPiece(PieceType.LION, PieceColor.WHITE));
            board.placePiece(new Position(1, 3), GamePieceFactory.createPiece(PieceType.LION, PieceColor.BLACK));
            board.placePiece(new Position(2, 0), GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.WHITE));
            board.placePiece(new Position(0, 3), GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.BLACK));
            board.placePiece(new Position(0, 0), GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.WHITE));
            board.placePiece(new Position(2, 3), GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.BLACK));
            board.placePiece(new Position(1, 1), GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.WHITE));
            board.placePiece(new Position(1, 2), GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.BLACK));
        }
        catch (IllegalMoveException ex) {
            System.err.println("Something when wrong when initializing the game engine");
            System.exit(1);
        }
    }

    public void selectCapturedPiece(PieceType type, PieceColor color) {

        switch (state.getType()){
            case MOVE:
                Integer piecesNumber = board.countCapturedPieces(type, color);
                if ( color == state.getPlayerColor() && piecesNumber > 0){
                    state.changeState(GameState.StateType.CAPTURED_PIECE_SELECTED);
                    state.setSelection(type);
                }
                break;
            default: break;
        }
    }

    public void selectBoardSquare(Position pos) throws IllegalMoveException{
        switch (state.getType()) {
            case MOVE:
                if (board.getPiece(pos) != null && board.getPiece(pos).getColor() == state.getPlayerColor()) {
                    state.setSelection(pos);
                    state.changeState(GameState.StateType.BOARD_PIECE_SELECTED);
                }
                break;
            case BOARD_PIECE_SELECTED:
                if (((Position) state.getSelection()).Equals(pos)) {
                    state.changeState(GameState.StateType.MOVE);
                }
                else {
                    board.movePiece((Position) state.getSelection(), pos);
                    state.changeState(GameState.StateType.MOVE,PieceColor.opposite(state.getPlayerColor()));
                }
                break;
            case CAPTURED_PIECE_SELECTED:
                board.placePiece(pos,GamePieceFactory.createPiece((PieceType) state.getSelection(),state.getPlayerColor()));
                board.removeCapturedPiece((PieceType) state.getSelection(), state.getPlayerColor());
                state.changeState(GameState.StateType.MOVE,PieceColor.opposite(state.getPlayerColor()));
                break;

        }
    }
    public Queue<GameChange> getChanges(){
        return board.changes;
    }
}
