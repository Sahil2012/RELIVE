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
    String apiUrl = "https://api.rootnet.in/covid19-in/stats/latest";
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
                    JSONObject jo = response.getJSONObject("data");
                    JSONArray tot = jo.getJSONArray("unofficial-summary");
                    JSONArray stateWise = jo.getJSONArray("regional");

                    JSONObject j = tot.getJSONObject(0);
                    cT = j.getString("active");
                    rT = j.getString("recovered");
                    dT = j.getString("deaths");
                    setTextForTotal();

                    for(int i = 0; i < stateWise.length(); i++){

                        JSONObject jsonObject = stateWise.getJSONObject(i);

                        String stateName = jsonObject.getString("loc");
                        String confirm = jsonObject.getString("totalConfirmed");
                        String recover = jsonObject.getString("discharged");
                        String dead = jsonObject.getString("deaths");

                        Map<String,String> mp = new HashMap<>();
                        mp.put("deaths",dead);
                        mp.put("recovered",recover);
                        mp.put("confirmed",confirm);
                        mp.put("state",stateName);

                        arr.add(mp);
                    }

                    stateAdapter.notifyDataSetChanged();
                } catch (JSONException e){
                    Toast.makeText(getContext(),""+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),""+error.toString(),Toast.LENGTH_SHORT).show();
                error.printStackTrace();
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