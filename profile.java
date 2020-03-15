package tejashbutani.medicalassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Profile extends AppCompatActivity {

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
    TextView nametxt, agetxt, gendertxt;
    TextView bloodgrouptxt, weighttxt, heighttxt, hereditarydiseasetxt, anyallergytxt,maintxt;
    TextView resaddresstxt, mobilenumbertxt, emailaddresstxt;
    String ss_mob;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nametxt = findViewById(R.id.nametxt);
        agetxt = findViewById(R.id.agetxt);
        ll = findViewById(R.id.content_layoutp);
        maintxt = findViewById(R.id.maintxtp);
        gendertxt = findViewById(R.id.gendertxt);
        bloodgrouptxt = findViewById(R.id.bloodgrouptxt);
        weighttxt = findViewById(R.id.weighttxt);
        heighttxt = findViewById(R.id.heighttxt);
        hereditarydiseasetxt = findViewById(R.id.hereditarydiseasetxt);
        anyallergytxt = findViewById(R.id.anyallergytxt);
        resaddresstxt = findViewById(R.id.resaddresstxt);
        mobilenumbertxt = findViewById(R.id.mobilenumbertxt);
        emailaddresstxt = findViewById(R.id.emailaddresstxt);
        Animation bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_in);
        Animation rightInAnimation = AnimationUtils.loadAnimation(this,R.anim.right_in);
        Animation leftAnimation = AnimationUtils.loadAnimation(this,R.anim.left_in);
        leftAnimation.setDuration(500);
        bottomAnimation.setDuration(500);
        maintxt.startAnimation(leftAnimation);
        ll.startAnimation(bottomAnimation);
        SharedPreferences sharedPreferencesget = getSharedPreferences("key",MODE_PRIVATE);
        String secsh = sharedPreferencesget.getString("mob","default_data");
        ss_mob= secsh;
        fetch();

    }

    public void fetch() {
        DocumentReference mRef = FirebaseFirestore.getInstance().document("userdata/" + ss_mob);
                    mRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                    String s_firstname = documentSnapshot.getString(FIRSTNAME);
                    String s_lastname = documentSnapshot.getString(LASTNAME);
                    String s_age = documentSnapshot.getString(AGE);
                    String s_gender = documentSnapshot.getString(GENDER);
                    String s_bloodgroup = documentSnapshot.getString(BLOODGROUP);
                    String s_weight = documentSnapshot.getString(WEIGHT);
                    String s_height = documentSnapshot.getString(HEIGHT);
                    String s_hereditarydisease = documentSnapshot.getString(HEREDITARYDISEASE);
                    String s_anyallergy = documentSnapshot.getString(ANYALLERGY);
                    String s_resaddress = documentSnapshot.getString(RESADDRESS);
                    String s_mobilenumber = documentSnapshot.getString(MOBILENUMBER);
                    String s_emailaddress = documentSnapshot.getString(EMAILADDRESS);

                    Map<String, Object> data = documentSnapshot.getData();
                    nametxt.setText(s_firstname + " " + s_lastname);
                    agetxt.setText(s_age);
                    gendertxt.setText(s_gender);
                    bloodgrouptxt.setText(s_bloodgroup);
                    weighttxt.setText(s_weight);
                    heighttxt.setText(s_height);
                    hereditarydiseasetxt.setText(s_hereditarydisease);
                    anyallergytxt.setText(s_anyallergy);
                    resaddresstxt.setText(s_resaddress);
                    mobilenumbertxt.setText(s_mobilenumber);
                    emailaddresstxt.setText(s_emailaddress);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

