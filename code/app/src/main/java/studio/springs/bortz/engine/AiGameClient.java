package studio.springs.bortz.engine;

import studio.springs.bortz.ai.Ai;
import studio.springs.bortz.ai.RandomAi;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.utils.IllegalMoveException;

public class AiGameClient extends GameClient {
    Ai ai;
    public AiGameClient(){
        this.ai = new RandomAi(logic, board, PieceColor.BLACK);
    }

    @Override
    public void performMove() throws IllegalMoveException{
        super.performMove();
        performMove(ai.calculateMove());
    }
}
