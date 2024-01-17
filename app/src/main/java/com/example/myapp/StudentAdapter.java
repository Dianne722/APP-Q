package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StudentAdapter extends BaseAdapter implements View.OnClickListener {

    private List<Student> studentDatas;

    private Context studentContext;

    public StudentAdapter( Context studentContext, List<Student> studentDatas) {
        this.studentDatas = studentDatas;
        this.studentContext = studentContext;
    }

    @Override
    public void onClick(View view) {
        final int position = (int) view.getTag();
        final Student student = (Student) studentDatas.get(position);
        if (view.getId() == R.id.edit) {
            Intent intent = new Intent();
            intent.setClass(studentContext, ZCActivity.class);
            intent.putExtra("student", student);
            intent.putExtra("operationType", StudentOperatorType.Edit);
            Toast.makeText(studentContext, student.getName() + ":编辑" + position,Toast.LENGTH_LONG).show();
            studentContext.startActivity(intent);

        } else if (view.getId() == R.id.delete) {
            AlertDialog.Builder myDialog = new AlertDialog.Builder(studentContext);
            myDialog.setTitle("提示");
            myDialog.setIcon(R.mipmap.ic_launcher);
            myDialog.setMessage("确定删除" + student.getName() + "?");
            myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delete(student, position);
                }
            });
            myDialog.setNegativeButton("取消",null);
            myDialog.show();
        }
    }

    @Override
    public int getCount() {
        return studentDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return studentDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("CutPasteId")
    @Override
    public View getView(int i, View studentView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (studentView == null) {
            studentView = View.inflate(studentContext, R.layout.student_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.text1 = studentView.findViewById(R.id.name);
            viewHolder.text2 = studentView.findViewById(R.id.major);

            studentView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) studentView.getTag();
        }

        TextView tv_name = studentView.findViewById(R.id.name);
        TextView tv_major = studentView.findViewById(R.id.major);
        ImageView iv_edit = studentView.findViewById(R.id.edit);
        ImageView iv_delete = studentView.findViewById(R.id.delete);
        //将数据源的一项数据和item的子元素绑定
        Student s = (Student) getItem(i);
        tv_name.setText(s.getName());
        tv_major.setText(s.getMajor());
        // 给adpter内部的控件增加一个监听
        iv_edit.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        //给删除和编辑的imageView设置一个标志，用item的位置position来定位，类似一个id,通过tag可以获得
        //list的item
        iv_delete.setTag(i);
        iv_edit.setTag(i);

        return studentView;
    }

    static class ViewHolder {
        TextView text1;
        TextView text2;
    }

    @SuppressLint("DefaultLocale")
    private void delete(final Student student, final int position){
        new DeleteStudentGetTask().execute(WebApiUrl.makeDeleteStudentUrl(student.getId()), Integer.toString(position));
    }

    private class DeleteStudentGetTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... urls) {
            return Integer.parseInt(urls[1]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            studentDatas.remove((int)result);
            StudentAdapter.this.notifyDataSetChanged();
        }
    }
}
