package ketank.gateregistrations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText user, pass ;
    DatabaseReference dbRef;
    SharedPreferences preferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("mypref",Context.MODE_PRIVATE);


        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        user = findViewById(R.id.userid);
        pass = findViewById(R.id.pass);

        if(preferences.getString("userid",null)!=null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }








    }


    public void login(View view) {

     final String   suser = user.getText().toString();
     final String   spass = pass.getText().toString();




        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            final int[] count = {0};
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){

                    User user = data.getValue(User.class);

                    if(user.getUserId().equalsIgnoreCase(suser)&& user.getPass().equalsIgnoreCase(spass))
                    {
                        count[0]++;
                        SharedPreferences.Editor editor= preferences.edit() ;
                        editor.putString("userid" , user.getUserId());
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);


                        finish();

                        break;
                    }





                }
                if(count[0]==0)
                Toast.makeText(getApplicationContext(),"Invalid userid / pass",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
