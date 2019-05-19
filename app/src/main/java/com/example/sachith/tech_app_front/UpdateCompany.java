/**
 *
 * Created by Sachith Tharaka
 *
 **/
package com.example.sachith.tech_app_front;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sachith.tech_app_front.domain.Company;
import com.example.sachith.tech_app_front.enums.EndPoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//class to update and delete company
public class UpdateCompany extends Fragment implements CompanyAdapter.OnItemClickListener{

    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Company> companyList;
    private CompanyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.view_company, container, false);

        mList = view.findViewById(R.id.company_list);

        companyList = new ArrayList<>();
        adapter = new CompanyAdapter(R.layout.single_company_edit, getContext(), companyList);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData();

        return view;
    }


    // method help to get data from the backend
    public void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                EndPoints.GET_ALL_COMPANY.getUrl(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("responseObject");


                            for(int i=0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                if(object.getString("status").toString().equalsIgnoreCase("ACTIVE")) {
                                    Company company = new Company();

                                    company.setId(object.getInt("id"));
                                    company.setName(object.getString("name"));
                                    company.setAddress(object.getString("address"));
                                    company.setContactNum(object.getString("contactNumber"));
                                    company.setWeb(object.getString("website"));
                                    company.setDescription(object.getString("description"));

                                    companyList.add(company);
                                }

                                adapter = new CompanyAdapter(R.layout.single_company_edit, getContext(), companyList);
                                mList.setAdapter(adapter);
                                adapter.setOnItemClickListener(UpdateCompany.this);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastMessage("error");
                        progressDialog.dismiss();
                        System.out.println("ERRORRR : " + error);
                    }
                }

        );

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);


    }

    //this method is to display toast messages
    private void toastMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int posistion) {

        Company clickedCompany = companyList.get(posistion);

        EditCompanyDialog companyDialog = new EditCompanyDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("id" , clickedCompany.getId());
        bundle.putString("name" , clickedCompany.getName());
        bundle.putString("address" , clickedCompany.getAddress());
        bundle.putString("contactNum",clickedCompany.getContactNum());
        bundle.putString("web" , clickedCompany.getWeb());
        bundle.putString("description" , clickedCompany.getDescription());
        companyDialog.setArguments(bundle);
        companyDialog.show(getFragmentManager(),"My dialog");

    }
}

