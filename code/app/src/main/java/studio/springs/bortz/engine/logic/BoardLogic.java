package studio.springs.bortz.engine.logic;

import java.util.ArrayList;
import java.util.List;

import studio.springs.bortz.engine.GameMove;
import studio.springs.bortz.engine.utils.IllegalMoveException;
import studio.springs.bortz.engine.utils.Position;
import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class BoardLogic {
    private GameBoard board;
    List<GameMove> record;
    public BoardLogic(GameBoard board) {
        this.board = board;
        record = new ArrayList<>();
    }
    static public GameBoard prepareBoard() {
        GameBoard board = new GameBoard(3,4);

        board.setPiece(new Position(1, 0), GamePieceFactory.createPiece(PieceType.LION, PieceColor.WHITE));
        board.setPiece(new Position(1, 3), GamePieceFactory.createPiece(PieceType.LION, PieceColor.BLACK));
        board.setPiece(new Position(2, 0), GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.WHITE));
        board.setPiece(new Position(0, 3), GamePieceFactory.createPiece(PieceType.GIRAFFE, PieceColor.BLACK));
        board.setPiece(new Position(0, 0), GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.WHITE));
        board.setPiece(new Position(2, 3), GamePieceFactory.createPiece(PieceType.ELEPHANT, PieceColor.BLACK));
        board.setPiece(new Position(1, 1), GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.WHITE));
        board.setPiece(new Position(1, 2), GamePieceFactory.createPiece(PieceType.CHICK, PieceColor.BLACK));
        return board;
    }
    public void movePiece(Position from, Position to) throws IllegalMoveException {
        final GamePiece toPiece = board.getPiece(to);
        final GamePiece fromPiece = board.getPiece(from);
        boolean gameWon = false;
        if(fromPiece.getColor() != getPlayerColor()) {
            throw new IllegalMoveException("It's not your turn right now!");
        } else if (toPiece != null && fromPiece.getColor() == toPiece.getColor()) {
            throw new IllegalMoveException("You cannot capture your own piece.");
        } else if (fromPiece.canMove(Position.Subtract(to, from))) {
            if (toPiece != null) {
                if (toPiece.getType() == PieceType.LION) {
                    gameWon = true;
                }
                else {
                    GamePiece demotedPiece = toPiece.demote();
                    GamePiece capturedPiece = demotedPiece == null ? toPiece : demotedPiece;

                    board.addCapturedPiece(capturedPiece.swapColor());
                }
            }
            // If white lion reaches the end or black lion reaches beginning check if they are in danger, if not win.
            if (fromPiece.getType() == PieceType.LION && (
                    fromPiece.getColor() == PieceColor.BLACK && to.y == 0 ||
                    fromPiece.getColor() == PieceColor.WHITE && to.y == board.getSize().y - 1)){
                gameWon = true;
                for (int xi = -1; xi < 2; xi++){
                    for (int yi = -1; yi < 2; yi++){
                        GamePiece checkPiece = board.getPiece(Position.Add(to, new Position(xi,yi)));
                        if (checkPiece != null && fromPiece.getColor() != checkPiece.getColor() &&
                                checkPiece.canMove(new Position(-xi, -yi))){
                            gameWon = false;
                            break;
                        }
                    }
                }
            }
            // Promote piece if it reaches the end or the beginning of the board.
            if (((to.y == board.getSize().y - 1 && fromPiece.getColor() == PieceColor.WHITE ) ||
                    (to.y == 0 && fromPiece.getColor() == PieceColor.BLACK)) &&
                    fromPiece.promote() != null){
                board.setPiece(to, fromPiece.promote());
            }
            else {
                board.setPiece(to, fromPiece);
            }
            board.setPiece(from, null);

            record.add(new GameMove(fromPiece.getType(),from, GameMove.MoveType.SIMPLE_MOVEMENT,to));
            if (gameWon) board.changes.add(new BoardChange(BoardChange.ChangeType.WIN, new Position(-1,-1), fromPiece));
        } else throw new IllegalMoveException("This piece cannot move like this");
    }
    PieceColor getPlayerColor(){
        return record.size() % 2 == 0 ? PieceColor.WHITE : PieceColor.BLACK;
    }
    public void dropPiece(Position pos, PieceType type) throws IllegalMoveException {
        GamePiece piece = GamePieceFactory.createPiece(type,getPlayerColor());
        if (board.getPiece(pos) != null) {
            throw new IllegalMoveException("A piece cannot be placed on a square that is already taken");
        }
        else if (board.countCapturedPieces(piece) < 1){
            throw new IllegalMoveException("You do not have this type of piece captured");
        }
        else {
            board.setPiece(pos, piece);
            board.removeCapturedPiece(piece);
            record.add(new GameMove(piece.getType(),null, GameMove.MoveType.DROP,pos));
        }
    }
    public List<GameMove> getMoves(){
        return record;
    }
}
