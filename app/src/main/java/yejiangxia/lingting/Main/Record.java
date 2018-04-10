package yejiangxia.lingting.Main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yejiangxia.lingting.R;

public class Record extends AppCompatActivity {
    private SQLiteDatabase dbRead;
    private ListView lv;
    private List<Map<String, String>> Getmylist = new ArrayList<Map<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record);

        lv = (ListView) findViewById(R.id.lv);

        final DB db = new DB(this);


        dbRead = db.getReadableDatabase();
        Cursor c = dbRead.query("user", null, null, null, null, null, null);
        while (c.moveToNext()) {
            String date = c.getString(c.getColumnIndex("date"));
            String time = c.getString(c.getColumnIndex("time"));
            String count = c.getString(c.getColumnIndex("count"));
            Map<String, String> map = new HashMap<String, String>();
            map.put("date", date);
            map.put("time", time);
            map.put("count", count);
            Getmylist.add(map);
        }
        System.out.println(Getmylist);
        lv.setAdapter(new MyAdapter(Getmylist));
    }



    class MyAdapter extends BaseAdapter {
        private List<Map<String, String>> mylist;
        public MyAdapter(List<Map<String, String>> mylist) {
            this.mylist = mylist;
        }

        @Override
        public int getCount() {
            Log.i("TAG","长度："+mylist.size()+"");
            return mylist.size();
        }

        @Override
        public Object getItem(int position) {
            return mylist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder{
            private TextView tvDate;
            private TextView tvTime;
            private TextView tvCount;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if(convertView==null){


                convertView = View.inflate(Record.this,R.layout.list_item1,null);

                viewHolder = new ViewHolder();
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
                viewHolder.tvTime= (TextView) convertView.findViewById(R.id.tvTime);
                viewHolder.tvCount= (TextView) convertView.findViewById(R.id.tvCount);
                convertView.setTag(viewHolder);

            }else {

                viewHolder = (ViewHolder) convertView.getTag();
            }
            Log.i("TAG",mylist.toString());
            viewHolder.tvDate.setText(mylist.get(position).get("date"));
            viewHolder.tvTime.setText(mylist.get(position).get("time"));
            viewHolder.tvCount.setText(mylist.get(position).get("count")+"次");

            System.out.println("11");


            return convertView;

        }


    }
    class DB extends SQLiteOpenHelper {

        //public DB(){ super(null, "db", null, 1); }

        public DB(Context context) {
            super(context, "db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE user(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "date TEXT DEFAULT\"\"," +
                    ""+" time TEXT DEFAULT\"\","+
                    "count INTEGER DEFAULT\"\")");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    }

