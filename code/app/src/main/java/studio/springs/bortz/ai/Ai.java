package studio.springs.bortz.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import studio.springs.bortz.engine.utils.GameMove;
import studio.springs.bortz.engine.board.BoardLogic;
import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.engine.utils.Position;

public abstract class Ai extends Thread{
    public abstract GameMove calculateMove();
    protected GameBoard board;
    protected BoardLogic logic;
    protected PieceColor aiColor;
    private GameMove calculatedMove;
    private List<Position> boardPositions;
    private GamePiece[] capturablePieces = new GamePiece[] {
            GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.BLACK),
            GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.WHITE),
            GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.BLACK),
            GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.WHITE),
            GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.BLACK),
            GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.WHITE)
    };

    Ai(BoardLogic logic, GameBoard board, PieceColor aiColor){
        this.board = board;
        this.aiColor = aiColor;
        this.logic = logic;
        this.boardPositions = Position.listPositions(
            new Position(0,0),
            Position.Subtract(this.board.getSize(),new Position(1,1)));
    }
    private static Map<PieceType,Integer> piecesValuse = new HashMap<>(); static {
        piecesValuse.put(PieceType.LION, 900);
        piecesValuse.put(PieceType.CHICKEN, 70);
        piecesValuse.put(PieceType.GIRAFFE, 50);
        piecesValuse.put(PieceType.ELEPHANT, 30);
        piecesValuse.put(PieceType.CHICK, 10);
    }


    int evaluateBoard(GameBoard board){
        int value = 0;
        for (Position pos : boardPositions){
            GamePiece piece = board.getPiece(pos);
            if (piece != null) {
                value += piecesValuse.get(piece.getType()) *
                        (piece.getColor() == PieceColor.WHITE ? 1 : -1);
            }
        }
        for (GamePiece piece : capturablePieces){
            value += board.countCapturedPieces(piece) *
                    piecesValuse.get(piece.getType()) * 0.5 *
                    (piece.getColor() == PieceColor.WHITE ? 1 : -1);
        }
        return value;
    }
    public void run(){
        calculatedMove = calculateMove();
    }
    public GameMove getLastMove(){
        return calculatedMove;
    }
    public PieceColor getAiColor(){return aiColor;}
}
