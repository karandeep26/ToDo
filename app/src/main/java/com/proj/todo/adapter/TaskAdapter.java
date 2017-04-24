package com.proj.todo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.proj.todo.R;
import com.proj.todo.domain.TaskModel;

import java.util.ArrayList;

/**
 * Created by stpl on 4/24/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<TaskModel> taskModels;
    public interface MyAdapterListener {

        void buttonOnClick(View v, int position);
    }
    private MyAdapterListener onClickListener;

    public TaskAdapter(ArrayList<TaskModel> taskModels,MyAdapterListener onClickListener) {
        this.taskModels = taskModels;
        this.onClickListener=onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskModel taskModel=taskModels.get(position);
        holder.taskDescription.setText(taskModel.getTask());
        holder.date.setText(taskModel.getDate_created());

    }

    @Override
    public int getItemCount() {
        if(taskModels!=null){
            return taskModels.size();
        }
        return 0;
    }
    public void updateTask(TaskModel taskModel,int position){
        taskModels.remove(position);
        taskModels.add(position,taskModel);
        notifyItemChanged(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView taskDescription,date;
        ImageButton delete,done;
        RelativeLayout root;
        ViewHolder(View itemView) {
            super(itemView);
            root= (RelativeLayout) itemView.findViewById(R.id.root_layout);
            taskDescription= (TextView) itemView.findViewById(R.id.task_description);
            date= (TextView) itemView.findViewById(R.id.date);
            delete= (ImageButton) itemView.findViewById(R.id.delete);
            done= (ImageButton) itemView.findViewById(R.id.done);
            delete.setOnClickListener(this);
            done.setOnClickListener(this);
            root.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.buttonOnClick(v,getAdapterPosition());
        }
    }
    public TaskModel getItemAt(int index){
        if(taskModels!=null){
            return taskModels.get(index);
        }
        return null;
    }
    public void removeItemAt(int index){
        if(taskModels!=null){
            taskModels.remove(index);
            notifyItemRemoved(index);
        }
    }
}
