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

        for (GameMove move : logic.possibleMoves()){
            int newMove = minimax(board, move, depth - 1, aiColor);
            if ( sign * newMove > sign * bestMoveValue ){
                bestMoves.clear();
                bestMoves.add(move);
                bestMoveValue = newMove;
            }
            else if (newMove == bestMoveValue) {
                bestMoves.add(move);
            }
        }
        if (bestMoves.size() == 0) return null;
        return bestMoves.get((int) Math.floor(Math.random() * bestMoves.size()));
    }
    private int minimax(GameBoard board, GameMove move, int depth, PieceColor playerColor){
        GameBoard newBoard = BoardLogic.boardAfterMovement(board, playerColor, move);
        int boardValue = evaluateBoard(newBoard);
        if (depth == 0 || sign * evaluateBoard(board) > sign * boardValue) {
            return boardValue;
        }
        else {
            List<GameMove> moves = BoardLogic.possibleMoves(newBoard,PieceColor.opposite(playerColor));
            int mod = PieceColor.opposite(playerColor) == PieceColor.WHITE ? 1 : -1;
            int bestMove = -mod * maxValue;
            for (GameMove newMove : moves){
                int newMoveValue = minimax(newBoard, newMove, depth - 1, PieceColor.opposite(playerColor));
                if ( mod * newMoveValue > mod * bestMove ){
                    bestMove = newMoveValue;
                }
            }
            return bestMove;
        }
    }
}
