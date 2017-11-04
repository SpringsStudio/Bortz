package studio.springs.bortz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SettingsMenu extends AppCompatActivity {

    private ThemeManager themeManager = new ThemeManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        List<String> themeNames = themeManager.getThemesNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, themeNames);

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);

    }

}
