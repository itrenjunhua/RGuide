package com.it.hightsample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.renj.hightlight.HighLightBuilder;
import com.renj.hightlight.HighLightManager;
import com.renj.hightlight.callback.OnPosCallback;
import com.renj.hightlight.type.BorderLineType;
import com.renj.hightlight.type.HighLightShape;


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
        // 使用默认的设置
        HighLightManager highLightManager = HighLightBuilder.newInstance(this)
                .setBorderLineType(BorderLineType.FULL_LINE) // 使用实线
                .build();
        highLightManager
                .addHighLight(R.id.iv_hight, R.layout.layout_hight, HighLightShape.CIRCULAR, new OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLightManager.MarginInfo marginInfo) {
                        marginInfo.rightMargin = rightMargin;
                        marginInfo.bottomMargin = bottomMargin + view.getHeight();
                    }
                });// 圆形高亮
        highLightManager.show();
    }
}
