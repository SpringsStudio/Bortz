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
import android.widget.Toast;

public class SettingsMenu extends AppCompatActivity implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    ListView listView;
    Switch guidesSwitch;
    SharedPreferences pref;
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



    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id ){
        TextView text = (TextView) view;
        Toast.makeText(this, text.getText() ,Toast.LENGTH_LONG).show();
        listView.setSelector(android.R.color.darker_gray);
    }

    @Override
    public void onCheckedChanged(CompoundButton  buttonView, boolean isChecked){
        if (isChecked)
            Toast.makeText(this, "guides enabled" ,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "guides disabled" ,Toast.LENGTH_LONG).show();
    }

}
