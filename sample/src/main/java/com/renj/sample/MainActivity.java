package com.renj.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.renj.highlight.HighLightMarginInfo;
import com.renj.highlight.RHighLightBgParams;
import com.renj.highlight.RHighLightManager;
import com.renj.highlight.RHighLightViewParams;
import com.renj.highlight.callback.OnPosCallback;
import com.renj.highlight.type.BorderLineType;
import com.renj.sample.utils.Logger;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.id_btn_important);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                showHighView(true);
            }
        });

        // 同时显示
        findViewById(R.id.btn_reshow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHighView(true);
            }
        });

        // 分步显示
        findViewById(R.id.btn_reshow_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHighView(false);
            }
        });

        // 进入下一页
        findViewById(R.id.go_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showHighView(boolean together) {
        RHighLightBgParams highLightBgParams = RHighLightBgParams.create(this)
                .setAnchor(findViewById(R.id.id_container)); //绑定根布局，在Activity中可不写;
        RHighLightViewParams rHighLightViewParams1 = RHighLightViewParams.create()
                .setHighView(R.id.id_btn_important)
                .setDecorLayoutId(R.layout.info_up)
                .setBlurShow(false)
                .setBorderShow(true)
                .setBorderLineType(BorderLineType.DASH_LINE)
                .setOnPosCallback(new OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLightMarginInfo marginInfo) {
                        Logger.e("rectF.right => " + rectF.right);
                        Logger.e("rectF.width() => " + rectF.width());
                        Logger.e("rectF.bottom => " + rectF.bottom);
                        Logger.e("--------------------------------------------------------------------");

                        marginInfo.leftMargin = rectF.right - rectF.width() / 2;
                        marginInfo.topMargin = rectF.bottom;

                        Logger.e("1 => leftMargin:" + marginInfo.leftMargin + "; topMargin: " + marginInfo.topMargin);
                    }
                });
        RHighLightViewParams rHighLightViewParams2 = RHighLightViewParams.create()
                .setHighView(R.id.id_btn_amazing)
                .setDecorLayoutId(R.layout.info_down)
                .setOnPosCallback(new OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLightMarginInfo marginInfo) {
                        Logger.e("rightMargin => " + rightMargin);
                        Logger.e("rectF.width() => " + rectF.width());
                        Logger.e("rectF.height() => " + rectF.height());
                        Logger.e("bottomMargin => " + bottomMargin);
                        Logger.e("--------------------------------------------------------------------");
                        marginInfo.rightMargin = rightMargin + rectF.width() / 2;
                        marginInfo.bottomMargin = bottomMargin + rectF.height();

                        Logger.e("2 => leftMargin: " + marginInfo.leftMargin + " ; topMargin: " + marginInfo.topMargin);
                    }
                });

        // 是否一起显示
        if (together) {
            List<RHighLightViewParams> lightBgParams = new ArrayList<>();
            lightBgParams.add(rHighLightViewParams1);
            lightBgParams.add(rHighLightViewParams2);
            RHighLightManager.getInstance().addHighLightView(highLightBgParams, lightBgParams, true).show();
        } else {
            RHighLightManager.getInstance()
                    .addHighLightView(highLightBgParams, rHighLightViewParams1, true) // 分开添加，表示分步显示
                    .addHighLightView(highLightBgParams, rHighLightViewParams2, true)
                    .show();
        }
    }
}
