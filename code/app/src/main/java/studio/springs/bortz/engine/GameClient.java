package studio.springs.bortz.engine;

import studio.springs.bortz.engine.board.BoardLogic;
import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.utils.IllegalMoveException;

public class GameClient{
    private GameInterface gInterface;
    protected final GameBoard board = BoardLogic.prepareBoard();
    protected final BoardLogic logic = new BoardLogic(board);

    public GameInterface getInterface(){
        gInterface = new GameInterface(board);
        return  gInterface;
    }

    public void performMove() throws IllegalMoveException{
        logic.performMove(gInterface.getMove());
    }

}
