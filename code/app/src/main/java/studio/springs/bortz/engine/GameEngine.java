package studio.springs.bortz.engine;

import java.util.Queue;

import studio.springs.bortz.engine.board.BoardLogic;
import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameEngine {
    private GameState state;
    private GameBoard board;
    private BoardLogic logic;

    public GameEngine() {
        board = BoardLogic.prepareBoard();
        logic = new BoardLogic(board);
        state = new GameState();
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
                else if (board.getPiece(pos) != null && board.getPiece(pos).getColor() == state.getPlayerColor()){
                    state.setSelection(pos);
                }
                else {
                    logic.movePiece((Position) state.getSelection(), pos);
                    state.changeState(GameState.StateType.MOVE,PieceColor.opposite(state.getPlayerColor()));
                }
                break;
            case CAPTURED_PIECE_SELECTED:
                logic.dropPiece(pos, GamePieceFactory.createPiece((PieceType) state.getSelection(), state.getPlayerColor()));
                state.changeState(GameState.StateType.MOVE, PieceColor.opposite(state.getPlayerColor()));
                break;

        }
    }

    public Queue<GameChange> getChanges(){
        return board.changes;
    }
}
