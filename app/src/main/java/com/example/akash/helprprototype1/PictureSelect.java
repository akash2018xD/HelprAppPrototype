package com.example.akash.helprprototype1;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PictureSelect extends Fragment {

    private ImageView mPictureSelect;
    private Button picture_next;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private static final int CAMERA_REQUEST_CODE=1;

    public PictureSelect() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_picture_select, container, false);


        //returns root to the firebase storage folder
        mPictureSelect=rootView.findViewById(R.id.add_image);
        mStorage=FirebaseStorage.getInstance().getReference();
        mPictureSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        });
        picture_next=rootView.findViewById(R.id.picture_select_next);
        picture_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Toast.makeText(getContext(),"This button works fine",Toast.LENGTH_SHORT).show();
              FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
              FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
              ExtraDetails ext=new ExtraDetails();
              fragmentTransaction.replace(R.id.HelpFrame,ext);
              fragmentTransaction.commit();
                //  FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
               // FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
               // ExtraDetails extraDetails=new ExtraDetails();
               // fragmentTransaction.replace(R.id.activity_helpr_home,extraDetails);
               // fragmentTransaction.commit();

            }
        });
    return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getContext(),"Uplodaing Finished",Toast.LENGTH_SHORT).show();
        if(requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK){
            Uri uri =data.getData();
            StorageReference filepath=mStorage.child("Photos");
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"Uplodaing Finished",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Uplodaing Failed, Try Again.",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getContext(),"No",Toast.LENGTH_SHORT).show();
        }
    }
}
