package ketank.gateregistrations;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterActivity extends AppCompatActivity {
    EditText name, phone,college;
    Button signup;
    SharedPreferences preferences;
    DatabaseReference dbRef;
    TextView ectext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        college = (EditText) findViewById(R.id.college);
        ectext = (TextView) findViewById(R.id.ectext);

        signup = (Button) findViewById(R.id.register);

        dbRef = FirebaseDatabase.getInstance().getReference("Students");

        preferences=getSharedPreferences("mypref",Context.MODE_PRIVATE);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String nam= name.getText().toString();
                final String mobile= phone.getText().toString();
                final String col= college.getText().toString();

                final String ec_id= "EC_"+nam.substring(0,3).toUpperCase()+mobile.substring(mobile.length()-3);

                ectext.setText(ec_id);
                ectext.setVisibility(View.VISIBLE);

                StudentModel student = new StudentModel() ;
                student.setName(nam);
                student.setCollege(col);
                student.setEc_id(ec_id);
                student.setContact(mobile);
                student.setAdminId(preferences.getString("userid","123"));

                dbRef.child(student.getEc_id()).setValue(student);

                Toast.makeText(getApplicationContext(),"Student added",Toast.LENGTH_LONG).show();



              //  SignUp(All_urls.values.UserRegistration,imei,nam,email1,col,bch,semester,ec_id);


            }
        });
    }


/*
    void SignUp(String url,final String imei, final String name, final String phone, final String college, final String Branch,final String semester,final String ecid){


        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Registering ....");
        progressDialog.show();

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_name",  name);
        params.put("phone",phone);
        params.put("college",college);
        params.put("branch",Branch);
        params.put("semester",semester);
        params.put("user_id",imei);
        params.put("ec_id",ecid);


        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject json) {

                        try {


                            if(json.getString("error").equalsIgnoreCase("false")) {

                                progressDialog.dismiss();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("Registered", true);
                                editor.apply();
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", "ok");
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();

                                Toasty.success(getApplicationContext(),"Sign Up Successful", Toast.LENGTH_LONG).show();

                            }else {
                                Toasty.error(getApplicationContext(),"Try again",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            Log.e("error",e.toString());
                            Toasty.error(getApplicationContext(),"Try again",Toast.LENGTH_LONG).show();

                        }




                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }
*/


}
