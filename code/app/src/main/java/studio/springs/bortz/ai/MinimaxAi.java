package studio.springs.bortz.ai;

import java.util.ArrayList;
import java.util.List;

import studio.springs.bortz.engine.utils.GameMove;
import studio.springs.bortz.engine.board.BoardLogic;
import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.pieces.PieceColor;

public class MinimaxAi extends Ai {
    private int depth;
    private int sign;
    public MinimaxAi(BoardLogic logic, GameBoard board, PieceColor aiColor, int depth){
        super(logic, board, aiColor);
        sign = aiColor == PieceColor.WHITE ? 1 : -1;
        this.depth = depth;
    }

    int maxValue = 999999;

    @Override
    public GameMove calculateMove() {
        List<GameMove> bestMoves = new ArrayList<>();
        int bestMoveValue = -sign * maxValue;
        board.setChangesEnabled(false);
        for (GameMove move : logic.possibleMoves()){
            int newMove = minimax(move, depth - 1);
            if ( sign * newMove > sign * bestMoveValue ){
                bestMoves.clear();
                bestMoves.add(move);
                bestMoveValue = newMove;
            }
            else if (newMove == bestMoveValue) {
                bestMoves.add(move);
            }
        }
        board.setChangesEnabled(true);
        if (bestMoves.size() == 0) return null;
        return bestMoves.get((int) Math.floor(Math.random() * bestMoves.size()));
    }
    private int minimax(GameMove move, int depth){
        int oldBoardValue = evaluateBoard(board);
        logic.unsafePerformMove(move);
        int newBoardValue = evaluateBoard(board);
        int bestMove;
        if (depth == 0 || sign * oldBoardValue > sign * newBoardValue) {
            bestMove = newBoardValue;
        }
        else {
            int mod = logic.getPlayerColor() == PieceColor.WHITE ? 1 : -1;
            bestMove = -mod * maxValue;
            for (GameMove newMove : logic.possibleMoves()){
                int newMoveValue = minimax(newMove, depth - 1);
                if ( mod * newMoveValue > mod * bestMove ){
                    bestMove = newMoveValue;
                }
            }
        }
        logic.undo();
        return bestMove;
    }
}
