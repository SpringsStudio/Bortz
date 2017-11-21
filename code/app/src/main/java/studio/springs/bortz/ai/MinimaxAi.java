package studio.springs.bortz.ai;

import java.util.List;

import studio.springs.bortz.engine.GameMove;
import studio.springs.bortz.engine.board.BoardLogic;
import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.pieces.PieceColor;

public class MinimaxAi extends Ai {
    private int depth;
    public MinimaxAi(BoardLogic logic, GameBoard board, PieceColor aiColor, int depth){
        super(logic, board, aiColor);
        this.depth = depth;
    }

    @Override
    public GameMove calculateMove() {
        GameMove bestMove = null;
        int bestMoveValue = -9999;

        for (GameMove move : logic.possbleMoves()){
            int newMove = minimax(board, move, depth - 1, aiColor);
            if ( newMove > bestMoveValue){
                bestMove = move;
                bestMoveValue = newMove;
            }
        }
        return bestMove;
    }
    private int minimax(GameBoard board, GameMove move, int depth, PieceColor playerColor){
        GameBoard newBoard = BoardLogic.boardAfterMovement(board, playerColor, move);
        int boardValue = evaluateBoard(newBoard);
        if (depth == 0 || evaluateBoard(board) > boardValue)
            return boardValue;
        else {
            List<GameMove> moves = BoardLogic.possibleMoves(newBoard,PieceColor.opposite(playerColor));
            int bestMove;
            if (aiColor == PieceColor.opposite(playerColor)){
                bestMove = -9999;
                for (GameMove newMove : moves){
                    bestMove = Math.max(bestMove, minimax(newBoard, newMove,depth - 1 , PieceColor.opposite(playerColor)));
                }
            }
            else {
                bestMove = 9999;
                for (GameMove newMove : moves){
                    bestMove = Math.min(bestMove, minimax(newBoard, newMove, depth - 1, PieceColor.opposite(playerColor)));
                }
            }
            return bestMove;
        }
    }
}
