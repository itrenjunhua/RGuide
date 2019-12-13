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
import com.renj.highlight.type.HighLightShape;


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
public class SecondActivity extends Activity {

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
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        findViewById(R.id.bt_go_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThreeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addHighView() {
        RHighLightBgParams highLightBgParams = RHighLightBgParams.create(this);

        RHighLightViewParams rHighLightViewParams = RHighLightViewParams.create()
                .setHighView(R.id.id_btn_important)
                .setDecorLayoutId(R.layout.info_up)
                .setBlurShow(false)
                .setBorderShow(true)
                .setHighLightShape(HighLightShape.CIRCULAR)
                .setBorderLineType(BorderLineType.DASH_LINE)
                .setHighView(R.id.iv_hight)
                .setDecorLayoutId(R.layout.layout_hight)
                .setOnPosCallback(new OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLightMarginInfo marginInfo) {
                        marginInfo.rightMargin = rightMargin;
                        marginInfo.bottomMargin = bottomMargin + view.getHeight();
                    }
                });
        RHighLightManager.getInstance().addHighLightView(highLightBgParams, rHighLightViewParams, true).show();
    }
}
