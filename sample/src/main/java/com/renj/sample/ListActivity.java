package com.renj.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.renj.guide.RGuideViewManager;
import com.renj.guide.highlight.HighLightViewHelp;
import com.renj.guide.highlight.RHighLightPageParams;
import com.renj.guide.highlight.RHighLightViewParams;
import com.renj.guide.highlight.type.HighLightShape;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-06-03   11:19
 * <p>
 * 描述：列表条目高亮
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;


    private List<String> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recycler_view);

        initListData();
        setRecyclerViewAdapter();
    }

    private void initListData() {
        for (int i = 0; i < 30; i++) {
            listData.add((i + 1) + ". 列表数据，高亮条目");
        }
    }

    private void setRecyclerViewAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter<ListViewHolder>() {
            @NonNull
            @Override
            public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(ListActivity.this).inflate(R.layout.activity_list_item, parent, false);
                return new ListViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
                holder.tvItem.setText(listData.get(position));
                if (position == 0) {
                    holder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            showHighLightView(holder.itemView, holder.tvItem);
                            holder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return listData.size();
            }
        });
    }

    private void showHighLightView(View itemView, View tvItem) {
        HighLightViewHelp rGuideViewManager = RGuideViewManager.createHighLightViewHelp();

        RHighLightPageParams highLightPageParams = RHighLightPageParams.create(this)
                .setAnchor(recyclerView); // 绑定根布局，在Activity中可不写

        RHighLightViewParams rHighLightViewParams = RHighLightViewParams.create()
                .setHighView(tvItem)
                .setDecorLayoutId(R.layout.info_empty)
                .setBlurShow(true)
                .setBlurWidth(8)
                .setBorderShow(true)
                .setBorderColor(Color.RED)
                .setRadius(5)
                .setHighLightShape(HighLightShape.RECTANGULAR)
                .setOnHLDecorInflateListener(decorLayoutView -> {
                    TextView textView = decorLayoutView.findViewById(R.id.tv_empty);
                    textView.setText("高亮条目中的控件");
                })
                .setOnHLDecorPositionCallback((rightMargin, bottomMargin, rectF, marginInfo) -> {
                    marginInfo.leftMargin = rectF.left + 60;
                    marginInfo.topMargin = rectF.bottom + 20;
                });
        RHighLightViewParams rHighLightViewParams1 = RHighLightViewParams.create()
                .setHighView(itemView)
                .setDecorLayoutId(R.layout.info_empty)
                .setBlurShow(true)
                .setBlurWidth(8)
                .setBorderShow(true)
                .setBorderColor(Color.RED)
                .setRadius(0)
                .setHighLightShape(HighLightShape.RECTANGULAR).setOnHLDecorInflateListener(decorLayoutView -> {
                    TextView textView = decorLayoutView.findViewById(R.id.tv_empty);
                    textView.setText("高亮整个条目");
                })
                .setOnHLDecorPositionCallback((rightMargin, bottomMargin, rectF, marginInfo) -> {
                    marginInfo.leftMargin = rectF.right - rectF.width() / 2 - 80;
                    marginInfo.topMargin = rectF.bottom + 20;
                });

        rGuideViewManager
                .addHighLightView(highLightPageParams, rHighLightViewParams)
                .addHighLightView(highLightPageParams, rHighLightViewParams1)
                .showHighLightView();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}
