package com.renj.sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.renj.guide.RGuideViewManager;
import com.renj.guide.highlight.RHighLightPageParams;
import com.renj.guide.highlight.RHighLightViewParams;
import com.renj.guide.highlight.type.BorderLineType;
import com.renj.guide.highlight.type.HighLightShape;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-12-16   15:32
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class FirstActivity extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        view = findViewById(R.id.id_btn_important);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                showHighView(true);
            }
        });

        // 同时显示
        findViewById(R.id.btn_reshow).setOnClickListener(v -> showHighView(true));

        // 分步显示
        findViewById(R.id.btn_reshow_two).setOnClickListener(v -> showHighView(false));

        // 进入下一页
        findViewById(R.id.go_next).setOnClickListener(v -> {
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        // 返回上一页
        findViewById(R.id.bt_back).setOnClickListener(v -> {
            finish();
        });
    }

    private void showHighView(boolean together) {
        RHighLightPageParams highLightPageParams = RHighLightPageParams.create(this)
                .setAnchor(findViewById(R.id.id_container)); // 绑定根布局，在Activity中可不写;
        RHighLightViewParams rHighLightViewParams1 = RHighLightViewParams.create()
                .setHighView(R.id.id_btn_important)
                .setDecorLayoutId(R.layout.info_up)
                .setBlurShow(true)
                .setBlurWidth(8)
                .setBorderShow(true)
                .setBorderColor(Color.RED)
                .setRadius(5)
                .setHighLightShape(HighLightShape.RECTANGULAR)
                .setOnPosCallback((rightMargin, bottomMargin, rectF, marginInfo) -> {
                    marginInfo.leftMargin = rectF.right - rectF.width() / 2;
                    marginInfo.topMargin = rectF.bottom;
                });
        RHighLightViewParams rHighLightViewParams2 = RHighLightViewParams.create()
                .setHighView(R.id.id_btn_amazing)
                .setDecorLayoutId(R.layout.info_down)
                .setBorderLineType(BorderLineType.DASH_LINE)
                .setIntervals(new float[]{12, 12})
                .setOnPosCallback((rightMargin, bottomMargin, rectF, marginInfo) -> {
                    marginInfo.rightMargin = rightMargin + rectF.width() / 2;
                    marginInfo.bottomMargin = bottomMargin + rectF.height();
                });

        // 是否一起显示
        if (together) {
            List<RHighLightViewParams> lightBgParams = new ArrayList<>();
            lightBgParams.add(rHighLightViewParams1);
            lightBgParams.add(rHighLightViewParams2);
            RGuideViewManager.getInstance().addHighLightView(highLightPageParams, lightBgParams).showHighLightView();
        } else {
            RGuideViewManager.getInstance()
                    .addHighLightView(highLightPageParams, rHighLightViewParams1) // 分开添加，表示分步显示
                    .addHighLightView(highLightPageParams, rHighLightViewParams2)
                    .showHighLightView();
        }
    }
}