package studio.springs.bortz.ai;

import java.util.List;

import studio.springs.bortz.engine.utils.GameMove;
import studio.springs.bortz.engine.board.BoardLogic;
import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.pieces.PieceColor;

public class RandomAi extends Ai {

    public RandomAi(BoardLogic logic, GameBoard board, PieceColor aiColor){
        super(logic, board, aiColor);
    }

    @Override
    public GameMove calculateMove() {
        List<GameMove> posMoves = logic.possibleMoves();
        if (posMoves.size() == 0) return null;
        int randomnum = (int) Math.floor(Math.random() *  posMoves.size());
        return posMoves.get(randomnum);
    }
}
