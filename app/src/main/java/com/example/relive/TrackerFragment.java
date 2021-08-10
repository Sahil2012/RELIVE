package com.example.relive;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TrackerFragment extends Fragment {



    private Context context;
    public TrackerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private ArrayList<Map<String,String>> arr;
    private RecyclerView recyclerView;
    private StateAdapter stateAdapter;
    String apiUrl = "https://api.covid19india.org/data.json";
    private TextView rTotal,cTotal,dTotal;
    String rT = "0",cT = "0",dT = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tracker, container, false);

        rTotal = v.findViewById(R.id.rTotal);
        cTotal = v.findViewById(R.id.cTotal);
        dTotal = v.findViewById(R.id.dTotal);
        recyclerView = v.findViewById(R.id.stateRec);

        arr = new ArrayList<>();

        loadData();

        rTotal.setText(rT);
        cTotal.setText(cT);
        dTotal.setText(dT);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        stateAdapter = new StateAdapter(arr);

        recyclerView.setAdapter(stateAdapter);

        return v;
    }

    private void loadData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray stateWise = response.getJSONArray("statewise");

                    JSONObject j = stateWise.getJSONObject(0);
                    cT = j.getString("confirmed");
                    rT = j.getString("recovered");
                    dT = j.getString("deaths");
                    setTextForTotal();
                    for(int i = 1; i < stateWise.length(); i++){

                        JSONObject jsonObject = stateWise.getJSONObject(i);

                        String stateName = jsonObject.getString("state");
                        String confirm = jsonObject.getString("confirmed");
                        String recover = jsonObject.getString("recovered");
                        String dead = jsonObject.getString("deaths");
                        String last = jsonObject.getString("lastupdatedtime");

                        Map<String,String> mp = new HashMap<>();
                        mp.put("lastupdatedtime",last);
                        mp.put("deaths",dead);
                        mp.put("recovered",recover);
                        mp.put("confirmed",confirm);
                        mp.put("state",stateName);

                        arr.add(mp);
                    }

                    stateAdapter.notifyDataSetChanged();
                } catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void setTextForTotal() {

        rTotal.setText(rT);
        cTotal.setText(cT);
        dTotal.setText(dT);
    }
}