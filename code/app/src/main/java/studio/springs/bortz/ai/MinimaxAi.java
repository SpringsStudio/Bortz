package studio.springs.bortz.ai;

import java.util.List;

import studio.springs.bortz.engine.GameMove;
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

    @Override
    public GameMove calculateMove() {
        GameMove bestMove = null;
        int bestMoveValue = -sign * 9999;

        for (GameMove move : logic.possbleMoves()){
            int newMove = minimax(board, move, depth - 1, aiColor);
            if ( sign * newMove > sign * bestMoveValue ){
                bestMove = move;
                bestMoveValue = newMove;
            }
        }
        return bestMove;
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
            int bestMove = -mod * 9999;
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
