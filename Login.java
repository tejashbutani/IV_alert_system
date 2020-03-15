package tejashbutani.medicalassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    EditText passedt;
    Button go;
    ProgressBar lo_rg;
    TextView dds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passedt= findViewById(R.id.passedt);
        go = findViewById(R.id.go);
        lo_rg = findViewById(R.id.lo_rng);
        dds = findViewById(R.id.stsii);
        passedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lo_rg.setVisibility(View.GONE);
                dds.setVisibility(View.GONE);
                go.setVisibility(View.VISIBLE);
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go();
                go.setVisibility(View.GONE);
                lo_rg.setVisibility(View.VISIBLE);


            }
        });
    }
    public void fetch() {
        DocumentReference mRef = FirebaseFirestore.getInstance().document("userdata/" + passedt.getText().toString());
        mRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    hi();
                }else{
                    lo_rg.setVisibility(View.GONE);
                   dds.setVisibility(View.VISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "fail to load", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hi() {
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }


    public void go() {
         ((InputMethodManager)Login.this.getSystemService(Context.INPUT_METHOD_SERVICE))
                       .hideSoftInputFromWindow(findViewById(R.id.lol).getWindowToken(),0);
        SharedPreferences sharedPreferences = getSharedPreferences("key",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mob", passedt.getText().toString());
        editor.commit();
        fetch();
    }
}
