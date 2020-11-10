package com.example.notepad;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.notepad.thum_item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.List;

public class thum_RecyclerViewAdapter extends RecyclerView.Adapter<thum_RecyclerViewAdapter.ViewHolder> {

    private final List<thum_item> mValues;
    private Context context;
    private Notepad notepad;

    public thum_RecyclerViewAdapter(List<thum_item> items,Context context,Notepad notepad) {
        mValues = items;
        this.context = context;
        this.notepad = notepad;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).title);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notepad.create_text_activity(mValues.get(position).filename, position);
            }
        });
    }
    public void remove_item(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mValues.size());
    }

    public void deletefile(String filename) {
        File file = new File(context.getFilesDir(), filename);
        file.delete();
        //System.out.println("delete file "+ filename);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final FloatingActionButton mButton;
        public thum_item mItem;
        private thum_RecyclerViewAdapter adapter;

        public ViewHolder(View view, final thum_RecyclerViewAdapter adapter) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
            mButton = (FloatingActionButton) view.findViewById(R.id.deleteButton);
            this.adapter = adapter;

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //System.out.println(mValues.indexOf(mItem));
                    adapter.remove_item(mValues.indexOf(mItem));
                    String filename = mItem.filename;
                    adapter.deletefile(filename);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
