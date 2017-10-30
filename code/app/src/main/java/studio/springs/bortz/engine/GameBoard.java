package studio.springs.bortz.engine;

import android.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameBoard {
    private GamePiece[][] pieceBoard;
    private Map<Pair<PieceColor,PieceType>,Integer> capturedPieces;
    Queue<GameChange> changes;

    public GameBoard(int width, int height) {
        pieceBoard = new GamePiece[width][height];
        capturedPieces = new HashMap<>();
        changes = new LinkedList<>();
    }
    public void placePiece(Position pos, GamePiece piece) throws IllegalMoveException {
        if (pieceBoard[pos.x][pos.y] != null) {
            throw new IllegalMoveException("A piece cannot be placed on a square that is already taken");
        }
        else {
            pieceBoard[pos.x][pos.y] = piece;
            changes.add(new GameChange(GameChange.ChangeType.PIECE_ADDED, pos, piece));
        }
    }
    public GamePiece getPiece(Position pos){
        return pieceBoard[pos.x][pos.y];
    }
    private void setPiece(Position pos, GamePiece piece){
        pieceBoard[pos.x][pos.y] = piece;
    }
    public Integer countCapturedPieces(PieceType type, PieceColor color){
        final Integer count = capturedPieces.get(new Pair<>(color, type));
        return count == null ? 0 : count;
    }
    public void removeCapturedPiece(PieceType type, PieceColor color){
        capturedPieces.put(new Pair<>(color, type), countCapturedPieces(type, color) - 1);
        changes.add(new GameChange(GameChange.ChangeType.PIECE_PLACED, null, GamePieceFactory.createPiece(type, color)));
    }
    private void addCapturedPiece(PieceType type, PieceColor color){
        capturedPieces.put(new Pair<>(color, type), countCapturedPieces(type, color) + 1);
    }
    public void movePiece(Position from, Position to) throws IllegalMoveException{
        if (getPiece(to) != null && getPiece(from).getColor() == getPiece(to).getColor()){
            throw new IllegalMoveException("You cannot take your own piece.");
        }
        else if (getPiece(from).canMove(Position.Subtract(from,to))){
            changes.add(new GameChange(GameChange.ChangeType.PIECE_ADDED, to, getPiece(from)));
            changes.add(new GameChange(GameChange.ChangeType.PIECE_REMOVED, from, getPiece(from)));
            if (getPiece(to) != null) {
                PieceType type = getPiece(to).getType() == PieceType.CHICKEN ? PieceType.CHICK : getPiece(to).getType();
                addCapturedPiece(type, getPiece(to).getColor());
                changes.add(new GameChange(GameChange.ChangeType.PIECE_CAPTURED, null, getPiece(to)));
            }
            setPiece(to, getPiece(from));
            setPiece(from, null);
        }
        else throw new IllegalMoveException("This piece cannot move like this");
    }
}
