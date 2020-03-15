package tejashbutani.medicalassistance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import tejashbutani.Myadapter.Myaddtepter;
import tejashbutani.User.User;

public class View_report extends AppCompatActivity {

    ListView listView;
     String ss_mobs,sts;
     LinearLayout rlay;
     TextView t1,t2,t3,t4,t5,t6;

    ArrayList<User> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        rlay = findViewById(R.id.rlay);
        t1 = findViewById(R.id.r1);
        t2 = findViewById(R.id.r2);
        t3 = findViewById(R.id.r3);
        t4 = findViewById(R.id.r4);
        t5 = findViewById(R.id.r5);
        t6 = findViewById(R.id.r6);



        listView = findViewById(R.id.list);
        SharedPreferences sharedPreferencesget = getSharedPreferences("key",MODE_PRIVATE);
        String secsh = sharedPreferencesget.getString("mob","default_data");
        sts = sharedPreferencesget.getString("rep","default_data");
        ss_mobs= secsh;
        if (sts.equals("his")){
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.VISIBLE);
            t4.setVisibility(View.VISIBLE);
            t5.setVisibility(View.VISIBLE);
            t6.setVisibility(View.VISIBLE);


        }else  if (sts.equals("med")){
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.GONE);
            t4.setVisibility(View.VISIBLE);
            t5.setVisibility(View.GONE);
            t6.setVisibility(View.GONE);

        }else if (sts.equals("pre")){
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
            t5.setVisibility(View.VISIBLE);
            t6.setVisibility(View.GONE);

        }else{
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
            t5.setVisibility(View.GONE);
            t6.setVisibility(View.VISIBLE);
        }





        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("/Report/" + ss_mobs+ "/dates").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                arrayList.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    arrayList.add(new User(snapshot.getString("Date"),
                            snapshot.getString("Name"),
                            snapshot.getString("Parameter"),
                            snapshot.getString("Medic"),
                            snapshot.getString("Prevent"),
                            snapshot.getString("Diet Details")));
                }
                Myaddtepter myaddtepter = new Myaddtepter(arrayList, View_report.this);
                listView.setAdapter(myaddtepter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        t1.setText("Date: "+arrayList.get(i).d);
                        t2.setText("Report Name: "+arrayList.get(i).c);
                        t3.setText("Report Value: "+arrayList.get(i).p);
                        t4.setText("Medication :"+arrayList.get(i).m);
                        t5.setText("Prevention: "+arrayList.get(i).pr);
                        t6.setText("Diet Details: " +arrayList.get(i).di);
                       listView.setVisibility(View.GONE);
                       rlay.setVisibility(View.VISIBLE);

                    }
                });
                if (arrayList.isEmpty()){
                    Toast.makeText(View_report.this, "No service done till date.", Toast.LENGTH_SHORT).show();
                }
            }
        });



            }
        }


