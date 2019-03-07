package ketank.gateregistrations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class AdminListActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    ListAdapter adapter;
    ArrayList<StudentModel> students = new ArrayList<>();
    DatabaseReference ref;
    TextView count,usercount;
    MaterialSpinner spinner;
    int pos=0;
    String[] arr={"","A22GATE","U23GATE","K24GATE","A25GATE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        ref = FirebaseDatabase.getInstance().getReference("Students");
        spinner = (MaterialSpinner) findViewById(R.id.user);

        spinner.setItems("All", "Anoop","Utkarsh","Kriti", "Abhilash");


        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if (position == 0) {
                    pos=0;
                    onStart();

                } else if (position == 1) {
                    pos=1;
                    onStart();
                }else   if (position == 2) {
                    pos=2;
                    onStart();
                } else if (position == 3) {
                    pos=3;
                    onStart();

                } else if (position == 4) {
                    pos=4;
                    onStart();

                }
            }
        });

        recyclerView= findViewById(R.id.list) ;
        count = findViewById(R.id.count);
        usercount=findViewById(R.id.usercount);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new ListAdapter(students,this);

        recyclerView.setAdapter(adapter);

        adapter = new ListAdapter(students,AdminListActivity.this);
        recyclerView.setAdapter(adapter);
    }

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


                    if(pos==0) {


                        students.add(student);
                        userreg++;
                    }else if(student.getAdminId().equalsIgnoreCase(arr[pos])) {

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
}
