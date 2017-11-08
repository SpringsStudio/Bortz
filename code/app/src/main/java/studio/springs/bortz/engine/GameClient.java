package studio.springs.bortz.engine;

import studio.springs.bortz.engine.utils.IllegalMoveException;

public class GameClient extends GameEngine{
    private GameInterface gInterface;

    public GameInterface getInterface(){
        gInterface = new GameInterface(board);
        return  gInterface;
    }

    public void performMove() throws IllegalMoveException{
        performMove(gInterface.getMove());
    }

}
