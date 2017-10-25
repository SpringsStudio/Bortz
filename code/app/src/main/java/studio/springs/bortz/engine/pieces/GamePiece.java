package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public abstract class GamePiece {
    private PieceColor color;

    public abstract boolean canMove(Position from, Position to);
    public abstract PieceType getType();
    public PieceColor getColor() {return color; }
    void setColor(PieceColor color) {this.color = color; }
    public void swapColor() {
        if (color == PieceColor.BLACK) {
            color = PieceColor.WHITE;
        }
        else {
            color = PieceColor.BLACK;
        }
    }
}
