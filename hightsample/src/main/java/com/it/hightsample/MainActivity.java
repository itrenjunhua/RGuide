package com.it.hightsample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.it.hightsample.utils.Logger;
import com.renj.hightlight.HighLightBuilder;
import com.renj.hightlight.HighLightManager;
import com.renj.hightlight.callback.OnPosCallback;
import com.renj.hightlight.type.BorderLineType;


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
                addHighView();
            }
        });


        findViewById(R.id.go_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addHighView() {
        HighLightManager highLightManager = HighLightBuilder.newInstance(this)
                .anchor(findViewById(R.id.id_container)) //绑定根布局，在Activity中可不写
                .setIntercept(true) // 查看注释和代码，可设置其他属性
                .isBlur(false)
                .setIsNeedBorder(true)
                .setBorderLineType(BorderLineType.DASH_LINE)
                .build();

        highLightManager
                .addHighLight(R.id.id_btn_important, R.layout.info_up, new OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLightManager.MarginInfo marginInfo) {
                        Logger.e("rectF.right" + rectF.right);
                        Logger.e("rectF.width()" + rectF.width());
                        Logger.e("rectF.bottom" + rectF.bottom);
                        Logger.e("--------------------------------------------------------------------");

                        marginInfo.leftMargin = rectF.right - rectF.width() / 2;
                        marginInfo.topMargin = rectF.bottom;

                        Logger.e("1. " + marginInfo.leftMargin + "  :  " + marginInfo.topMargin);
                    }
                })
                .addHighLight(R.id.id_btn_amazing, R.layout.info_down, new OnPosCallback() {
                    /**
                     * @param rightMargin
                     *            高亮view在anchor中的右边距
                     * @param bottomMargin
                     *            高亮view在anchor中的下边距
                     * @param rectF
                     *            高亮view的l,t,r,b,w,h都有
                     * @param marginInfo
                     *            设置你的布局的位置，一般设置l,t或者r,b
                     */
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLightManager.MarginInfo marginInfo) {

                        Logger.e("rightMargin" + rightMargin);
                        Logger.e("rectF.width()" + rectF.width());
                        Logger.e("rectF.height()" + rectF.height());
                        Logger.e("bottomMargin" + bottomMargin);
                        Logger.e("--------------------------------------------------------------------");
                        marginInfo.rightMargin = rightMargin + rectF.width() / 2;
                        marginInfo.bottomMargin = bottomMargin + rectF.height();

                        Logger.e("2. " + marginInfo.leftMargin + "  :  " + marginInfo.topMargin);
                    }
                });

        highLightManager.show();
    }
}
