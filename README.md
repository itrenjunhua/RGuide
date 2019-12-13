# 新手操作引导 MyHightLight
Android 应用新手操作引导实现

## 代码功能说明
> 1.支持给界面添加半透明层(除指定id的控件外，指定id的控件将不会绘制【高亮】)  
> 2.支持添加覆盖整个页面的引导，没有高亮区域  
> 3.支持给高亮区域设置边框，边框支持实线和虚线两种形式以及设置颜色  
> 4.各种属性和效果支持自定义设置

## 效果图
![操作引导效果图](https://raw.githubusercontent.com/itrenjunhua/MyHightLight/master/heightlight.gif)

## 使用示例
> 引导布局

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:orientation="vertical">
    
        <ImageView
            android:id="@+id/id_iv_arrow"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@mipmap/jt_up"/>

        <TextView
            android:layout_below="@id/id_iv_arrow"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="个人中心在这里"
            android:textColor="@android:color/white"/>
    
    </LinearLayout>
> Java代码

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

> 绘制圆形高亮区域设置(布局代码省略)

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
            
> 使整个界面都成半透明状态(没有高亮区域，不需要xml布局文件)

    HighLightBuilder.newInstance(this)
                    .setOnClickCallback(new OnClickCallback() {
                        @Override
                        public void onClick() {
                            Toast.makeText(ThreeActivity.this, "覆盖层被点击了", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build()
                    .addLayout(R.layout.layout_three);

## 混淆

    -keep class com.renj.highlight.**{*;}
    -keep emum com.renj.highlight.type.* {
        **[] $VALUES;
        public *;
    }
    -dontwarn com.renj.hightlight.**

## 博客说明
博客说明地址：<http://blog.csdn.net/itrenj/article/details/53890118>