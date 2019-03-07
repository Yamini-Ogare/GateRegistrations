package ketank.gateregistrations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    ArrayList<StudentModel> students;
    Context context;


    public ListAdapter(ArrayList<StudentModel> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(students.get(position).getName());
        holder.college.setText(students.get(position).getCollege());
        holder.ec_id.setText(students.get(position).getEc_id());
        holder.phone.setText(students.get(position).getContact());


    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, college, ec_id, phone;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            college = itemView.findViewById(R.id.college);
            ec_id = itemView.findViewById(R.id.ec);
            phone = itemView.findViewById(R.id.phone);
        }
    }



}
