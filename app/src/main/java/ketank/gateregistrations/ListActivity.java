package ketank.gateregistrations;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
   RecyclerView recyclerView ;
   ListAdapter adapter;
   ArrayList<StudentModel> students = new ArrayList<>();
   DatabaseReference ref;
   TextView count,usercount;
   SharedPreferences preferences;



    @Override
    protected void onStart() {
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            int userreg=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count.setText("Registration Count :"+dataSnapshot.getChildrenCount());

                students.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting students
                   StudentModel student= postSnapshot.getValue(StudentModel.class);


                   if(student.getAdminId().equalsIgnoreCase(preferences.getString("userid","123"))) {
                       students.add(student);
                       userreg++;
                   }

                }

                usercount.setText("Reg by User : "+userreg);
                  adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ref = FirebaseDatabase.getInstance().getReference("Students");
        preferences= getSharedPreferences("mypref",Context.MODE_PRIVATE);


        recyclerView= findViewById(R.id.list) ;
        count = findViewById(R.id.count);
        usercount=findViewById(R.id.usercount);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new ListAdapter(students,this);

        recyclerView.setAdapter(adapter);

        adapter = new ListAdapter(students,ListActivity.this);
        recyclerView.setAdapter(adapter);
    }


}
