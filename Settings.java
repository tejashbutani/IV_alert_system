package tejashbutani.medicalassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void logout(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("key",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mob", "");
        editor.commit();
        startActivity(new Intent(this,MainOptions.class));
        finish();
    }

    public void add_medical_report(View view) {
        startActivity(new Intent(this,Add_Report.class));
    }

    public void add_upcoming(View view) {
        startActivity(new Intent(this,addUpcoming.class));
    }
}
