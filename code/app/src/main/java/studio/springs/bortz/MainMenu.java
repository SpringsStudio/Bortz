package studio.springs.bortz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Button upButton = (Button) findViewById(R.id.buttonUp);
        upButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Not Implemented!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void goToTraining(View view) {
        Intent train = new Intent(this, TrainingMenu.class);
        startActivity(train);
    }

    public void goToSettings(View view) {
        Intent set = new Intent(this, SettingsMenu.class);
        startActivity(set);
    }
}
