package tejashbutani.medicalassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDetails extends AppCompatActivity {

    EditText firstname, lastname, age;
    RadioGroup gender;
    RadioButton choice;
    EditText bloodgroup, weight, height, hereditarydisease, anyallergy;
    EditText resaddress, mobilenumber, emailaddress;
    ProgressBar progressRing;
    LinearLayout content_layout;
    LottieAnimationView check;
    TextView result;

    public static final String FIRSTNAME = "First name";
    public static final String LASTNAME = "Last name";
    public static final String AGE = "Age";
    public static final String GENDER = "Gender";
    public static final String BLOODGROUP = "Blood group";
    public static final String WEIGHT = "Weight";
    public static final String HEIGHT = "Height";
    public static final String HEREDITARYDISEASE = "Hereditary disease";
    public static final String ANYALLERGY = "Any allergy";
    public static final String RESADDRESS = "Res Address";
    public static final String MOBILENUMBER = "Mobile number";
    public static final String EMAILADDRESS = "Email address";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_add_detail);
        progressRing = findViewById(R.id.progressRing);
        content_layout = findViewById(R.id.content_layout);
        check= findViewById(R.id.check);
        result = findViewById(R.id.result);
        result.setVisibility(View.GONE);
        check.setVisibility(View.GONE);
        progressRing.setVisibility(View.GONE);
        content_layout.setVisibility(View.VISIBLE);
    }

    public void submit(View view) {
//Intialization-------------------------------------------------------------------------------------
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        bloodgroup = findViewById(R.id.bloodgroup);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        hereditarydisease = findViewById(R.id.hereditarydisease);
        anyallergy = findViewById(R.id.anyallergy);
        resaddress = findViewById(R.id.resaddress);
        mobilenumber = findViewById(R.id.mobilenumber);
        emailaddress = findViewById(R.id.emailaddress);
        progressRing = findViewById(R.id.progressRing);
        content_layout = findViewById(R.id.content_layout);
//converting into strings---------------------------------------------------------------------------
        String s_firstname = firstname.getText().toString();
        String s_lastname = lastname.getText().toString();
        String s_age = age.getText().toString();
        String s_bloodgroup = bloodgroup.getText().toString();
        String s_weight = weight.getText().toString();
        String s_height = height.getText().toString();
        String s_hereditarydisease = hereditarydisease.getText().toString();
        String s_anyallergy = anyallergy.getText().toString();
        String s_resaddress = resaddress.getText().toString();
        String s_mobilenumber = mobilenumber.getText().toString();
        String s_emailaddress = emailaddress.getText().toString();
        String s_gender;

        if(gender.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
            return;
        }
        int length = s_mobilenumber.length();
        if (s_mobilenumber.isEmpty() || length !=10){
            Toast.makeText(this, "Mobile number invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        int selectedId = gender.getCheckedRadioButtonId();
        choice = findViewById(selectedId);
        s_gender = choice.getText().toString();

//send_method---------------------------------------------------------------------------------------
            DocumentReference mRef = FirebaseFirestore.getInstance().document("userdata/" + s_mobilenumber);
            Map<String, Object> dataToSave = new HashMap<>();
            dataToSave.put(FIRSTNAME, s_firstname);
            dataToSave.put(LASTNAME, s_lastname);
            dataToSave.put(AGE, s_age);
            dataToSave.put(GENDER, s_gender);
            dataToSave.put(BLOODGROUP, s_bloodgroup);
            dataToSave.put(WEIGHT, s_weight);
            dataToSave.put(HEIGHT, s_height);
            dataToSave.put(HEREDITARYDISEASE, s_hereditarydisease);
            dataToSave.put(ANYALLERGY, s_anyallergy);
            dataToSave.put(RESADDRESS, s_resaddress);
            dataToSave.put(MOBILENUMBER, s_mobilenumber);
            dataToSave.put(EMAILADDRESS, s_emailaddress);

            progressRing.setVisibility(View.VISIBLE);
            content_layout.setVisibility(View.GONE);
            mRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressRing.setVisibility(View.GONE);
                    check.setVisibility(View.VISIBLE);
                    result.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gotomain();
                            finish();
                        }
                    }, 1000);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   

                }
            });

    }

    private void gotomain() {
        SharedPreferences sharedPreferences = getSharedPreferences("key",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mob", mobilenumber.getText().toString());
        editor.commit();
        startActivity(new Intent(this,MainActivity.class));
    }
}
