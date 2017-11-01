package studio.springs.bortz.engine;

import java.util.Queue;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameEngine {
    private GameBoard board;
    private GameState state;
    private BoardLogic logic;

    public GameEngine() {
        board = new GameBoard(3,4);
        logic = new BoardLogic();
        state = new GameState();

        try {
            logic.prepareBoard();
        } catch (IllegalMoveException ex) {
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
                else if (board.getPiece(pos) != null && board.getPiece(pos).getColor() == state.getPlayerColor()){
                    state.setSelection(pos);
                }
                else {
                    logic.movePiece((Position) state.getSelection(), pos);
                    state.changeState(GameState.StateType.MOVE,PieceColor.opposite(state.getPlayerColor()));
                }
                break;
            case CAPTURED_PIECE_SELECTED:
                logic.placePiece(pos,GamePieceFactory.createPiece((PieceType) state.getSelection(),state.getPlayerColor()));
                board.removeCapturedPiece((PieceType) state.getSelection(), state.getPlayerColor());
                state.changeState(GameState.StateType.MOVE,PieceColor.opposite(state.getPlayerColor()));
                break;

        }
    }

    public Queue<GameChange> getChanges(){
        return board.changes;
    }

    private class BoardLogic {
        void prepareBoard() throws IllegalMoveException{
            placePiece(new Position(1, 0), GamePieceFactory.createPiece(PieceType.LION, PieceColor.WHITE));
            placePiece(new Position(1, 3), GamePieceFactory.createPiece(PieceType.LION, PieceColor.BLACK));
            placePiece(new Position(2, 0), GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.WHITE));
            placePiece(new Position(0, 3), GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.BLACK));
            placePiece(new Position(0, 0), GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.WHITE));
            placePiece(new Position(2, 3), GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.BLACK));
            placePiece(new Position(1, 1), GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.WHITE));
            placePiece(new Position(1, 2), GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.BLACK));
        }
        void movePiece(Position from, Position to) throws IllegalMoveException {
            if (board.getPiece(to) != null && board.getPiece(from).getColor() == board.getPiece(to).getColor()) {
                throw new IllegalMoveException("You cannot capture your own piece.");
            } else if (board.getPiece(from).canMove(Position.Subtract(to, from))) {
                if (board.getPiece(to) != null) {
                    if (board.getPiece(to).getType() == PieceType.LION) {
                        // Game end, win
                    }
                    else {
                        PieceType type = board.getPiece(to).getType() == PieceType.CHICKEN ? PieceType.CHICK : board.getPiece(to).getType();
                        board.addCapturedPiece(type, PieceColor.opposite(board.getPiece(to).getColor()));
                    }
                }
                // Turn chick into chicken if it reaches the end or the beginning of the board.
                if (board.getPiece(from).getType() == PieceType.CHICK && (to.y == board.getSize().y - 1 || to.y == 0)){
                    board.setPiece(to, GamePieceFactory.createPiece(PieceType.CHICKEN,board.getPiece(from).getColor()));
                }
                else {
                    board.setPiece(to, board.getPiece(from));
                }
                board.setPiece(from, null);
            } else throw new IllegalMoveException("This piece cannot move like this");
        }
        void placePiece(Position pos, GamePiece piece) throws IllegalMoveException {
            if (board.getPiece(pos) != null) {
                throw new IllegalMoveException("A piece cannot be placed on a square that is already taken");
            }
            else {
                board.setPiece(pos, piece);
            }
        }
    }
}
