# 新手操作引导 RGuide
Android 应用新手操作引导实现

## 主要功能

1. 支持高亮部分控件形式的引导
2. 支持添加覆盖整个页面的引导  
3. 支持各种属性和效果自定义设置
4. 支持多层引导并自动跳转
5. 覆盖整个页面的引导支持自定义覆盖层事件
6. 覆盖整个页面的引导支持跳过后面引导或者移除当前引导后重新查看后面引导

## 效果图
![高亮部分控件形式](https://raw.githubusercontent.com/itrenjunhua/MyHightLight/master/images/highlight.gif)        ![高亮部分控件形式](https://raw.githubusercontent.com/itrenjunhua/MyHightLight/master/images/coverview.gif)

## 使用/参数说明

### 高亮形式

	// 创建高亮页面参数参数
	RHighLightPageParams highLightPageParams = RHighLightPageParams.create(@NonNull Activity activity) 
            .setAnchor(findViewById(R.id.id_container)) // 绑定根布局，在Activity中可不写;
            .setMaskColor(int maskColor) // 设置背景颜色
            .setOnDecorClickListener(OnDecorClickListener onDecorClickListener); // 设置装饰布局点击回调
			
	// 创建高亮View相关参数
    RHighLightViewParams rHighLightViewParams = RHighLightViewParams.create()
            .setHighView(View highView) // 需要高亮的View。和方法 {@link #setHighView(int)} 二选一即可，若两个都设置了，该方法优先级更高
            .setHighView(View highView) // 需要高亮的ViewId。和方法 {@link #setHighView(View)} 二选一即可，两个都设置了，该方法优先级更低
            .setDecorLayoutId(@LayoutRes int decorLayoutId) // 设置高亮背景装饰布局
            .setHighLightShape(HighLightShape highLightShape) // 设置高亮形状，默认 矩形
            .setRadius(int radius) // 设置圆角度数。只有当形状为 {@link HighLightShape#RECTANGULAR} 时生效，单位dp
            .setBorderShow(boolean borderShow) // 设置是否需要边框
            .setBorderWidth(float borderWidth) // 设置边框宽度，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}，单位dp
            .setBorderColor(int borderColor) // 设置边框颜色，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}，该方法才能生效
            .setBorderLineType(BorderLineType borderLineType) // 设置边框类型，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}，该方法才能生效
            .setIntervals(@NonNull float[] intervals) // 设置虚线边框的样式，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}并且边框类型为 {@link BorderLineType#DASH_LINE}，该方法才能生效
            .setBlurShow(boolean blurShow) // 设置是否需要模糊化边框，默认不显示
            .setBlurWidth(int blurSize) // 设置模糊边界的宽度，需要调用 {@link #setBlurShow(boolean)} 方法设置为 {@code true}，该方法才能生效，单位dp
            .setOnDecorViewInflateFinish((decorLayoutView) -> {}) // 设置装饰布局初始化完成回调
            .setOnPosCallback((rightMargin, bottomMargin, rectF, marginInfo) -> {}); // 修正高亮控件和它的装饰控件相对位置

	// 添加和显示高亮View
	RGuideViewManager.getInstance()
		    .addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
		                      @NonNull RHighLightViewParams rHighLightViewParams) // 分开添加，表示分步显示
		    .addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
		                      @NonNull List<RHighLightViewParams> rHighLightBgParamsList) // 添加一个集合，表示一个页面同时高亮多个View
		    .showHighLightView(); // 显示高亮View

### 覆盖形式

	// 创建覆盖页面类型参数
	RCoverViewParams rCoverViewParams = RCoverViewParams.create(this)
	        .setCoverLayoutId(@LayoutRes int coverLayoutId) // 设置覆盖布局
	        .setOnCoverViewInflateFinish((rCoverViewParams, coverView) -> {}) // 遮罩层布局初始化完成回调
	        .setOnDecorClickListener(() ->{}); // 设置遮罩层点击回调

	// 添加和显示遮罩层
	RGuideViewManager.getInstance()
            .addCoverView(@NonNull RCoverViewParams rCoverViewParams) // 在整个窗体上面增加一层布局，可以多次添加
            .showCoverView(); // 开始显示覆盖布局

	// 其他方法 通过 RGuideViewManager.getInstance(). 调用
	removeCoverView(@NonNull RCoverViewParams rCoverViewParams, @NonNull View coverView) // 移除指定的遮罩层，默认会同时清除其他的遮罩层
	removeCoverView(@NonNull RCoverViewParams rCoverViewParams, @NonNull View coverView, boolean clearOtherCoverView) // 移除指定的遮罩层，并设置是否需要移除其他的遮罩层
	skipAllCoverView() // 移除后面的遮罩层/跳过后面所有的遮罩层
	
	对于 removeCoverView(@NonNull RCoverViewParams rCoverViewParams, @NonNull View coverView, boolean clearOtherCoverView) 方法，详细说明如下：
     	* 移除指定的遮罩层，并设置是否需要移除其他的遮罩层。
     		* 如果移除（clearOtherCoverView值传true），那么该页面就不会在显示遮罩层了，除非再次添加和显示
     		* 如果不移除（clearOtherCoverView值传false）并且后面还有，那么可以继续调用 {@link #showCoverView()} 方法显示。
     		
     	* 特别注意：当当前页面不在需要显示并且后面还有未显示完的遮罩层时，必须清除其他的遮罩层，
     		* 方式1：该方法的参数{@code clearOtherCoverView} 值传 {@code true}
     		* 方式2：手动调用 {@link #skipAllCoverView()} 方法跳过当前页面后面所有的遮罩层。
     	* 如果不试用上述的方法清除遮罩层，那么后面的遮罩层使用(包括当前页或者其他页面)将会出现问题

## 混淆

    -keep class com.renj.guide.**{*;}
    -keep emum com.renj.guide.highlight.type.* {
        **[] $VALUES;
        public *;
    }
    -dontwarn com.renj.guide.**

## 博客说明
博客说明地址：<http://blog.csdn.net/itrenj/article/details/53890118>