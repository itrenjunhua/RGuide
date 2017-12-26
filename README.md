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

    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/id_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent" >
    
        <Button
            android:id="@+id/id_btn_amazing"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_alignParentRight="true"
            android:layout_margin="16dp"
            android:text="账户信息" />
    
        <Button
            android:id="@+id/go_next"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:layout_margin="16dp"
            android:text="进入下一页" />
    
        <Button
            android:id="@+id/id_btn_important"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentLeft="true"
            android:layout_alignParentTop="true"
            android:layout_margin="16dp"
            android:text="个人中心" />
    
    </RelativeLayout>
> Java代码

    HighLight highLight = new HighLight(this)
                    .anchor(findViewById(R.id.id_container)) // 绑定根布局，在Activity中可不写
                    .setIntercept(true) 
                    .setShadow(false)
                    .setIsNeedBorder(true)
                    .setShadow(false)
                    .setBroderLineType(HighLight.BorderLineType.DASH_LINE)
                    .addHighLight(R.id.id_btn_important, R.layout.info_up, new HighLight.OnPosCallback() {
                        @Override
                        public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                            marginInfo.leftMargin = rectF.right - rectF.width() / 2;
                            marginInfo.topMargin = rectF.bottom;
                        }
                    })
                    .addHighLight(R.id.id_btn_amazing, R.layout.info_down, new HighLight.OnPosCallback() {
                        @Override
                        public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                            marginInfo.rightMargin = rightMargin + rectF.width() / 2;
                            marginInfo.bottomMargin = bottomMargin + rectF.height();
                        }
                    });
    
            highLight.show(); // 开始显示

> 绘制圆形高亮区域设置(布局代码省略)

    HighLight highLight = new HighLight(this)
                    .setBroderLineType(HighLight.BorderLineType.FULL_LINE) // 使用实线
                    .addHighLight(R.id.iv_hight, R.layout.layout_hight, new HighLight.OnPosCallback() {
                        @Override
                        public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                            marginInfo.rightMargin = rightMargin;
                            marginInfo.bottomMargin = bottomMargin + view.getHeight();
                        }
                    }, HighLight.HightLightShape.CIRCULAR);// 圆形高亮
            highLight.show();
            
> 使整个界面都成半透明状态(没有高亮区域，不需要xml布局文件)

    HighLight highLight = new HighLight(this)
                    .setOnClickCallback(new HighLight.OnClickCallback() {
                        @Override
                        public void onClick() {
                            Toast.makeText(ThreeActivity.this,"覆盖层被点击了",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addLayout(R.layout.layout_three);
## 博客说明
博客说明地址：<http://blog.csdn.net/itrenj/article/details/53890118>