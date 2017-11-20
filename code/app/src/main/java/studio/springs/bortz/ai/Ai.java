package studio.springs.bortz.ai;

import studio.springs.bortz.engine.GameMove;
import studio.springs.bortz.engine.board.BoardLogic;
import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.pieces.PieceColor;

public abstract class Ai {
    public abstract GameMove calculateMove();
    protected GameBoard board;
    protected BoardLogic logic;
    protected PieceColor aiColor;

    public Ai(BoardLogic logic, GameBoard board, PieceColor aiColor){
        this.board = board;
        this.aiColor = aiColor;
        this.logic = logic;
    }


}
