package studio.springs.bortz;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Game extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
    final Resources res = getResources();

    public void buttonPressed(View v) {
        String id = res.getResourceEntryName(v.getId());
        int buttonX = Character.getNumericValue(id.charAt(6));
        int buttonY = Character.getNumericValue(id.charAt(7));
        Toast.makeText(getApplicationContext(), "Pressed button at position x: " + buttonX + " y: " + buttonY, Toast.LENGTH_SHORT).show();
    }
}
