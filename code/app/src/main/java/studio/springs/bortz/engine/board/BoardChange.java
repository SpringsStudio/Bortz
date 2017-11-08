package studio.springs.bortz.engine.board;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.utils.Position;

public class BoardChange {
    public enum ChangeType {
        PIECE_REMOVED,
        PIECE_ADDED,
        PIECE_CAPTURED,
        PIECE_DROPPED,
        WIN
    }
    private final ChangeType type;
    private final Position pos;
    private final GamePiece piece;

    public BoardChange(ChangeType type, Position pos, GamePiece piece) {
        this.type = type;
        this.pos = pos;
        this.piece = piece;
    }
    public ChangeType getType() {return type;}
    public Position getPosition() {return pos;}
    public GamePiece getPiece() {return piece;}
}
