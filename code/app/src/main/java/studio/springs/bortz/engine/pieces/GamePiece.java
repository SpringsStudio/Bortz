package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public abstract class GamePiece {
    private PieceColor color;

    public abstract boolean canMove(Position vector);
    public abstract PieceType getType();
    public PieceColor getColor() {return color; }
    void setColor(PieceColor color) {this.color = color; }
}
