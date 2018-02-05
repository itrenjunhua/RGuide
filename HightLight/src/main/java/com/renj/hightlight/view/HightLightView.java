package com.renj.hightlight.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.renj.hightlight.HighLight;

import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2016-08-02    17:26
 * <p/>
 * 描述：绘制高亮View
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class HightLightView extends FrameLayout {
    /**
     * 默认模糊边界的大小
     */
    private static final int DEFAULT_WIDTH_BLUR = 15;
    /**
     * 默认圆角度数
     */
    private static final int DEFAULT_RADIUS = 6;
    /**
     * 用于实现新绘制的像素与Canvas上对应位置已有的像素按照混合规则进行颜色混合
     */
    private static final PorterDuffXfermode MODE_DST_OUT = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    /**
     * 用于表示高亮区域的图片
     */
    private Bitmap mMaskBitmap;
    /**
     * 绘制高亮区域的画笔
     */
    private Paint mPaint;
    /**
     * 用于保存高亮View的集合
     */
    private List<HighLight.ViewPosInfo> mViewRects;
    /**
     * HighLight对象
     */
    private HighLight mHighLight;
    /**
     * 打气筒
     */
    private LayoutInflater mInflater;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 是否需要模糊边界，默认不需要
     */
    private boolean isBlur = false;
    /**
     * 背景颜色
     */
    private int maskColor = 0x99000000;
    /**
     * 是否需要边框，默认需要
     */
    private boolean isNeedBorder = true;
    /**
     * 模糊边界大小，默认15
     */
    private int blurSize = DEFAULT_WIDTH_BLUR;
    /**
     * 圆角大小，默认6
     */
    private int radius = DEFAULT_RADIUS;
    /**
     * 边框颜色，默认和背景颜色一样
     */
    private int borderColor = maskColor;
    /**
     * 边框类型,默认虚线 HighLight.BorderLineType.DASH_LINE
     */
    private HighLight.BorderLineType borderLineType = HighLight.BorderLineType.DASH_LINE;
    /**
     * 边框宽度，单位：dp，默认3dp
     */
    private float borderWidth = 3;
    /**
     * 偏移量，直接使用1即可
     */
    private int phase = 1;
    /**
     * 虚线的排列方式，需要setIsNeedBorder(true)并且边框类型为HighLight.BorderLineType.DASH_LINE，该样式才能生效
     */
    private float[] intervals;


    /**
     * 设置是否需要边框
     *
     * @param isNeedBorder
     */
    public void setIsNeedBorder(boolean isNeedBorder) {
        this.isNeedBorder = isNeedBorder;
    }

    /**
     * 是否需要模糊边界
     *
     * @param isBlur
     */
    public void setIsBlur(boolean isBlur) {
        this.isBlur = isBlur;
    }

    /**
     * 设置模糊边界的宽度，需要setIsBlur(true)，该方法才能生效
     *
     * @param blurSize
     */
    public void setBlurWidth(int blurSize) {
        this.blurSize = blurSize;
        // 判断是否需要设置模糊边框
        if (isBlur)
            mPaint.setMaskFilter(new BlurMaskFilter(this.blurSize,
                    BlurMaskFilter.Blur.SOLID));
    }

    /**
     * 设置边框颜色，需要setIsNeedBorder(true)，该方法才能生效
     *
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * 设置边框类型，需要setIsNeedBorder(true)，该方法才能生效
     *
     * @param borderLineType
     */
    public void setBorderLineType(HighLight.BorderLineType borderLineType) {
        this.borderLineType = borderLineType;
    }

    /**
     * 设置背景颜色
     *
     * @param maskColor
     */
    public void setMaskColor(int maskColor) {
        this.maskColor = maskColor;
    }

    /**
     * 设置边框宽度，需要setIsNeedBorder(true)，该方法才能生效；不需要转换单位，默认dp
     *
     * @param borderWidth
     */
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * 设置虚线边框的样式，需要setIsNeedBorder(true)并且边框类型为HighLight.BorderLineType.DASH_LINE，该方法才能生效；不需要转换单位，默认dp
     * <p/>
     * 必须是偶数长度,且>=2,指定了多少长度的实线之后再画多少长度的空白.
     * 如在 new float[] { 1, 2, 4, 8}中,表示先绘制长度1的实线,再绘制长度2的空白,再绘制长度4的实线,再绘制长度8的空白,依次重复
     *
     * @param intervals
     */
    public void setIntervals(@NonNull float[] intervals) {
        int length = intervals.length;
        if ((length >= 2) && (length % 2 == 0)) {
            this.intervals = new float[length];
            for (int i = 0; i < length; i++) {
                this.intervals[i] = dip2px(intervals[i]);
            }
        } else {
            throw new IllegalArgumentException("元素的个数必须大于2并且是偶数");
        }
    }

    /**
     * 设置圆角度数
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public HightLightView(@NonNull Context context, @NonNull HighLight highLight, int maskColor,
                          @NonNull List<HighLight.ViewPosInfo> viewRects) {
        super(context);
        this.context = context;
        mHighLight = highLight;
        mInflater = LayoutInflater.from(context);
        mViewRects = viewRects;
        this.maskColor = maskColor;
        setWillNotDraw(false);
        init();// 初始化参数
    }

    /**
     * 初始化一些配置参数
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        addViewForTip();

        // 初始化虚线的样式
        intervals = new float[]{dip2px(4), dip2px(4)};
    }

    /**
     * 将需要高亮的View增加到帧布局上方
     */
    private void addViewForTip() {
        for (HighLight.ViewPosInfo viewPosInfo : mViewRects) {
            View view = mInflater.inflate(viewPosInfo.layoutId, this, false);
            FrameLayout.LayoutParams lp = buildTipLayoutParams(view,
                    viewPosInfo);

            if (lp == null)
                continue;

            lp.leftMargin = (int) viewPosInfo.marginInfo.leftMargin;
            lp.topMargin = (int) viewPosInfo.marginInfo.topMargin;
            lp.rightMargin = (int) viewPosInfo.marginInfo.rightMargin;
            lp.bottomMargin = (int) viewPosInfo.marginInfo.bottomMargin;

            if (lp.rightMargin != 0) {
                lp.gravity = Gravity.RIGHT;
            } else {
                lp.gravity = Gravity.LEFT;
            }

            if (lp.bottomMargin != 0) {
                lp.gravity |= Gravity.BOTTOM;
            } else {
                lp.gravity |= Gravity.TOP;
            }
            addView(view, lp);
        }
    }

    /**
     * 绘制高亮区域
     */
    private void buildMask() {
        mMaskBitmap = Bitmap.createBitmap(getMeasuredWidth(),
                getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mMaskBitmap);
        canvas.drawColor(maskColor);
        mPaint.setXfermode(MODE_DST_OUT);
        mPaint.setColor(Color.parseColor("#00000000"));
        if (isBlur)
            mPaint.setMaskFilter(new BlurMaskFilter(this.blurSize,
                    BlurMaskFilter.Blur.SOLID));
        mHighLight.updateInfo();

        for (HighLight.ViewPosInfo viewPosInfo : mViewRects) {
            if (viewPosInfo.hightLightShape != null) {

                switch (viewPosInfo.hightLightShape) {
                    case CIRCULAR:// 圆形

                        float width = viewPosInfo.rectF.width();
                        float height = viewPosInfo.rectF.height();
                        float circle_center1;
                        float circle_center2;
                        double radius = Math.sqrt(Math.pow(width / 2, 2)
                                + Math.pow(height / 2, 2));
                        circle_center1 = width / 2;
                        circle_center2 = height / 2;
                        canvas.drawCircle(viewPosInfo.rectF.right - circle_center1,
                                viewPosInfo.rectF.bottom - circle_center2,
                                (int) radius, mPaint);

                        if (isNeedBorder)
                            drawCircleBorder(canvas, viewPosInfo, circle_center1, circle_center2, (int) radius);

                        break;

                    case RECTANGULAR:
                        canvas.drawRoundRect(viewPosInfo.rectF, this.radius,
                                this.radius, mPaint);
                        if (isNeedBorder)
                            drawRectBorder(canvas, viewPosInfo);
                        break;
                    default:

                        break;
                }
            } else {
                canvas.drawRoundRect(viewPosInfo.rectF, this.radius,
                        this.radius, mPaint);

                if (isNeedBorder)
                    drawRectBorder(canvas, viewPosInfo);
            }

        }
    }

    /**
     * 绘制圆形边框
     *
     * @param canvas
     * @param viewPosInfo
     * @param circle_center1
     * @param circle_center2
     * @param radius
     */
    private void drawCircleBorder(Canvas canvas, HighLight.ViewPosInfo viewPosInfo, float circle_center1, float circle_center2, int radius) {
        Paint paint = new Paint();
        paint.reset();
        if (this.borderLineType == HighLight.BorderLineType.DASH_LINE) {
            DashPathEffect pathEffect = new DashPathEffect(intervals, this.phase);
            paint.setPathEffect(pathEffect);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dip2px(borderWidth));
        paint.setAntiAlias(true);
        paint.setColor(borderColor);
        Path path = new Path();
        path.addCircle(viewPosInfo.rectF.right - circle_center1,
                viewPosInfo.rectF.bottom - circle_center2,
                radius, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制矩形边框
     *
     * @param canvas
     * @param viewPosInfo
     */
    private void drawRectBorder(Canvas canvas, HighLight.ViewPosInfo viewPosInfo) {
        Paint paint = new Paint();
        paint.reset();
        if (this.borderLineType == HighLight.BorderLineType.DASH_LINE) {
            DashPathEffect pathEffect = new DashPathEffect(intervals, this.phase);
            paint.setPathEffect(pathEffect);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dip2px(borderWidth));
        paint.setAntiAlias(true);
        paint.setColor(borderColor);
        Path path = new Path();
        path.addRect(viewPosInfo.rectF, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),//
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            buildMask();
            updateTipPos();
        }

    }

    private void updateTipPos() {
        for (int i = 0, n = getChildCount(); i < n; i++) {
            View view = getChildAt(i);
            HighLight.ViewPosInfo viewPosInfo = mViewRects.get(i);

            LayoutParams lp = buildTipLayoutParams(view, viewPosInfo);
            if (lp == null)
                continue;
            view.setLayoutParams(lp);
        }
    }

    @Nullable
    private LayoutParams buildTipLayoutParams(View view,
                                              HighLight.ViewPosInfo viewPosInfo) {
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        if (lp.leftMargin == (int) viewPosInfo.marginInfo.leftMargin
                && lp.topMargin == (int) viewPosInfo.marginInfo.topMargin
                && lp.rightMargin == (int) viewPosInfo.marginInfo.rightMargin
                && lp.bottomMargin == (int) viewPosInfo.marginInfo.bottomMargin)
            return null;

        lp.leftMargin = (int) viewPosInfo.marginInfo.leftMargin;
        lp.topMargin = (int) viewPosInfo.marginInfo.topMargin;
        lp.rightMargin = (int) viewPosInfo.marginInfo.rightMargin;
        lp.bottomMargin = (int) viewPosInfo.marginInfo.bottomMargin;

        if (lp.rightMargin != 0) {
            lp.gravity = Gravity.RIGHT;
        } else {
            lp.gravity = Gravity.LEFT;
        }

        if (lp.bottomMargin != 0) {
            lp.gravity |= Gravity.BOTTOM;
        } else {
            lp.gravity |= Gravity.TOP;
        }
        return lp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mMaskBitmap, 0, 0, null);
        super.onDraw(canvas);
    }
}
