package tejashbutani.medicalassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class Splash extends AppCompatActivity {
    TextView txt;
    ImageView logo;
    LinearLayout c,i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txt =findViewById(R.id.txt);
        logo =findViewById(R.id.logo);
        c =findViewById(R.id.c);
        i =findViewById(R.id.i);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Animation bottomInAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_in);
        Animation topInAnimation = AnimationUtils.loadAnimation(this,R.anim.top_in);
        Animation leftout = AnimationUtils.loadAnimation(this,R.anim.right_in);
        fadeInAnimation.setDuration(1000);
        bottomInAnimation.setDuration(1000);
        topInAnimation.setDuration(1000);
        txt.setText("\"We care your health\"");

        c.startAnimation(bottomInAnimation);
        i.startAnimation(topInAnimation);
        logo.startAnimation(fadeInAnimation);
        txt.startAnimation(fadeInAnimation);
        logo.setClickable(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               start();
                //finish();
            }
        }, 2000);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("d", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                    }
                });

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

    }

    private void start() {
        SharedPreferences sharedPreferencesget = getSharedPreferences("key",MODE_PRIVATE);
        String secsh = sharedPreferencesget.getString("mob","");
        if (secsh.equals("")){
            Intent startMainopt = new Intent(this,MainOptions.class);
            startActivity(startMainopt);
        }else{
            Intent startMain = new Intent(this,MainActivity.class);
            startActivity(startMain);
        }

        logo.setVisibility(View.GONE);
        c.setVisibility(View.GONE);
        overridePendingTransition(R.anim.fade_in,R.anim.stay);
        finish();
    }






}

