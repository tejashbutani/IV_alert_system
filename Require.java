package tejashbutani.medicalassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Require extends AppCompatActivity {
    Spinner spinner;
    TextView textView,b,c;

    ArrayList<String> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_require);



        spinner = findViewById(R.id.sp);
        textView = findViewById(R.id.text);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        arrayList.add("Blood test");
        arrayList.add("Urine test");
        arrayList.add("Blood Pressure test");

        ArrayAdapter arrayAdapter = new ArrayAdapter(Require.this,R.layout.support_simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                if (spinner.getSelectedItem().toString().equals("Blood test")){
                    textView.setVisibility(View.VISIBLE);
                    b.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);

                }else if (spinner.getSelectedItem().toString().equals("Urine test")){
                    b.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);

                }else if (spinner.getSelectedItem().toString().equals("Blood Pressure test")){
                    c.setVisibility(View.VISIBLE);
                    b.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}