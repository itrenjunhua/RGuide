package com.renj.guide.highlight;

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

import com.renj.guide.highlight.type.BorderLineType;

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
/*public*/ class HighLightView extends FrameLayout {
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
     * 高亮页面参数
     */
    private RHighLightPageParams rHighLightPageParams;
    /**
     * 用于保存高亮View的集合，因为一个页面可能有多个高亮的地方同时显示
     */
    private List<RHighLightViewParams> rHighLightViewParamsList;
    /**
     * 打气筒
     */
    private LayoutInflater mInflater;

    public HighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
                         @NonNull List<RHighLightViewParams> rHighLightViewParamsList) {
        super(rHighLightPageParams.activity);
        this.rHighLightPageParams = rHighLightPageParams;
        this.rHighLightViewParamsList = rHighLightViewParamsList;
        this.mInflater = LayoutInflater.from(rHighLightPageParams.activity);
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
    }

    /**
     * 将需要高亮的View增加到帧布局上方
     */
    private void addViewForTip() {
        for (RHighLightViewParams rHighLightViewParams : rHighLightViewParamsList) {
            View decorLayoutView = mInflater.inflate(rHighLightViewParams.decorLayoutId, this, false);
            FrameLayout.LayoutParams lp = buildTipLayoutParams(decorLayoutView, rHighLightViewParams);

            if (lp == null)
                continue;

            lp.leftMargin = (int) rHighLightViewParams.marginInfo.leftMargin;
            lp.topMargin = (int) rHighLightViewParams.marginInfo.topMargin;
            lp.rightMargin = (int) rHighLightViewParams.marginInfo.rightMargin;
            lp.bottomMargin = (int) rHighLightViewParams.marginInfo.bottomMargin;

            if (lp.rightMargin != 0) {
                lp.gravity = Gravity.END;
            } else {
                lp.gravity = Gravity.START;
            }

            if (lp.bottomMargin != 0) {
                lp.gravity |= Gravity.BOTTOM;
            } else {
                lp.gravity |= Gravity.TOP;
            }
            addView(decorLayoutView, lp);
        }
    }

    @Nullable
    private LayoutParams buildTipLayoutParams(View view, RHighLightViewParams viewPosInfo) {
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        if (lp.leftMargin == (int) viewPosInfo.marginInfo.leftMargin
                && lp.topMargin == (int) viewPosInfo.marginInfo.topMargin
                && lp.rightMargin == (int) viewPosInfo.marginInfo.rightMargin
                && lp.bottomMargin == (int) viewPosInfo.marginInfo.bottomMargin)
            return lp;

        lp.leftMargin = (int) viewPosInfo.marginInfo.leftMargin;
        lp.topMargin = (int) viewPosInfo.marginInfo.topMargin;
        lp.rightMargin = (int) viewPosInfo.marginInfo.rightMargin;
        lp.bottomMargin = (int) viewPosInfo.marginInfo.bottomMargin;

        if (lp.rightMargin != 0) {
            lp.gravity = Gravity.END;
        } else {
            lp.gravity = Gravity.START;
        }

        if (lp.bottomMargin != 0) {
            lp.gravity |= Gravity.BOTTOM;
        } else {
            lp.gravity |= Gravity.TOP;
        }
        return lp;
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
        }
    }

    /**
     * 绘制高亮区域
     */
    private void buildMask() {
        mMaskBitmap = Bitmap.createBitmap(getMeasuredWidth(),
                getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mMaskBitmap);
        mPaint.setXfermode(MODE_DST_OUT);
        mPaint.setColor(Color.parseColor("#00000000"));
        canvas.drawColor(rHighLightPageParams.maskColor);

        for (RHighLightViewParams rHighLightViewParams : rHighLightViewParamsList) {
            if (rHighLightViewParams.blurShow)
                mPaint.setMaskFilter(new BlurMaskFilter(dip2px(rHighLightViewParams.blurSize), BlurMaskFilter.Blur.SOLID));
            else
                mPaint.setMaskFilter(null);
            if (rHighLightViewParams.highLightShape != null) {
                switch (rHighLightViewParams.highLightShape) {
                    case CIRCULAR:// 圆形
                        float width = rHighLightViewParams.rectF.width();
                        float height = rHighLightViewParams.rectF.height();
                        float circle_center1;
                        float circle_center2;
                        double radius = Math.sqrt(Math.pow(width / 2, 2)
                                + Math.pow(height / 2, 2));
                        circle_center1 = width / 2;
                        circle_center2 = height / 2;
                        canvas.drawCircle(rHighLightViewParams.rectF.right - circle_center1,
                                rHighLightViewParams.rectF.bottom - circle_center2,
                                (int) radius, mPaint);

                        if (rHighLightViewParams.borderShow)
                            drawCircleBorder(rHighLightViewParams, canvas, circle_center1, circle_center2, (int) radius);

                        break;
                    case RECTANGULAR:
                        canvas.drawRoundRect(rHighLightViewParams.rectF, dip2px(rHighLightViewParams.radius), dip2px(rHighLightViewParams.radius), mPaint);
                        if (rHighLightViewParams.borderShow)
                            drawRectBorder(rHighLightViewParams, canvas);
                        break;
                }
            } else {
                canvas.drawRoundRect(rHighLightViewParams.rectF, dip2px(rHighLightViewParams.radius), dip2px(rHighLightViewParams.radius), mPaint);
                if (rHighLightViewParams.borderShow)
                    drawRectBorder(rHighLightViewParams, canvas);
            }

        }
    }

    /**
     * 绘制圆形边框
     */
    private void drawCircleBorder(RHighLightViewParams rHighLightViewParams, Canvas canvas, float circle_center1, float circle_center2, int radius) {
        Paint paint = new Paint();
        paint.reset();
        if (rHighLightViewParams.borderLineType == BorderLineType.DASH_LINE) {
            DashPathEffect pathEffect = new DashPathEffect(rHighLightViewParams.intervals, 1);
            paint.setPathEffect(pathEffect);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dip2px(rHighLightViewParams.borderWidth));
        paint.setAntiAlias(true);
        paint.setColor(rHighLightViewParams.borderColor);
        Path path = new Path();
        path.addCircle(rHighLightViewParams.rectF.right - circle_center1,
                rHighLightViewParams.rectF.bottom - circle_center2,
                radius, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制矩形边框
     */
    private void drawRectBorder(RHighLightViewParams rHighLightViewParams, Canvas canvas) {
        Paint paint = new Paint();
        paint.reset();
        if (rHighLightViewParams.borderLineType == BorderLineType.DASH_LINE) {
            DashPathEffect pathEffect = new DashPathEffect(rHighLightViewParams.intervals, 1);
            paint.setPathEffect(pathEffect);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dip2px(rHighLightViewParams.borderWidth));
        paint.setAntiAlias(true);
        paint.setColor(rHighLightViewParams.borderColor);
        Path path = new Path();
        path.addRoundRect(rHighLightViewParams.rectF, dip2px(rHighLightViewParams.radius), dip2px(rHighLightViewParams.radius), Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mMaskBitmap, 0, 0, null);
        super.onDraw(canvas);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
