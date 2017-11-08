package studio.springs.bortz.engine.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import studio.springs.bortz.engine.utils.Position;
import studio.springs.bortz.engine.pieces.GamePiece;

public class GameBoard {
    private GamePiece[][] pieceBoard;
    private Map<GamePiece,Integer> capturedPieces;
    public Queue<BoardChange> changes;

    GameBoard(int width, int height) {
        pieceBoard = new GamePiece[width][height];
        capturedPieces = new HashMap<>();
        changes = new LinkedList<>();
    }
    public GamePiece getPiece(Position pos){
        if (pos.MoreThan(Position.Subtract(getSize(), new Position(1,1))) || pos.LessThan(new Position(0,0))){
            return null;
        }
        else {
            return pieceBoard[pos.x][pos.y];
        }
    }
    void setPiece(Position pos, GamePiece piece){
        if (piece != null){
            changes.add(new BoardChange(BoardChange.ChangeType.PIECE_ADDED, pos, piece));
        }
        else {
            changes.add(new BoardChange(BoardChange.ChangeType.PIECE_REMOVED, pos, getPiece(pos)));
        }
        pieceBoard[pos.x][pos.y] = piece;
    }
    public Position getSize(){
        return new Position(pieceBoard.length, pieceBoard[0].length);
    }
    public Integer countCapturedPieces(GamePiece piece){
        final Integer count = capturedPieces.get(piece);
        return count == null ? 0 : count;
    }
    void removeCapturedPiece(GamePiece piece){
        capturedPieces.put(piece, countCapturedPieces(piece) - 1);
        changes.add(new BoardChange(BoardChange.ChangeType.PIECE_DROPPED,
                new Position(countCapturedPieces(piece),-1), piece));
    }
    void addCapturedPiece(GamePiece piece){
        changes.add(new BoardChange(BoardChange.ChangeType.PIECE_CAPTURED,
                new Position(countCapturedPieces(piece),-1), piece));
        capturedPieces.put(piece, countCapturedPieces(piece) + 1);
    }
}
