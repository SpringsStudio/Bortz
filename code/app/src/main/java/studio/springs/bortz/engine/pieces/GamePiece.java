package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public abstract class GamePiece {
    PieceColor color;

    public abstract boolean canMove(Position from, Position to);
    public abstract PieceType getType();
    public PieceColor getColor() {return color; }
    public void setColor(PieceColor color) {this.color = color; }
}
