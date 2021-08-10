package com.example.relive;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Context context;
    public RequestFragment(Context context) {
        this.context = context;
    }


    EditText pName,pNo,loc,hos,why,quan;
    Spinner blood;
    Button addReq;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_request, container, false);


        pName = v.findViewById(R.id.pName);
        pNo = v.findViewById(R.id.pNnum);
        loc = v.findViewById(R.id.cityName);
        hos = v.findViewById(R.id.hName);
        why = v.findViewById(R.id.why);
        blood = v.findViewById(R.id.blod);
        quan = v.findViewById(R.id.quan);
        addReq = v.findViewById(R.id.addReq);

        ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(context,R.array.blood, android.R.layout.simple_spinner_item);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood.setAdapter(adap);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        addReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patN = pName.getText().toString();
                String pNum = pNo.getText().toString();
                String city = loc.getText().toString();
                String hosp = hos.getText().toString();
                String whyR = why.getText().toString();
                String quanR = quan.getText().toString();
                String bg = blood.getSelectedItem().toString();

                if(patN.length() < 3) {
                    Toast.makeText(context,"Enter Patient Name Valid",Toast.LENGTH_SHORT).show();
                } else if(pNum.length() != 10){
                    Toast.makeText(context,"Enter Valid Number",Toast.LENGTH_SHORT).show();
                } else if(city.length() == 0) {
                    Toast.makeText(context,"Enter City",Toast.LENGTH_SHORT).show();
                } else if(hosp.length() == 0){
                    Toast.makeText(context,"Enter Hospital",Toast.LENGTH_SHORT).show();
                } else {

                    RequestPost requestPost = new RequestPost();

                    requestPost.setUiD(auth.getUid());
                    requestPost.setBloodGroup(bg);
                    requestPost.setCity(city);
                    requestPost.setNeed(whyR);
                    requestPost.setPhoneNo(pNum);
                    requestPost.setPatName(patN);
                    requestPost.setHosp(hosp);
                    requestPost.setQuan(quanR);

                    pName.setText("");
                    pNo.setText("");
                    hos.setText("");
                    loc.setText("");
                    why.setText("");
                    quan.setText("");
                    firebaseFirestore.collection("post").document().set(requestPost);
                }
            }
        });

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}