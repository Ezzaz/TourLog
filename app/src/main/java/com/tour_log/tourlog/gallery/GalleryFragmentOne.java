package com.tour_log.tourlog.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tour_log.tourlog.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragmentOne extends Fragment {

    private ImageView momentIV;
    private Button takephotoBtn;
    private Button closephotoBtn;

    private int flag =0;
    private Bitmap bitmap;

    private StorageReference storageRef;

    private DatabaseReference rootReference;

    private FirebaseUser user;

    public GalleryFragmentOne () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gallery_fragment_one, container, false);
        momentIV =(ImageView) view.findViewById(R.id.momentImg);
        takephotoBtn =(Button) view.findViewById(R.id.momentTake);
        closephotoBtn =(Button) view.findViewById(R.id.momentClose);


        storageRef = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();


        closephotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap= null;
                flag = 0;
                closephotoBtn.setVisibility(View.GONE);
                momentIV.setImageResource(R.drawable.dummyphoto);
            }
        });


        takephotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(flag == 0){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,99);
                }
                else if ( flag == 1){

                    savePhoto(bitmap);
                    flag = 0;
                    takephotoBtn.setText("Take Photo");
                }
            }
        });
        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 99 && resultCode == RESULT_OK && data != null){

            bitmap = (Bitmap) data.getExtras().get("data");
            momentIV.setImageBitmap(bitmap);
            flag = 1;
            takephotoBtn.setText("Save ");
            closephotoBtn.setVisibility(View.VISIBLE);

        }
    }

    private void savePhoto(Bitmap bitmap){
        closephotoBtn.setVisibility(View.GONE);
        momentIV.setImageResource(R.drawable.dummyphoto);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        final String photoName = sdf.format(new Date());

        String root = Environment.getExternalStorageDirectory().toString();
        File folder = new File(root+"/Tour_Event");
        folder.mkdirs();

        File myFile = new File(folder,photoName+".png");


        try{
            FileOutputStream stream = new FileOutputStream(myFile);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri file = Uri.fromFile(new File(root+"/Tour_Event/"+photoName+".png"));
        StorageReference imgRef = storageRef.child(user.getUid()+"/"+photoName+".png");

        imgRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                rootReference = FirebaseDatabase.getInstance().getReference("Images");
                String imgId = rootReference.push().getKey();
                rootReference.child(user.getUid()).child(imgId).setValue(new GalleryImg(photoName));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("pic..........", "onProgress: "+(100 *taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount() );
            }
        });


    }

}
