# 新手操作引导 RGuide
Android 应用新手操作引导实现

## 主要功能

1. 支持高亮部分控件形式的引导
2. 支持添加覆盖整个页面的引导  
3. 支持各种属性和效果自定义设置
4. 支持多层引导并自动跳转
5. 覆盖整个页面的引导支持自定义覆盖层事件
6. 覆盖整个页面的引导支持跳过后面引导或者移除当前引导后重新查看后面引导
7. 高亮和覆盖效果都支持这是背景高斯模糊效果

## 效果图
![高亮部分控件形式](https://raw.githubusercontent.com/itrenjunhua/RGuide/master/images/highlight.gif)        ![高亮部分控件形式](https://raw.githubusercontent.com/itrenjunhua/RGuide/master/images/coverview.gif)

## 使用/参数说明

### 高亮形式

	// 创建高亮页面参数参数
	RHighLightPageParams highLightPageParams = RHighLightPageParams.create(@NonNull Activity activity) 
            .setAnchor(findViewById(R.id.id_container)) // 绑定根布局，在Activity中可不写;
            .setMaskColor(int maskColor) // 设置背景颜色
			.setAutoRemoveView(boolean autoRemoveView) // 设置点击任意位置是否自动移除高亮遮罩，默认true
			.setAutoShowNext(boolean autoShowNext) // 当移除(手动或自动)之后是否自动显示下一个高亮，如果有的话。默认true
            .setMaskBlur(boolean maskIsBlur, @IntRange(from = 0, to = 25) int maskBlurRadius) // 设置背景包含高斯模糊效果
            .setOnDecorClickListener(OnDecorClickListener onDecorClickListener); // 设置装饰布局点击回调
			
	// 创建高亮View相关参数
    RHighLightViewParams rHighLightViewParams = RHighLightViewParams.create()
            .setHighView(View highView) // 需要高亮的View。和方法 {@link #setHighView(int)} 二选一即可，若两个都设置了，该方法优先级更高
            .setHighView(int highViewId) // 需要高亮的ViewId。和方法 {@link #setHighView(View)} 二选一即可，两个都设置了，该方法优先级更低
            .setDecorLayoutId(@LayoutRes int decorLayoutId) // 设置高亮背景装饰布局
            .setHighLightShape(HighLightShape highLightShape) // 设置高亮形状，默认 矩形
            .setRadius(int radius) // 设置圆角度数。只有当形状为 {@link HighLightShape#RECTANGULAR} 时生效，单位dp
            .setBorderShow(boolean borderShow) // 设置是否需要边框
            .setBorderWidth(float borderWidth) // 设置边框宽度，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}，单位dp
            .setBorderColor(int borderColor) // 设置边框颜色，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}，该方法才能生效
            .setBorderShader(OnBorderShader onBorderShader) // 设置高亮边框渐变样式，优先级高于边框颜色 {@link #setBorderColor(int)}
            .setBorderLineType(BorderLineType borderLineType) // 设置边框类型，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}，该方法才能生效
            .setBorderIntervals(@NonNull float[] intervals) // 设置虚线边框的样式，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}并且边框类型为 {@link BorderLineType#DASH_LINE}，该方法才能生效
			.setBorderMargin(int borderMargin) // 设置绘制的边框线与高亮区域的边距
            .setBlurShow(boolean blurShow) // 设置是否需要模糊化边框，默认不显示
            .setBlurWidth(int blurSize) // 设置模糊边界的宽度，需要调用 {@link #setBlurShow(boolean)} 方法设置为 {@code true}，该方法才能生效，单位dp
            .setBlurColor(int blurColor) // 设置模糊边界颜色，前提是 {@link #setBlurShow(boolean)} 方法设置值为 {@code true}，默认透明色
            .setOnHLDecorInflateListener((decorLayoutView) -> {}) // 设置装饰布局初始化完成回调
			.setOnHLViewClickListener((highLightView, highViewId) -> {}) // 设置高亮布局点击监听
			.setOnDecorScrollListener((decorView, orientation, axis) -> {}) // 设置装饰背景滑动监听
            .setOnHLDecorPositionCallback((rightMargin, bottomMargin, rectF, marginInfo) -> {}); // 修正高亮控件和它的装饰控件相对位置

	// 添加和显示高亮View
	HighLightViewHelp highLightViewHelp = RGuideViewManager.createHighLightViewHelp();
	highLightViewHelp
		    .addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
		                      @NonNull RHighLightViewParams rHighLightViewParams) // 分开添加，表示分步显示
		    .addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
		                      @NonNull List<RHighLightViewParams> rHighLightBgParamsList) // 添加一个集合，表示一个页面同时高亮多个View
		    .showHighLightView(); // 显示高亮View

#### HighLightViewHelp 其他常用方法

* addHighLightView()：增加一个高亮的布局
* showHighLightView()：显示高亮布局，点击之后自动显示下一个高亮视图，如果有上一个高亮没有移除，会自动移除掉
* removeHighLightView()：移除指定的高亮Page
* skipAllHighLightView()：移除后面的高亮Page/跳过后面所有的高亮Page
* setOnHLViewRemoveListener()：设置移除高亮监听


---

========================================== 分割线 ==========================================

---

### 覆盖形式

	// 创建覆盖页面类型参数
	RCoverViewParams rCoverViewParams = RCoverViewParams.create(this)
	        .setCoverLayoutId(@LayoutRes int coverLayoutId) // 设置覆盖布局
			.setAutoRemoveView(boolean autoRemoveView) // 设置点击任意位置是否自动移除高亮遮罩，默认true
			.setAutoShowNext(boolean autoShowNext) // 当移除(手动或自动)之后是否自动显示下一个高亮，如果有的话。默认true
            .setMaskBlur(boolean maskIsBlur, @IntRange(from = 0, to = 25) int maskBlurRadius) // 设置背景包含高斯模糊效果
	        .setOnCViewInflateListener((rCoverViewParams, coverView) -> {}) // 遮罩层布局初始化完成回调
			.setOnDecorScrollListener((decorView, orientation, axis) -> {}) // 设置装饰背景滑动监听
	        .setOnDecorClickListener(() ->{}); // 设置遮罩层点击回调

	// 添加和显示遮罩层
	CoverViewHelp coverViewHelp = RGuideViewManager.createCoverViewHelp();
	coverViewHelp
            .addCoverView(@NonNull RCoverViewParams rCoverViewParams) // 在整个窗体上面增加一层布局，可以多次添加
            .showCoverView(); // 开始显示覆盖布局


#### CoverViewHelp 其他常用方法

* addCoverView()：在整个窗体上面增加一层布局，默认点击移除视图
* showCoverView()：显示高亮布局，点击之后自动显示下一个高亮视图
* removeCoverView()：移除当前的遮罩层
* skipAllCoverView()：移除后面的遮罩层/跳过后面所有的遮罩层
* setOnCViewRemoveListener()：设置遮罩移除监听

## 混淆

    -keep class com.renj.guide.**{*;}
    -keep emum com.renj.guide.highlight.type.* {
        **[] $VALUES;
        public *;
    }
    -dontwarn com.renj.guide.**

## 博客说明
博客说明地址：<http://blog.csdn.net/itrenj/article/details/53890118>