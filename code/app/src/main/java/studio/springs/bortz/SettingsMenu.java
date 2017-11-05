package studio.springs.bortz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsMenu extends AppCompatActivity implements AdapterView.OnItemClickListener,
        CompoundButton.OnCheckedChangeListener {

    ListView listView;
    Switch guidesSwitch;
    public static SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);
        pref =  getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor =  pref.edit();

        guidesSwitch = findViewById(R.id.switch1);
        guidesSwitch.setOnCheckedChangeListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ThemeManager.getThemesNames());

        listView = findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        loadPreferences();
        listView.setSelector(android.R.color.darker_gray);

    }

    private void loadPreferences(){
        boolean guidesEnabled = pref.getBoolean("GUIDES", ThemeManager.DEFAULT_GUIDES);
        int themeID = pref.getInt("THEME", ThemeManager.DEFAULT_THEME.ordinal());
        System.out.println( ThemeManager.DEFAULT_THEME.ordinal());
        System.out.println( ThemeManager.DEFAULT_GUIDES);
        if (guidesEnabled)
            guidesSwitch.setChecked(true);
        else
            guidesSwitch.setChecked(false);
        listView.setItemChecked(themeID, true);
    }

    private void savePreferences(String keyVal, int themeID){
        editor.putInt("THEME", themeID);
        editor.commit();
    }

    private void savePreferences(String keyVal, boolean guidesEnabled){
        editor.putBoolean("GUIDES", guidesEnabled);
        editor.commit();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id ){
        TextView text = (TextView) view;
        savePreferences("THEME", position);
        //Toast.makeText(this, text.getText()  ,Toast.LENGTH_LONG).show();
        System.out.println(position);
    }

    @Override
    public void onCheckedChanged(CompoundButton  buttonView, boolean isChecked){
        savePreferences("GUIDES", isChecked);
    }

}
