package studio.springs.bortz.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsCapture {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public static final boolean DEFAULT_GUIDES = true;
    public static final ThemeManager.Theme DEFAULT_THEME = ThemeManager.Theme.CHESS;

    public SettingsCapture(Context context){
        pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public ThemeManager.Theme getTheme(){
        return ThemeManager.Theme.values()[pref.getInt("THEME", DEFAULT_THEME.ordinal())];
    }

    public void setTheme(ThemeManager.Theme theme){
        editor.putInt("THEME", theme.ordinal());
        editor.commit();
    }

    public boolean getGuides(){
        return pref.getBoolean("GUIDES", DEFAULT_GUIDES);
    }

    public void setGuides(boolean guidesEnabled){
        editor.putBoolean("GUIDES", guidesEnabled);
        editor.commit();
    }

}
