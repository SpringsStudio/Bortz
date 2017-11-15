package studio.springs.bortz.ui;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Queue;

import studio.springs.bortz.R;
import studio.springs.bortz.engine.GameClient;
import studio.springs.bortz.engine.GameInterface;
import studio.springs.bortz.engine.board.BoardChange;
import studio.springs.bortz.engine.utils.IllegalMoveException;
import studio.springs.bortz.engine.utils.Position;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.ui.utils.SettingsCapture;
import studio.springs.bortz.ui.utils.ThemeManager;

public class Game extends AppCompatActivity {
    private Resources res;
    private final GameClient client = new GameClient();
    private final GameInterface gInterface = client.getInterface();
    private ThemeManager tmanager;
    private SettingsCapture capture;
    private PieceColor currentPlayerColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        currentPlayerColor = PieceColor.WHITE;
        capture = new SettingsCapture(this);
        tmanager = new ThemeManager(capture.getTheme(), capture.getGuides());

        res = getResources();
        tmanager.createDrawableMap(res);
        prepareView();
        tmanager.createDrawableMap(res);
        updateView();
    }

    public void boardButtonPressed(View v) {
        final String id = res.getResourceEntryName(v.getId());
        final int buttonX = Character.getNumericValue(id.charAt(6));
        final int buttonY = Character.getNumericValue(id.charAt(7));
        gInterface.selectBoardSquare(new Position(buttonX, buttonY));

        if (gInterface.hasMoveEnded()){
            try {
                client.performMove();
                currentPlayerColor = PieceColor.opposite(currentPlayerColor);
                updateView();
            } catch (IllegalMoveException ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void capturedPieceButtonPressed(View v){
        final String id = res.getResourceEntryName(v.getId());
        final PieceType type = PieceType.values()[Character.getNumericValue(id.charAt(13))];
        final PieceColor color = id.charAt(11) == 'B' ? PieceColor.BLACK : PieceColor.WHITE;
        if(color == currentPlayerColor) {
            gInterface.selectCapturedPiece(type);
        }

    }
    void prepareView(){
        ImageButton button;

        for(PieceType piece : new PieceType[] {
                PieceType.GIRAFFE,
                PieceType.CHICK,
                PieceType.ELEPHANT}){
            for(int layer = 0; layer < 2; layer++){
                for(PieceColor color : PieceColor.values()) {
                    button = getCapturedPieceButton(color,  piece, layer);
                    button.setImageDrawable(tmanager.getPieceDrawable(piece));
                    if (color == PieceColor.WHITE) button.setColorFilter(Color.WHITE);
                }
            }
        }
    }

    ImageButton getBoardButton(Position pos){
       return findViewById(res.getIdentifier("button" + pos.x + pos.y, "id", this.getPackageName()));
    }

    ImageButton getCapturedPieceButton(PieceColor color, PieceType type, int layer){
        String colorChar = color == PieceColor.BLACK ? "B" : "W";
        return findViewById(res.getIdentifier("imageButton" + colorChar + layer + type.ordinal(), "id", this.getPackageName()));
    }

    void updateView(){
        final Queue<BoardChange> changes = client.getChanges();
        ImageButton button;
        BoardChange change;
        while (changes.peek() != null) {
            change = changes.remove();
            // ------------------ DEBUGGING INFO ---------------------------------------------------------- //
            System.out.println("[Change] Type: " + change.getType().name() + "; Position: x=" +
                    change.getPosition().x + ", y=" + change.getPosition().y + "; Piece: type=" +
                    change.getPiece().getType().name() + ", color=" + change.getPiece().getColor().name());
            // -------------------------------------------------------------------------------------------- //
            switch (change.getType()) {
                case PIECE_ADDED:
                    button = getBoardButton(change.getPosition());
                    button.setAlpha(1.0f);
                    button.setImageDrawable(tmanager.getPieceDrawable(change.getPiece().getType()));
                    if (change.getPiece().getColor() == PieceColor.WHITE) {
                        button.setRotation(0);
                        button.setColorFilter(Color.WHITE);
                    }
                    else{
                        button.setRotation(180);
                        button.setColorFilter(null);
                    }
                    break;
                case PIECE_REMOVED:
                    button = getBoardButton(change.getPosition());
                    button.setAlpha(0.0f);
                    break;
                case PIECE_CAPTURED:
                    button = getCapturedPieceButton(change.getPiece().getColor(),change.getPiece().getType(), change.getPosition().x);
                    button.setVisibility(View.VISIBLE);
                    break;
                case PIECE_DROPPED:
                    button = getCapturedPieceButton(change.getPiece().getColor(),change.getPiece().getType(),change.getPosition().x);
                    button.setVisibility(View.INVISIBLE);
                    break;
                case WIN:
                    String winner = (change.getPiece().getColor() == PieceColor.WHITE) ? "White" : "Black";
                    Toast.makeText(getApplicationContext(), winner + " wins!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
