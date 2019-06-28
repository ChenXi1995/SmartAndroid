package com.cxh.smart.future.io;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxh.smart.city.select.ArerSelectActivity;
import com.cxh.smart_refreshview.SmartRefreshView;
import com.cxh.smart_refreshview.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private SmartRefreshView smartRefreshView;
    private RecyclerView recyclerView;
    private List<String> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.smartRefreshView = this.findViewById(R.id.smart_refresh);
        this.recyclerView = this.findViewById(R.id.recyclerview);
        datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("item"+i);
        }
        final TestAdapter adapter = new TestAdapter();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        smartRefreshView.setOnRefreshListener(new SmartRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                switch (direction)
                {
                    case TOP:

                        break;
                    case BOTTOM:
                        for (int i = 0; i < 20; i++) {
                            datas.add("refresh"+i);
                        }
                        adapter.notifyDataSetChanged();
                        break;
                }

                smartRefreshView.setRefreshing(false);

            }
        });

        recyclerView.setAdapter(adapter);
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyHolder>{
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_test,parent,false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.text.setText(datas.get(position));
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,ArerSelectActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{

            private TextView text;
            public MyHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.tv_test);
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

}
