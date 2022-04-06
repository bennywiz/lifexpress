package life.example.xpress;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private static Session yourPreference;
    private SharedPreferences sharedPreferences;

    public static Session getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new Session(context);
        }
        return yourPreference;
    }

    Session(Context context) {
        sharedPreferences = context.getSharedPreferences("YourCustomNamedPreference", Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }
}
