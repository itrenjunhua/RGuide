package com.it.hightsample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.it.hightsample.utils.Logger;
import com.renj.hightlight.HighLight;


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
               addHightView();
           }
       });


        findViewById(R.id.go_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addHightView() {
        HighLight highLight = new HighLight(this)
                .anchor(findViewById(R.id.id_container)) //绑定根布局，在Activity中可不写
                .setIntercept(true) // 查看注释和代码，可设置其他属性
                .setShadow(false)
                .setIsNeedBorder(true)
                .setShadow(false)
                .setBroderLineType(HighLight.BorderLineType.DASH_LINE)
                .addHighLight(R.id.id_btn_important, R.layout.info_up, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                        Logger.e("rectF.right" + rectF.right);
                        Logger.e("rectF.width()" + rectF.width());
                        Logger.e("rectF.bottom" + rectF.bottom);
                        Logger.e("--------------------------------------------------------------------");

                        marginInfo.leftMargin = rectF.right - rectF.width() / 2;
                        marginInfo.topMargin = rectF.bottom;

                        Logger.e("1. " + marginInfo.leftMargin + "  :  " + marginInfo.topMargin);
                    }
                })
                .addHighLight(R.id.id_btn_amazing, R.layout.info_down, new HighLight.OnPosCallback() {
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
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {

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

        highLight.show();
    }
}
