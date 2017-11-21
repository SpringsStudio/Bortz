package studio.springs.bortz.ai;

import java.util.HashMap;
import java.util.Map;

import studio.springs.bortz.engine.GameMove;
import studio.springs.bortz.engine.board.BoardLogic;
import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.engine.utils.Position;

public abstract class Ai {
    public abstract GameMove calculateMove();
    protected GameBoard board;
    protected BoardLogic logic;
    protected PieceColor aiColor;

    Ai(BoardLogic logic, GameBoard board, PieceColor aiColor){
        this.board = board;
        this.aiColor = aiColor;
        this.logic = logic;
    }
    static Map<PieceType,Integer> piecesValuse = new HashMap<>(); static {
        piecesValuse.put(PieceType.LION, 900);
        piecesValuse.put(PieceType.CHICKEN, 70);
        piecesValuse.put(PieceType.GIRAFFE, 50);
        piecesValuse.put(PieceType.ELEPHANT, 30);
        piecesValuse.put(PieceType.CHICK, 10);
    }
    static int evaluateBoard(GameBoard board){
        int value = 0;
        for (Position pos :
                Position.listPositions(new Position(0,0),
                        Position.Subtract(board.getSize(),new Position(1,1)))){
            GamePiece piece = board.getPiece(pos);
            if (piece != null) {
                value += piecesValuse.get(piece.getType()) *
                        (piece.getColor() == PieceColor.WHITE ? 1 : -1);
            }
        }
        return value;
    }

}
