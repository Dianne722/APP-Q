package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GradeAdapter extends BaseAdapter {
    private List<Grade> gradeDatas;

    private Context gradeContext;

    public GradeAdapter(Context gradeContext, List<Grade> gradeDatas) {
        this.gradeContext = gradeContext;
        this.gradeDatas = gradeDatas;
    }

    @Override
    public int getCount() {
        return gradeDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return gradeDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"CutPasteId"})
    @Override
    public View getView(int i, View gradeView, ViewGroup viewGroup) {
        GradeAdapter.ViewHolder viewHolder;

        if (gradeView == null) {
            gradeView = View.inflate(gradeContext, R.layout.grade_layout, null);

            viewHolder = new GradeAdapter.ViewHolder();
            viewHolder.text1 = gradeView.findViewById(R.id.name);
            viewHolder.text2 = gradeView.findViewById(R.id.courseName);

            gradeView.setTag(viewHolder);
        } else {
            viewHolder = (GradeAdapter.ViewHolder) gradeView.getTag();
        }

        TextView tv_name = gradeView.findViewById(R.id.name);
        TextView tv_courseName = gradeView.findViewById(R.id.courseName);
        TextView tv_score = gradeView.findViewById(R.id.score);

        Grade s = (Grade) getItem(i);
        tv_name.setText(s.getStudent().getName());
        tv_courseName.setText(s.getCourse().getCourseName());
        tv_score.setText(Integer.toString(s.getScore()));

        return gradeView;
    }

    static class ViewHolder {
        TextView text1;
        TextView text2;
    }
}
