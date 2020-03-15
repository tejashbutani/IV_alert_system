package tejashbutani.medicalassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addUpcoming extends AppCompatActivity {
    EditText m,n;
    DatePicker d;
    Button a;
    LinearLayout up_lay;
    ProgressBar up_rng;
    LottieAnimationView up_bnb;
    TextView up_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_upcoming);
        a = findViewById(R.id.a);
        m = findViewById(R.id.m);
        d = findViewById(R.id.d);
        n = findViewById(R.id.n);
        up_lay = findViewById(R.id.uplay);
        up_bnb = findViewById(R.id.up_bnb);
        up_rng = findViewById(R.id.up_rng);
        up_txt = findViewById(R.id.up_txt);


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddetails();
            }
        });

    }

    public void adddetails() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


//converting into strings---------------------------------------------------------------------------
        String ns = n.getText().toString();
        String ds = dateFormat.format(cal.getTime());
        String ms = m.getText().toString();


        int length = ms.length();
        if (ms.isEmpty() || length !=10){
            Toast.makeText(this, "Mobile number invalid", Toast.LENGTH_SHORT).show();
            return;
        }

//send_method---------------------------------------------------------------------------------------
        DocumentReference mRef = FirebaseFirestore.getInstance().document("upcoming/" + ms);
        Map<String, Object> dataToSave = new HashMap<>();
        dataToSave.put("CheckupName", ns);
        dataToSave.put("Date", ds);
        up_lay.setVisibility(View.GONE);
        up_rng.setVisibility(View.VISIBLE);

        mRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                up_rng.setVisibility(View.GONE);
                up_bnb.setVisibility(View.VISIBLE);
                up_txt.setVisibility(View.VISIBLE);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addUpcoming.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
// ((InputMethodManager)ServiceHistory.this.getSystemService(Context.INPUT_METHOD_SERVICE))
//                        .hideSoftInputFromWindow(findViewById(R.id.valueLay).getWindowToken(),0);