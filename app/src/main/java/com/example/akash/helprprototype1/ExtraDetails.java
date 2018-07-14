package com.example.akash.helprprototype1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExtraDetails extends Fragment {
    CheckBox yes_box,no_box;
    String str;
    Button submit_data;
    public ExtraDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_extra_details, container, false);

        yes_box=rootView.findViewById(R.id.checkBoxYes);
        no_box=rootView.findViewById(R.id.checkBoxNo);
        submit_data=rootView.findViewById(R.id.submit_data);
        yes_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(no_box.isChecked())no_box.toggle();
                str=yes_box.getText().toString();
            }
        });
        no_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(yes_box.isChecked())yes_box.toggle();;
                str=no_box.getText().toString();
            }
        });
        submit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Thanks, Your data has been sent to all the nearby NGOs and Vets :)",Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AnimalSelect animalSelect = new AnimalSelect();
                fragmentTransaction.replace(R.id.HelpFrame, animalSelect);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

}
