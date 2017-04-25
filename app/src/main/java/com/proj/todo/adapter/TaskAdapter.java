package com.proj.todo.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.proj.todo.R;
import com.proj.todo.domain.TaskModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * RecyclerView adapter for the tasks.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<TaskModel> taskModels;


    public void sortDscData() {
        Collections.sort(taskModels, (o1, o2) -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            int result = 0;
            try {
                Date date1 = simpleDateFormat.parse(o1.getDate_created());
                Date date2 = simpleDateFormat.parse(o2.getDate_created());
                result = date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return result;
        });
        notifyDataSetChanged();
    }

    public void sortAscData() {
        Collections.sort(taskModels, (o1, o2) -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            int result = 0;
            try {
                Date date1 = simpleDateFormat.parse(o1.getDate_created());
                Date date2 = simpleDateFormat.parse(o2.getDate_created());
                result = date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return result;
        });
        notifyDataSetChanged();
    }

    public interface MyAdapterListener {

        void buttonOnClick(View v, int position);
    }

    private MyAdapterListener onClickListener;

    public TaskAdapter(ArrayList<TaskModel> taskModels, MyAdapterListener onClickListener) {
        this.taskModels = taskModels;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskModel taskModel = taskModels.get(position);
        holder.taskDescription.setText(taskModel.getTask());
        holder.date.setText(taskModel.getDate_created());
        if (taskModel.isDone()) {
            holder.root.setBackgroundColor(ContextCompat.getColor(holder.date.getContext(), R
                    .color.colorDone));
        } else {
            holder.root.setBackgroundColor(ContextCompat.getColor(holder.date.getContext(), R
                    .color.colorNotDone));
        }

    }

    @Override
    public int getItemCount() {
        if (taskModels != null) {
            return taskModels.size();
        }
        return 0;
    }

    public void updateTask(TaskModel taskModel, int position) {
        taskModels.remove(position);
        taskModels.add(position, taskModel);
        notifyItemChanged(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView taskDescription, date;
        ImageButton delete, done;
        RelativeLayout root;

        ViewHolder(View itemView) {
            super(itemView);
            root = (RelativeLayout) itemView.findViewById(R.id.root_layout);
            taskDescription = (TextView) itemView.findViewById(R.id.task_description);
            date = (TextView) itemView.findViewById(R.id.date);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
            done = (ImageButton) itemView.findViewById(R.id.done);
            delete.setOnClickListener(this);
            done.setOnClickListener(this);
            root.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.buttonOnClick(v, getAdapterPosition());
        }
    }

    public TaskModel getItemAt(int index) {
        if (taskModels != null) {
            return taskModels.get(index);
        }
        return null;
    }

    public void removeItemAt(int index) {
        if (taskModels != null) {
            taskModels.remove(index);
            notifyItemRemoved(index);
        }
    }
}
