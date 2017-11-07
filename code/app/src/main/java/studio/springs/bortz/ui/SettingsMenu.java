package studio.springs.bortz.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import studio.springs.bortz.R;
import studio.springs.bortz.ui.utils.SettingsCapture;
import studio.springs.bortz.ui.utils.ThemeManager;

public class SettingsMenu extends AppCompatActivity implements AdapterView.OnItemClickListener,
        CompoundButton.OnCheckedChangeListener {

    ListView listView;
    Switch guidesSwitch;
    private SettingsCapture settingsCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        settingsCapture = new SettingsCapture(this);

        guidesSwitch = findViewById(R.id.switch1);
        guidesSwitch.setOnCheckedChangeListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, ThemeManager.getThemesNames());

        listView = findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(settingsCapture.getTheme().ordinal(), true);
        listView.setOnItemClickListener(this);

        guidesSwitch.setChecked(settingsCapture.getGuides());

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id ){
        settingsCapture.setTheme(ThemeManager.Theme.values()[position]);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        settingsCapture.setGuides(isChecked);
    }

}
