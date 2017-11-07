package studio.springs.bortz.engine.board;

import android.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import studio.springs.bortz.engine.GameChange;
import studio.springs.bortz.engine.Position;
import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameBoard {
    private GamePiece[][] pieceBoard;
    private Map<Pair<PieceColor,PieceType>,Integer> capturedPieces;
    public Queue<GameChange> changes;

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
            changes.add(new GameChange(GameChange.ChangeType.PIECE_ADDED, pos, piece));
        }
        else {
            changes.add(new GameChange(GameChange.ChangeType.PIECE_REMOVED, pos, getPiece(pos)));
        }
        pieceBoard[pos.x][pos.y] = piece;
    }
    public Position getSize(){
        return new Position(pieceBoard.length, pieceBoard[0].length);
    }
    public Integer countCapturedPieces(PieceType type, PieceColor color){
        final Integer count = capturedPieces.get(new Pair<>(color, type));
        return count == null ? 0 : count;
    }
    void removeCapturedPiece(PieceType type, PieceColor color){
        capturedPieces.put(new Pair<>(color, type), countCapturedPieces(type, color) - 1);
        changes.add(new GameChange(GameChange.ChangeType.PIECE_PLACED,
                new Position(countCapturedPieces(type, color),-1), GamePieceFactory.createPiece(type, color)));
    }
    void addCapturedPiece(PieceType type, PieceColor color){
        changes.add(new GameChange(GameChange.ChangeType.PIECE_CAPTURED,
                new Position(countCapturedPieces(type, color),-1), GamePieceFactory.createPiece(type, color)));
        capturedPieces.put(new Pair<>(color, type), countCapturedPieces(type, color) + 1);
    }
}
