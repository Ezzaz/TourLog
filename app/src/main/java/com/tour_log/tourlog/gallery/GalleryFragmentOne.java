package com.tour_log.tourlog.gallery;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tour_log.tourlog.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import android.webkit.MimeTypeMap;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.io.FileNotFoundException;

import java.io.IOException;


import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragmentOne extends Fragment {

    private StorageReference storageRef;
    private DatabaseReference rootReference;

    private ImageView imageView;
    private EditText editText;
    private Uri imguri;

    private static final String FB_STORAGE_PATH="image/";
    static final String FB_DATABASE_PATH="image";
    private static final int REQUEST_CODE=1234;

    private FirebaseUser user;

    public GalleryFragmentOne () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gallery_fragment_one, container, false);


        storageRef = FirebaseStorage.getInstance().getReference();
        rootReference = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        imageView=(ImageView)view.findViewById ( R.id.image_view );
        editText=(EditText)view.findViewById ( R.id.edit_text );
        user = FirebaseAuth.getInstance().getCurrentUser();

        return  view;
    }

    public void btntakephoto_click(View view){
        Intent intent=new Intent (  );
        intent.setType ( "image/*" );
        intent.setAction ( Intent.ACTION_GET_CONTENT );
        startActivityForResult ( Intent.createChooser ( intent,"Select your image" ),REQUEST_CODE );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.getData ()!=null){
            imguri=data.getData ();

            try {
                Bitmap bm=MediaStore.Images.Media.getBitmap (getApplicationContext ().getContentResolver (),imguri);
                imageView.setImageBitmap (bm);
            }catch (FileNotFoundException e) {
                e.printStackTrace ( );

            } catch (IOException e) {
                e.printStackTrace ( );
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver=getApplicationContext ().getContentResolver ();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton ();
        return mimeTypeMap.getExtensionFromMimeType ( contentResolver.getType ( uri ) );
    }

    @SuppressWarnings ( "VisibleForTests" )
    public void btnuploadimage_click(View view){
        if (imguri!=null){
            final ProgressDialog dialog=new ProgressDialog ( getApplicationContext () );
            dialog.setTitle ( "Uploading image..." );
            dialog.show ();

            StorageReference ref=storageRef.child ( FB_STORAGE_PATH + System.currentTimeMillis () + "." +getImageExt ( imguri ));

            ref.putFile ( imguri ).addOnSuccessListener ( new OnSuccessListener <UploadTask.TaskSnapshot> ( ) {
                @Override
                public void onSuccess ( UploadTask.TaskSnapshot taskSnapshot ) {
                    dialog.dismiss ();
                    Toast.makeText ( getApplicationContext (),"image uploaded Successfully...",Toast.LENGTH_SHORT ).show ();

                    GalleryImg imageUpload=new GalleryImg ( editText.getText().toString(),taskSnapshot.getDownloadUrl ().toString () );

                    String uploadId=rootReference.push ().getKey ();
                    rootReference.child ( uploadId ).setValue ( imageUpload );

                }
            } ).addOnFailureListener ( new OnFailureListener ( ) {
                @Override
                public void onFailure ( @NonNull Exception e ) {
                    dialog.dismiss ();
                    Toast.makeText ( getApplicationContext (),e.getMessage (),Toast.LENGTH_SHORT ).show ();
                }
            } ).addOnProgressListener ( new OnProgressListener <UploadTask.TaskSnapshot> ( ) {

                @Override
                public void onProgress ( UploadTask.TaskSnapshot taskSnapshot ) {
                    double progress=(100 * taskSnapshot.getBytesTransferred ()) / taskSnapshot.getTotalByteCount ();

                    dialog.setMessage ( "Uploaded..." + (int) progress + "" );
                }
            } );
        }
        else {
            Toast.makeText ( getApplicationContext (),"Please select your image...",Toast.LENGTH_SHORT ).show ();
        }
    }

    public void btnlistimage_click(View view){
        Intent intent=new Intent (getApplicationContext (),GalleryFragmentTwo.class);
        startActivity (intent);
    }

}
