package tejashbutani.medicalassistance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Add_Report extends AppCompatActivity {
    TextView mob,dat,nam,par,medic,prevent,diet;
    LinearLayout rp_lay;
    ProgressBar rp_rng;
    LottieAnimationView rp_bnb;
    TextView rp_txt;


    EditText mResultEt;
    ImageView mPreviewIv;
    String str = " ";

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;


    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreport);
        mob = findViewById(R.id.mob);
        dat = findViewById(R.id.dat);
        nam = findViewById(R.id.nam);
        par = findViewById(R.id.param);
        medic = findViewById(R.id.medic);
        prevent = findViewById(R.id.prevent);
        diet = findViewById(R.id.diet);
        rp_lay = findViewById(R.id.rp_lay);
        rp_bnb = findViewById(R.id.rp_bnb);
        rp_rng = findViewById(R.id.rp_rng);
        rp_txt = findViewById(R.id.rp_txt);
        mResultEt = findViewById(R.id.resultEt);
        mPreviewIv = findViewById(R.id.imageiv);


        cameraPermission = new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        Date d = Calendar.getInstance().getTime();
        System.out.println("Current time => " + d);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(d);
        dat.setText(formattedDate);

    }

    public void addreport() {

//converting into strings---------------------------------------------------------------------------
        String nams = nam.getText().toString();
        String dats = dat.getText().toString();
        String mobs = mob.getText().toString();
        String pars = par.getText().toString();
        String medics = medic.getText().toString();
        String prevents = prevent.getText().toString();
        String diets = diet.getText().toString();


        int length = mobs.length();
        if (mobs.isEmpty() || length !=10){
            Toast.makeText(this, "Mobile number invalid", Toast.LENGTH_SHORT).show();
            return;
        }

//send_method---------------------------------------------------------------------------------------
        DocumentReference mRef = FirebaseFirestore.getInstance().document("Report/" + mobs+"/dates/"+ dats);
        Map<String, Object> dataToSave = new HashMap<>();
        dataToSave.put("Mobile", mobs);
        dataToSave.put("Date", dats);
        dataToSave.put("Name", nams);
        dataToSave.put("Parameter", pars);
        dataToSave.put("Medic", medics);
        dataToSave.put("Prevent", prevents);
        dataToSave.put("Diet Details", diets);
        rp_lay.setVisibility(View.GONE);
        rp_rng.setVisibility(View.VISIBLE);

        mRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                rp_rng.setVisibility(View.GONE);
               rp_bnb.setVisibility(View.VISIBLE);
               rp_txt.setVisibility(View.VISIBLE);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Add_Report.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void hi(View view) {
        addreport();
    }


    public void scan(View view) {
       // showImageImportDialog();
        pickCamera();
    }

    private void showImageImportDialog() {

        String[] items = {" Camera"," Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0)
                {
                    if (!checkCameraPermission())
                    {

                        requestCameraPermission();
                    }
                    else {
                        pickCamera();
                    }

                }
                if (which==1)
                {
                    if (!checkStoragePermission())
                    {

                        requestStoragePermission();
                    }
                    else {
                        pickGallery();
                    }

                }
            }
        });
        dialog.create().show();
    }

    private void pickGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);

    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image To text");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntant = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntant.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntant,IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);


    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);


        return result;
    }

    private void requestCameraPermission() {

        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length >0)
                {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && writeStorageAccepted)
                    {
                        pickCamera();
                    }
                    else
                    {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case STORAGE_REQUEST_CODE:

                if (grantResults.length >0)
                {


                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if ( writeStorageAccepted)
                    {
                        pickGallery();
                    }
                    else
                    {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }

                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
        {

            if (requestCode == IMAGE_PICK_GALLERY_CODE )
            {
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE)
            {
                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON).start(this);


            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                Uri resultUri  = result.getUri();
                mPreviewIv.setImageURI(resultUri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable)mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational())
                {

                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                else {

                    Frame frame = new  Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();

                    for (int i=0;i<items.size(); i++)
                    {

                        str= str + items.valueAt(i);
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");

                    }
                    str.replace("Second","name");
                    mResultEt.setText(sb.toString());
                    formatstr();

                }
            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {

                Exception error  = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void formatstr() {
        String s= mResultEt.getText().toString();
        String mn,cn,cp,mc,pr,dd;
        int star,at,hash,dollar,exclaim,percent;
        int stare=0,ate=0,hashe=0,dollare=0,exclaime=0,percente=0;
       int[] splchr = new int[7];

        s = s.replace(" ","");
        s = s.replace("\n","");
        s = s.replace(":","");
        s  =s.toLowerCase();
        s = s.replace("mobilenumber","*");
        s = s.replace("checkupname","@");
        s = s.replace("checkupparam","#");
        s = s.replace("medication","$");
        s = s.replace("prevention","!");
        s = s.replace("dietdetails","%");

        star = s.indexOf("*");
        at = s.indexOf("@");
        hash = s.indexOf("#");
        dollar = s.indexOf("$");
        exclaim = s.indexOf("!");
        percent = s.indexOf("%");

        splchr[0] = star;
        splchr[1] = at;
        splchr[2] = hash;
        splchr[3] = dollar;
        splchr[4] = exclaim;
        splchr[5] = percent;
        splchr[6] = s.length();

        int i,j,k,temp;
        for(i=0; i<6; i++){
        for(j=0; j<6;j++){
        if (splchr[j]>splchr[j+1]){
        temp=splchr[j];
            splchr[j]=splchr[j+1];
        splchr[j+1]=temp; }}}

        for (k=0;k<6;k++) {
            if (splchr[k] == star) {
                stare = splchr[k+1];
            }
            if (splchr[k] == at) {
                ate = splchr[k+1];
            }
            if (splchr[k] == hash) {
                hashe = splchr[k+1];
            }
            if (splchr[k] == dollar) {
                dollare = splchr[k+1];
            }
            if (splchr[k] == exclaim) {
                exclaime = splchr[k+1];
            }
            if (splchr[k] == percent) {
                percente = splchr[k+1];
            }
        }
//        mn = s.substring(star+1,at);
//        cn = s.substring(at+1,hash);
//        cp = s.substring(hash+1,dollar);
//        mc = s.substring(dollar+1,exclaim);
//        pr = s.substring(exclaim+1,percent);
//        dd = s.substring(percent+1);


        mn = s.substring(star+1,stare);
        cn = s.substring(at+1,ate);
        cp = s.substring(hash+1,hashe);
        mc = s.substring(dollar+1,dollare);
        pr = s.substring(exclaim+1,exclaime);
        dd = s.substring(percent+1,percente);

        mob.setText(mn);
        nam.setText(cn);
        par.setText(cp);
        medic.setText(mc);
        prevent.setText(pr);
        diet.setText(dd);
        mResultEt.setVisibility(View.GONE);
    }
}
