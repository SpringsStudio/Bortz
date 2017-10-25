package studio.springs.bortz.engine;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameBoard {
    GamePiece[][] pieceBoard;

    public GameBoard(int width, int height) {
        pieceBoard = new GamePiece[width][height];
    }
    public void placePiece(Position pos, GamePiece piece) throws IllegalMoveException {
        if (pieceBoard[pos.x][pos.y] != null) {
            throw new IllegalMoveException("A piece cannot be placed on a square that is already taken");
        }
        else {
            pieceBoard[pos.x][pos.y] = piece;
        }
    }
}
