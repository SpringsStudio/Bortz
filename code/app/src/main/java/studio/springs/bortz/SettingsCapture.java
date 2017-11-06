package studio.springs.bortz;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsCapture {

    private static SettingsCapture settingsCapture;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    protected SettingsCapture(Context context){
        pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public ThemeManager.Theme getTheme(){
        return ThemeManager.Theme.values()[pref.getInt("THEME", ThemeManager.DEFAULT_THEME.ordinal())];
    }

    public void setTheme(int themeID){
        editor.putInt("THEME", themeID);
        editor.commit();
    }

    public boolean getGuides(){
        return pref.getBoolean("GUIDES", ThemeManager.DEFAULT_GUIDES);
    }

    public void setGuides(boolean guidesEnabled){
        editor.putBoolean("GUIDES", guidesEnabled);
        editor.commit();
    }

}
