package ketank.gateregistrations;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONArray;
import org.json.JSONObject;


public class EcidFragment extends Fragment {
    View view;
    EditText editText;
    Button button,register;
    TextView name,contact,college,ecid,notfound;
    LinearLayout showly;
    SharedPreferences preferences;
    String url = "http://eclectika.org/api/news.php/GetUserByEcId?ec_id=" ;

    StudentModel student;
    DatabaseReference dbRef;

    public EcidFragment() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ecid, container, false) ;

        dbRef = FirebaseDatabase.getInstance().getReference("Students");

        name= view.findViewById(R.id.Name);
        ecid= view.findViewById(R.id.ec_id);
        college= view.findViewById(R.id.College);
        contact= view.findViewById(R.id.contact);
        showly=view.findViewById(R.id.showly);
        notfound=view.findViewById(R.id.notfound);

        showly.setVisibility(View.GONE);
        notfound.setVisibility(View.GONE);


        editText= view.findViewById(R.id.ecid);
        button= view.findViewById(R.id.details);
        register= view.findViewById(R.id.register);

        preferences = getActivity().getSharedPreferences("mypref",Context.MODE_PRIVATE);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showly.setVisibility(View.GONE);
               notfound.setVisibility(View.GONE);

               getUserDetails(url+editText.getText().toString());


           }
       });



       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                dbRef.child(student.getEc_id()).setValue(student);

               Toast.makeText(getActivity(),"Student added",Toast.LENGTH_LONG).show();


           }
       });




        return view ;

    }


    void getUserDetails(String url){
        final ProgressDialog progressDialog= new ProgressDialog(getActivity());
        progressDialog.setTitle("Getting Data");
        progressDialog.show();

         StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jObj = new JSONArray(response);
                     progressDialog.dismiss();
                    if(jObj.length()>0) {
                        JSONObject item = jObj.getJSONObject(0);

                        name.setText(item.getString("Name"));
                        college.setText(item.getString("College"));
                            /*Branch.setText(item.getString("Branch"));
                            Semester.setText(item.getString("Semester"));*/
                        contact.setText(item.getString("Phone"));
                        ecid.setText(item.getString("EC_Id"));
                        showly.setVisibility(View.VISIBLE);

                         student = new StudentModel();
                        student.setName(item.getString("Name"));
                        student.setCollege(item.getString("College"));
                        student.setBranch(item.getString("Branch"));
                        student.setSemester(item.getString("Semester"));
                        student.setEmail(item.getString("email"));
                        student.setEc_id(item.getString("EC_Id"));
                        student.setContact(item.getString("Phone"));
                        student.setAdminId(preferences.getString("userid","123"));

                    }else
                         notfound.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    // JSON error
                    progressDialog.dismiss();

                    Log.d("Error", e.toString());



                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Error", error.toString());


            }
        });


        AppController.getInstance().addToRequestQueue(strReq, "");

    }

}
