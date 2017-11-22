package studio.springs.bortz.engine.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import studio.springs.bortz.engine.utils.GameMove;
import studio.springs.bortz.engine.utils.IllegalMoveException;
import studio.springs.bortz.engine.utils.Position;
import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.GamePieceFactory;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;

public class BoardLogic {
    private GameBoard board;
    List<GameMove> record;
    Queue<GamePiece> capturedPieces;

    public BoardLogic(GameBoard board) {
        this.board = board;
        record = new ArrayList<>();
        capturedPieces = new LinkedList<>();
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
    public static boolean canMovePiece(GameBoard board, PieceColor playerColor, Position from, Position to){
        final GamePiece toPiece = board.getPiece(to);
        final GamePiece fromPiece = board.getPiece(from);
        return !isNowOpponentsTurn(playerColor, fromPiece) &&
                !isOpponentsPieceChosen(fromPiece, toPiece) &&
                !isPositionOutOfBounds(board, to) &&
                fromPiece.canMove(Position.Subtract(to, from));
    }
    public boolean canMovePiece(Position from, Position to){
        return canMovePiece(this.board, getPlayerColor(),from,to);
    }
    public static boolean canDropPiece(GameBoard board, PieceColor playerColor, Position pos, PieceType type){
        GamePiece piece = GamePieceFactory.createPiece(type,playerColor);
        return !isPositionAlreadyTaken(board, pos) &&
            !isThisPieceAvailable(board, piece);

    }
    public boolean canDropPiece(Position pos, PieceType type){
        return canDropPiece(this.board, getPlayerColor(), pos, type);
    }
    public void movePiece(Position from, Position to) throws IllegalMoveException {
        if (canMovePiece(from, to))
            unsafeMovePiece(from, to);
        else throw new IllegalMoveException("Illegal move attempted!");
    }
    private void unsafeMovePiece(Position from, Position to){
        final GamePiece toPiece = board.getPiece(to);
        final GamePiece fromPiece = board.getPiece(from);
        boolean gameWon = false;
        if (board.isChangesEnabled()) {
            if (isPieceAttacked(toPiece)) {
                gameWon = destroyPieceOrWin(toPiece);
            }
            // If white lion reaches the end or black lion reaches beginning check if they are in danger, if not win.
            if (isLionReachingEndOfBoard(fromPiece, to)) {
                if (isLionWinningSecurely(fromPiece, to)) gameWon = true;
            }
        }
        board.setPiece(from, null);
        // Promote piece if it reaches the end or the beginning of the board.
        if (isPieceReachningEndOfBoardAndNotPromoted(fromPiece, to)){
            board.setPiece(to, fromPiece.promote());
        }
        else {
            board.setPiece(to, fromPiece);
        }
        capturedPieces.add(toPiece);
        record.add(new GameMove(fromPiece.getType(),from, GameMove.MoveType.SIMPLE_MOVEMENT,to));
        if (gameWon) board.addChange(new BoardChange(BoardChange.ChangeType.WIN, new Position(-1,-1), fromPiece));
    }
    public void dropPiece(Position pos, PieceType type) throws IllegalMoveException {
        if (canDropPiece(pos, type)){
            unsafeDropPiece(pos,type);
        }
        else throw new IllegalMoveException("Illegal drop attempted!");
    }
    public void unsafeDropPiece(Position pos, PieceType type){
        GamePiece piece = GamePieceFactory.createPiece(type,getPlayerColor());
        board.removeCapturedPiece(piece);
        board.setPiece(pos, piece);
        record.add(new GameMove(type,null, GameMove.MoveType.DROP,pos));
    }
    public PieceColor getPlayerColor(){
        return record.size() % 2 == 0 ? PieceColor.WHITE : PieceColor.BLACK;
    }
    public void performMove(GameMove move) throws IllegalMoveException{
        switch (move.movement){
            case SIMPLE_MOVEMENT:
                movePiece(move.origin, move.destination);
                break;
            case DROP:
                dropPiece(move.destination, move.piece);
                break;
        }
    }
    public void unsafePerformMove(GameMove move){
        switch (move.movement){
            case SIMPLE_MOVEMENT:
                unsafeMovePiece(move.origin, move.destination);
                break;
            case DROP:
                unsafeDropPiece(move.destination, move.piece);
                break;
        }
    }
    public void undo(){
        GameMove lastMove = record.remove(record.size() - 1);

        switch (lastMove.movement){
            case SIMPLE_MOVEMENT:
                board.setPiece(lastMove.origin,board.getPiece(lastMove.destination));
                GamePiece lastPiece = capturedPieces.remove();
                board.setPiece(lastMove.destination,lastPiece);
                if (lastPiece != null) board.removeCapturedPiece(lastPiece);
                break;
            case DROP:
                board.addCapturedPiece(board.getPiece(lastMove.destination));
                board.setPiece(lastMove.destination, null);
                break;
        }
    }
    public List<GameMove> getMoves(){
        return record;
    }

    public static List<GameMove> possibleMoves(GameBoard board, PieceColor playerColor){
        List<GameMove> moves = new ArrayList<>();
        Position range = new Position(1,1);
        List<Position> boardPositions = Position.listPositions(new Position(0,0), Position.Subtract(board.getSize(),range));

        for(Position pos : boardPositions){
            GamePiece piece = board.getPiece(pos);
            if(piece != null){
                for (Position pos2 : Position.listPositions(Position.Subtract(pos, range), Position.Add(pos, range))) {
                    if (canMovePiece(board, playerColor, pos, pos2)) {
                        moves.add(new GameMove(piece.getType(), pos, GameMove.MoveType.SIMPLE_MOVEMENT, pos2));
                    }
                }
            }
            else {
                for (PieceType type : new PieceType[]{
                        PieceType.GIRAFFE,
                        PieceType.CHICK,
                        PieceType.ELEPHANT}){
                    if(canDropPiece(board, playerColor, pos,type)){
                        moves.add(new GameMove(type, null, GameMove.MoveType.DROP, pos));
                    }
                }
            }
        }
        return moves;
    }
    public List<GameMove> possbleMoves(){
        return possibleMoves(this.board, getPlayerColor());
    }
    public static GameBoard boardAfterMovement(GameBoard board, PieceColor playerColor, GameMove move){
        GameBoard newBoard = new GameBoard(board);
        switch (move.movement){
            case SIMPLE_MOVEMENT:
                GamePiece fromPiece = newBoard.getPiece(move.origin);
                Position to = move.destination;
                newBoard.setPiece(move.origin, null);
                if (isPieceReachningEndOfBoardAndNotPromoted(newBoard, fromPiece, to)){
                    newBoard.setPiece(to, fromPiece.promote());
                }
                else {
                    newBoard.setPiece(to, fromPiece);
                };
                break;
            case DROP:
                GamePiece piece = GamePieceFactory.createPiece(move.piece,playerColor);
                newBoard.removeCapturedPiece(piece);
                newBoard.setPiece(move.destination, piece);
                break;
        }
        return newBoard;

    }
    public GameBoard boardAfterMovement(GameMove move){
        return boardAfterMovement(this.board, getPlayerColor(), move);
    }
    public List<GameBoard> possibleBoards(){
        List<GameMove> gameMoves = possbleMoves();
        List<GameBoard> boards = new ArrayList<>(gameMoves.size());
        for (GameMove move : gameMoves) {
            boards.add(boardAfterMovement(move));
        }
        return boards;
    }

    private boolean destroyPieceOrWin(GamePiece toPiece){
        if (toPiece.getType() == PieceType.LION) {
            return true;
        }
        else {
            GamePiece demotedPiece = toPiece.demote();
            GamePiece capturedPiece = demotedPiece == null ? toPiece : demotedPiece;

            board.addCapturedPiece(capturedPiece.swapColor());
            return false;
        }
    }

    private boolean isLionWinningSecurely(GamePiece fromPiece ,Position to){
        for (int xi = -1; xi < 2; xi++){
            for (int yi = -1; yi < 2; yi++){
                GamePiece checkPiece = board.getPiece(Position.Add(to, new Position(xi,yi)));
                if (isLionInDangerFromParticularPiece(checkPiece, fromPiece, xi, yi)){
                    return false;
                }

            }
        }
        return true;
    }

    private boolean isLionInDangerFromParticularPiece(GamePiece checkPiece, GamePiece fromPiece, int xi, int yi){
        return checkPiece != null && fromPiece.getColor() != checkPiece.getColor() && checkPiece.canMove(new Position(-xi, -yi));
    }
    private static boolean isNowOpponentsTurn(PieceColor playerColor, GamePiece piece){
        return piece.getColor() != playerColor;
    }
    private boolean isNowOpponentsTurn(GamePiece piece){
        return isNowOpponentsTurn(getPlayerColor(), piece);
    }

    private static boolean isOpponentsPieceChosen(GamePiece fromPiece, GamePiece toPiece){
        return toPiece != null && fromPiece.getColor() == toPiece.getColor();
    }

    private static boolean isPositionAlreadyTaken(GameBoard board, Position pos){
        return board.getPiece(pos) != null;
    }
    private boolean isPositionAlreadyTaken(Position pos){
        return isPositionAlreadyTaken(this.board,pos);
    }

    private static boolean isThisPieceAvailable(GameBoard board, GamePiece piece){
        return board.countCapturedPieces(piece) < 1;
    }
    private boolean isThisPieceAvailable(GamePiece piece){
        return isThisPieceAvailable(this.board, piece);
    }

    private boolean isPieceAttacked(GamePiece piece){
        return piece != null;
    }

    private boolean isLionReachingEndOfBoard(GamePiece fromPiece, Position to){
        return fromPiece.getType() == PieceType.LION && (fromPiece.getColor() == PieceColor.BLACK &&
                to.y == 0 || fromPiece.getColor() == PieceColor.WHITE && to.y == board.getSize().y - 1);
    }
    private static boolean isPieceReachningEndOfBoardAndNotPromoted(GameBoard board, GamePiece fromPiece, Position to) {
        return ((to.y == board.getSize().y - 1 && fromPiece.getColor() == PieceColor.WHITE ) ||
                (to.y == 0 && fromPiece.getColor() == PieceColor.BLACK)) && fromPiece.promote() != null;
    }
    private boolean isPieceReachningEndOfBoardAndNotPromoted(GamePiece fromPiece, Position to){
        return isPieceReachningEndOfBoardAndNotPromoted(this.board, fromPiece, to);
    }
    private boolean isPositionOutOfBound(Position pos){
        return isPositionOutOfBounds(this.board,pos);
    }
    private static boolean isPositionOutOfBounds(GameBoard board, Position pos){
        return pos.x < 0 || pos.y < 0 ||
                pos.x >= board.getSize().x || pos.y >= board.getSize().y;
    }
}
