package com.example.akash.helprprototype1;


import android.app.Dialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimalSelect extends Fragment {
    CheckBox checkdog,checkcat,checkcattle,checkbird;
    private static final String TAG = "AnimalSelect";
    String str;
    Button  animal_select_next;
    private static final int ERROR_DIALOG_REQUEST=9001;
    public AnimalSelect() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView=inflater.inflate(R.layout.fragment_animal_select, container, false);
        checkdog=(CheckBox)rootView.findViewById(R.id.checkBoxDog);
        checkcat=(CheckBox)rootView.findViewById(R.id.checkBoxCat);
        checkcattle=(CheckBox)rootView.findViewById(R.id.checkBoxCattle);
        checkbird=(CheckBox)rootView.findViewById(R.id.checkBoxBird);
        //setting on click listener to all the checkbox
        checkdog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkcat.isChecked())checkcat.toggle();
                if(checkcattle.isChecked())checkcattle.toggle();
                if(checkbird.isChecked())checkbird.toggle();
                str=checkdog.getText().toString();
            }
        });
        checkcat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkdog.isChecked())checkdog.toggle();
                if(checkcattle.isChecked())checkcattle.toggle();
                if(checkbird.isChecked())checkbird.toggle();
                str=checkcat.getText().toString();
            }
        });
        checkcattle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkdog.isChecked())checkdog.toggle();
                if(checkcat.isChecked())checkcat.toggle();
                if(checkbird.isChecked())checkbird.toggle();
                str=checkcattle.getText().toString();
            }
        });
        checkbird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkdog.isChecked())checkdog.toggle();
                if(checkcattle.isChecked())checkcattle.toggle();
                if(checkcat.isChecked())checkcat.toggle();
                str=checkbird.getText().toString();
            }
        });
        rootView.findViewById(R.id.animal_select_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
            if(isServiceOK()) {
                Bundle bundle = new Bundle();
                bundle.putString("animal", str);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LocationSelect locationSelect = new LocationSelect();
                locationSelect.setArguments(bundle);
                fragmentTransaction.replace(R.id.HelpFrame, locationSelect);
                fragmentTransaction.commit();
            }
            }
        });

    return rootView;}
    //check if the play services is correctly setup
        private void init(){

        }
        public boolean isServiceOK(){
            Log.d(TAG,"Checking google service version");
            int available=GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
            if(available==ConnectionResult.SUCCESS){
                Log.d(TAG,"Google play service is working");
                return true;
            }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
                Log.d(TAG,"A fixable error occured");
                Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(getActivity(),available,ERROR_DIALOG_REQUEST);
                dialog.show();
            }else{
                Toast.makeText(getContext(),"We cant't make map request",Toast.LENGTH_SHORT).show();
            }
            return false;
        }

}
