package com.example.dheerajp.uploadseal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends SIC43N1xService implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    private Button mUploadBtn;
    private Button mStore;
    private ImageView mImageView;
    private StorageReference mStorage;
    private boolean tag_start;
    public static String final_tag_uid;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("product");
    private static final int CAMERA_REQUEST_CODE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private Uri filePath;


    class C02031 implements Runnable {
        final String displaUid;
        C02031(String uid) {
            this.displaUid = uid;
        }

        public void run() {
            //Toast.makeText(StatusActivity.this, this.displaUid, Toast.LENGTH_LONG).show();
//            Log.i("Checking data base23", "Inside Set Tamper Check");
//            TextView productId = (TextView)findViewById(R.id.textView);
//            productId.setText(this.displaUid);
            EditText tagId = (EditText) findViewById(R.id.tagId);
            tagId.setText(this.displaUid);


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    //String value = dataSnapshot.getValue(String.class);
                    showData(dataSnapshot);
                    //database.child("Campaigns").child(key).child("count");
                    //Log.d("test", "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("test", "Failed to read value.", error.toException());
                }
            });
        }

        private void showData(DataSnapshot dataSnapshot){
            Log.i("Checking data base1", "Inside Set Tamper Check");
//            TextView productname = (TextView)findViewById(R.id.textView3);
//            TextView productDescrption = (TextView)findViewById(R.id.description);
//            TextView productManufacture = (TextView)findViewById(R.id.manufacturing_date);
//            TextView productExpire = (TextView)findViewById(R.id.expiry_date);
//            ImageView productImage = (ImageView)findViewById(R.id.imageView2) ;
//
//            productname.setText(dataSnapshot.child("prod1").child("name").getValue().toString());
//            productDescrption.setText(dataSnapshot.child("prod1").child("description").getValue().toString());
//            productManufacture.setText(dataSnapshot.child("prod1").child("manufacturing_date").getValue().toString());
//            productExpire.setText(dataSnapshot.child("prod1").child("expiry_date").getValue().toString());
            //Picasso.with(StatusActivity.this).load("https://firebasestorage.googleapis.com/v0/b/fir-1-a158d.appspot.com/o/minion1.jpg?alt=media&token=dd72e136-1e01-4ed4-afdc-f1b96ddab6dc").into(productImage);
            //Log.d("snapshot Data", dataSnapshot.child("prod1").child("expiry_data").getValue().toString());
        }
    }

    @Override
    protected void onTagDetected() {
        Log.i("service Log", "inside onTagDetect");
        Log.i("tag id check is 1", SIC43N1xService.taguid);
        if (!this.tag_start) {
            Log.i("tag id check is", SIC43N1xService.taguid);
        }
        //TamperCheck();
        final_tag_uid = SIC43N1xService.taguid;
        runOnUiThread(new C02031(SIC43N1xService.taguid));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorage = FirebaseStorage.getInstance().getReference();
        mUploadBtn  = (Button)findViewById(R.id.selectImage);
        mImageView  = (ImageView)findViewById(R.id.imageView);
        mStore = (Button) findViewById(R.id.upload);

        mUploadBtn.setOnClickListener(this);
        mStore.setOnClickListener(this);
    }


    String mCurrentPhotoPath;


    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void UploadFile(){
        EditText imageName = (EditText)findViewById(R.id.tagId);
        String result = imageName.getText().toString();
        //StorageReference riversRef = mStorage.child("images/"+result+".jpg");
        StorageReference riversRef = mStorage.child("images/test8"+result+".jpg");

        riversRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        EditText productName = (EditText)findViewById(R.id.productName);
                        EditText productDescription = (EditText)findViewById(R.id.description);
                        EditText productBatchNo = (EditText)findViewById(R.id.batchNumber);
                        EditText productManDate = (EditText)findViewById(R.id.manDate);
                        EditText productExDate = (EditText)findViewById(R.id.exDate);

                        String resultName = productName.getText().toString();
                        String resultdescription = productDescription.getText().toString();
                        String resultManDate = productManDate.getText().toString();
                        String resultexDate = productExDate.getText().toString();
                        String resultbatchNo = productBatchNo.getText().toString();


                        Product product = new Product(final_tag_uid, resultName, resultManDate, resultexDate, resultbatchNo, resultdescription, taskSnapshot.getDownloadUrl().toString());
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("product").child(final_tag_uid);
                        myRef.setValue(product);
                        //Log.i("snapshot Data Uplaod111", "Testing");
                        //Log.d("snapshot Data Uplaod", String.valueOf(taskSnapshot.getStorage()));
                        //Log.i("snapshot Data Uplaod", taskSnapshot.getDownloadUrl().toString());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == mUploadBtn){
            showFileChooser();
        }else if(v == mStore){
            UploadFile();
        }
    }
}
