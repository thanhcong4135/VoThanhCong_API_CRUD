package com.example.api_crud_vothanhcong;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListUser extends AppCompatActivity implements  ISendUser{

    ArrayList<User> users=new ArrayList<>();
    RecyclerView rcvUser;
    UserAdapter userAdapter;

    TextView tvID, tvName, tvDiachi;
    EditText edtName, edtDiaChi;
    Button btnThem, btnSua, btnXoa, btnGetData;

    String url = "https://60c04b02b8d3670017554af4.mockapi.io/user";
    User userChon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_user);

        init();
        GetArrayJson(url,users);
        userAdapter = new UserAdapter(this, users);
        rcvUser.setAdapter(userAdapter);
        rcvUser.setLayoutManager(new LinearLayoutManager(this));

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostAPI(url);
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutAPI(url);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(url);
            }
        });
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetArrayJson(url,users);
            }
        });
    }
    private void GetArrayJson(String url,ArrayList<User> users) {
        edtName.setText("");
        edtDiaChi.setText("");
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject object = (JSONObject) response.get(i);
                                        String id = object.getString("id").toString();
                                        String name = object.getString("ten").toString();
                                        String diaChi = object.getString("diaChi").toString();
                                        // User user=new User("1","name","diaChi");
                                        User user = new User(id, name, diaChi);
                                        users.add(user);
                                        Log.i("VL",user.toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListUser.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        userAdapter = new UserAdapter(this, users);
        rcvUser.setAdapter(userAdapter);
        rcvUser.setLayoutManager(new LinearLayoutManager(this));
    }
    private void PostAPI(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ListUser.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListUser.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("ten",edtName.getText().toString());
                params.put("diaChi",edtDiaChi.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void PutAPI(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "/" + userChon.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ListUser.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListUser.this, "Error by Put data!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("ten",edtName.getText().toString());
                params.put("diaChi",edtDiaChi.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void Delete(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url +"/" + userChon.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ListUser.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListUser.this, "Error by Post data!"+ userChon.getId(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void init() {
        rcvUser = findViewById(R.id.rcvUser);
        tvID = findViewById(R.id.tvID);
        tvName = findViewById(R.id.tvName);
        tvDiachi = findViewById(R.id.tvDiachi);
        btnGetData = findViewById(R.id.btnGetData);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        edtName = findViewById(R.id.edtTen);
        edtDiaChi = findViewById(R.id.edtDiaChi);
    }
    @Override
    public void sendUser(User user) {
        userChon = user;
        edtName.setText(user.getName());
        edtDiaChi.setText(user.getDiaChi());
    }
}