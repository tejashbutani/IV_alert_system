package tejashbutani.medicalassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FetchUpcoming extends AppCompatActivity {

    TextView na,da;
    String ss_num;
    ProgressBar ft_rng;
    LinearLayout cnl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_upcoming);
        na = findViewById(R.id.na);
        da = findViewById(R.id.da);
        cnl = findViewById(R.id.cntlay);
        ft_rng = findViewById(R.id.ft_rng);
        SharedPreferences sharedPreferencesget = getSharedPreferences("key",MODE_PRIVATE);
        String secsh = sharedPreferencesget.getString("mob","default_data");
        ss_num= secsh;
        fetch();
    }

    public void fetch() {
        DocumentReference mRef = FirebaseFirestore.getInstance().document("upcoming/" + ss_num);
        mRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String s_firstname = documentSnapshot.getString("CheckupName");
                    String s_lastname = documentSnapshot.getString("Date");

                    Map<String, Object> data = documentSnapshot.getData();
                    ft_rng.setVisibility(View.GONE);
                   cnl.setVisibility(View.VISIBLE);
                    na.setText("Checkup Name: " + s_firstname);
                    da.setText("Checkup Date: " + s_lastname);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FetchUpcoming.this, "fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
