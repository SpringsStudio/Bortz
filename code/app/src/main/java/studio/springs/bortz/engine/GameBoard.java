package studio.springs.bortz.engine;

import studio.springs.bortz.engine.pieces.GamePiece;

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
    public GamePiece getPiece(Position pos){
        return pieceBoard[pos.x][pos.y];
    }
    public void movePiece(Position from, Position to) throws IllegalMoveException{
        if (getPiece(to) != null && getPiece(from).getColor() == getPiece(to).getColor()){
            throw new IllegalMoveException("You cannot take your own piece.");
        }
        else if (getPiece(from).canMove(from, to)){
            pieceBoard[to.x][to.y] = pieceBoard[from.x][from.y];
            pieceBoard[from.x][from.y] = null;
        }
        else throw new IllegalMoveException("This piece cannot move like this");
    }
}
