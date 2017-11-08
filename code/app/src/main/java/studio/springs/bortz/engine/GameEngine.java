package studio.springs.bortz.engine;

import java.util.Queue;

import studio.springs.bortz.engine.logic.BoardChange;
import studio.springs.bortz.engine.logic.BoardLogic;
import studio.springs.bortz.engine.logic.GameBoard;
import studio.springs.bortz.engine.utils.IllegalMoveException;

class GameEngine {
    protected final GameBoard board = BoardLogic.prepareBoard();
    protected final BoardLogic logic = new BoardLogic(board);

    public void performMove(GameMove move) throws IllegalMoveException{
        switch (move.movement){
            case SIMPLE_MOVEMENT:
                logic.movePiece(move.origin,move.destination);
                break;
            case DROP:
                logic.dropPiece(move.destination,move.piece);
                break;
        }
    }

    public Queue<BoardChange> getChanges(){
        return board.changes;
    }
}
