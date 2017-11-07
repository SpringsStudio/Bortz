package studio.springs.bortz.engine;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.utils.Position;

public class GameChange {
    public enum ChangeType {
        PIECE_REMOVED,
        PIECE_ADDED,
        PIECE_CAPTURED,
        PIECE_PLACED,
        WIN
    }
    private final ChangeType type;
    private final Position pos;
    private final GamePiece piece;

    public GameChange(ChangeType type, Position pos, GamePiece piece) {
        this.type = type;
        this.pos = pos;
        this.piece = piece;
    }
    public ChangeType getType() {return type;}
    public Position getPosition() {return pos;}
    public GamePiece getPiece() {return piece;}
}
