package ketank.gateregistrations;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
 final  int Request_Camera=4;
 SharedPreferences preferences ;


    DatabaseReference dbRef;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                JSONObject item = null;
                try {

                    item = new JSONObject(result);

                    StudentModel student = new StudentModel();
                    student.setName(item.getString("Name"));
                    student.setCollege(item.getString("College"));
                    student.setBranch(item.getString("Branch"));
                    student.setSemester(item.getString("Semester"));
                    student.setEmail(item.getString("email"));
                    student.setEc_id(item.getString("EC_Id"));
                    student.setContact(item.getString("Phone"));
                    student.setAdminId(preferences.getString("userid","123"));



                   dbRef.child(student.getEc_id()).setValue(student);

                    Toast.makeText(getApplicationContext(),"Student added",Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         dbRef = FirebaseDatabase.getInstance().getReference("Students");
        preferences = getSharedPreferences("mypref",Context.MODE_PRIVATE);



    }


    public void scanToRegister(View view) {
      scan();

    }

    public void scan(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Request_Camera);
        }
        else {
            Intent i = new Intent(this, SimpleScannerActivity.class);
            startActivityForResult(i, 1);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==Request_Camera){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scan();
            }
        }

    }

    public void studentlist(View view) {
        Intent intent = new Intent(MainActivity.this,ListActivity.class);
        startActivity(intent);
    }

    public void ec_id(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment events = new EcidFragment();
        fragmentTransaction.replace(R.id.frg, events);
        fragmentTransaction.addToBackStack("Ec_id");
        fragmentTransaction.commit();

    }

    public void form(View view) {
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);

    }

    public void LogOut(View view) {
        SharedPreferences.Editor editor= preferences.edit() ;
        editor.putString("userid" ,null);
        editor.apply();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
