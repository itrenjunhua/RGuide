package com.renj.sample;

import android.graphics.Color;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.renj.guide.RGuideViewManager;
import com.renj.guide.highlight.RHighLightPageParams;
import com.renj.guide.highlight.RHighLightViewParams;
import com.renj.guide.highlight.type.HighLightShape;


/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2016-08-03    14:54
 * <p/>
 * 描述：
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class SecondActivity extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        view = findViewById(R.id.iv_hight);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                addHighView();
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        findViewById(R.id.bt_show).setOnClickListener(v -> addHighView());
        findViewById(R.id.bt_back).setOnClickListener(v -> finish());
    }

    private void addHighView() {
        RHighLightPageParams rHighLightPageParams = RHighLightPageParams.create(this);
        RHighLightViewParams rHighLightViewParams = RHighLightViewParams.create()
                .setHighView(R.id.id_btn_important)
                .setDecorLayoutId(R.layout.info_up)
                .setBorderShow(true)
                //.setBorderColor(Color.BLUE)
                .setBorderWidth(1)
                .setBorderMargin(4)
                .setBorderShader(rectF -> new SweepGradient(rectF.right - rectF.width() / 2, rectF.bottom - rectF.height() / 2,
                        new int[]{Color.RED, Color.GREEN}, null))
                .setBlurShow(false)
                .setHighLightShape(HighLightShape.CIRCULAR)
                .setHighView(R.id.iv_hight)
                .setDecorLayoutId(R.layout.layout_hight)
                .setOnHLDecorPositionCallback((rightMargin, bottomMargin, rectF, marginInfo) -> {
                    marginInfo.rightMargin = rightMargin;
                    marginInfo.bottomMargin = bottomMargin + view.getHeight();
                });
        RGuideViewManager.createHighLightViewHelp().addHighLightView(rHighLightPageParams, rHighLightViewParams).showHighLightView();
    }
}
