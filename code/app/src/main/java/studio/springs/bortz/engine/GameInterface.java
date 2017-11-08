package studio.springs.bortz.engine;

import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.engine.utils.Position;

public class GameInterface {
   public  enum InterfaceState {
        MOVE_START,
        BOARD_PIECE_SELECTED,
        CAPTURED_PIECE_SELECTED,
        MOVE_END
    }

    final private GameBoard board;

    private InterfaceState state;
    private PieceType selectedPiece;
    private Position selectedPosition;
    private GameMove move;

    GameInterface(GameBoard board) {
        this.board = board;
        state = InterfaceState.MOVE_START;
    }

    GameMove getMove(){
        state = InterfaceState.MOVE_START;
        return move;
    }
    public InterfaceState getState(){
        return state;
    }
    public void selectCapturedPiece(PieceType type) {

        switch (state) {
            case MOVE_START:
                state = InterfaceState.CAPTURED_PIECE_SELECTED;
                selectedPiece = type;
                break;
            default:
                break;
        }
    }

    public void selectBoardSquare(Position pos) {
        GamePiece piece = board.getPiece(pos);
        switch (state) {
            case MOVE_START:
                if (piece != null) {
                    selectedPiece = piece.getType();
                    selectedPosition = pos;
                    state = InterfaceState.BOARD_PIECE_SELECTED;
                }
                break;
            case BOARD_PIECE_SELECTED:
                move = new GameMove(selectedPiece, selectedPosition, GameMove.MoveType.SIMPLE_MOVEMENT, pos);
                state = InterfaceState.MOVE_END;
                break;
            case CAPTURED_PIECE_SELECTED:
                move = new GameMove(selectedPiece, null, GameMove.MoveType.DROP, pos);
                state = InterfaceState.MOVE_END;
                break;
            case MOVE_END:
                break;

        }

    }
}

