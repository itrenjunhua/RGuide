package com.renj.guide.highlight;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.renj.guide.callback.OnDecorScrollListener;
import com.renj.guide.highlight.type.BorderLineType;
import com.renj.guide.utils.RGuideUtils;

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
    private int scaledTouchSlop;

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
        scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        addViewForTip();
    }

    /**
     * 将需要高亮的View增加到帧布局上方
     */
    private void addViewForTip() {
        for (RHighLightViewParams rHighLightViewParams : rHighLightViewParamsList) {
            View decorLayoutView;
            if (rHighLightViewParams.decorLayoutView != null) {
                decorLayoutView = rHighLightViewParams.decorLayoutView;
            } else {
                decorLayoutView = mInflater.inflate(rHighLightViewParams.decorLayoutId, this, false);
            }
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
            if (rHighLightViewParams.onHLDecorInflateListener != null)
                rHighLightViewParams.onHLDecorInflateListener.onInflateFinish(decorLayoutView);
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
        // 增加背景高斯模糊效果
        if (rHighLightPageParams.maskIsBlur && rHighLightPageParams.maskBlurRadius > 0) {
            mMaskBitmap = RGuideUtils.viewToBlurBitmap(getContext(), rHighLightPageParams.anchor, rHighLightPageParams.maskBlurRadius);
        }
        Canvas canvas = new Canvas(mMaskBitmap);
        mPaint.setXfermode(MODE_DST_OUT);
        canvas.drawColor(rHighLightPageParams.maskColor);

        for (RHighLightViewParams rHighLightViewParams : rHighLightViewParamsList) {
            // 如果需要绘制模糊边界，先绘制模糊边界
            if (rHighLightViewParams.blurShow) {
                mPaint.setColor(rHighLightViewParams.blurColor);
                mPaint.setMaskFilter(new BlurMaskFilter(dip2px(rHighLightViewParams.blurSize), BlurMaskFilter.Blur.SOLID));
                reallyHighShape(canvas, rHighLightViewParams);
            }
            mPaint.setColor(Color.TRANSPARENT);
            mPaint.setMaskFilter(null);
            reallyHighShape(canvas, rHighLightViewParams);

        }
    }

    /**
     * 真正绘制高亮
     */
    private void reallyHighShape(Canvas canvas, RHighLightViewParams rHighLightViewParams) {
        RectF drawRectF = new RectF(rHighLightViewParams.rectF.left - dip2px(rHighLightViewParams.highMarginRectRectF.left),
                rHighLightViewParams.rectF.top - dip2px(rHighLightViewParams.highMarginRectRectF.top),
                rHighLightViewParams.rectF.right + dip2px(rHighLightViewParams.highMarginRectRectF.right),
                rHighLightViewParams.rectF.bottom + dip2px(rHighLightViewParams.highMarginRectRectF.bottom));

        if (rHighLightViewParams.highLightShape != null) {
            switch (rHighLightViewParams.highLightShape) {
                case CIRCULAR:// 圆形
                    float width = drawRectF.width();
                    float height = drawRectF.height();
                    float centerX = drawRectF.right - width / 2;
                    float centerY = drawRectF.bottom - height / 2;
                    float radius = (float) Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));
                    canvas.drawCircle(centerX, centerY, radius, mPaint);

                    if (rHighLightViewParams.borderShow)
                        drawCircleBorder(rHighLightViewParams, canvas, centerX, centerY, radius);

                    break;
                case RECTANGULAR:
                    canvas.drawRoundRect(drawRectF, dip2px(rHighLightViewParams.radius), dip2px(rHighLightViewParams.radius), mPaint);
                    if (rHighLightViewParams.borderShow)
                        drawRectBorder(rHighLightViewParams, canvas);
                    break;
            }
        } else {
            canvas.drawRoundRect(drawRectF, dip2px(rHighLightViewParams.radius), dip2px(rHighLightViewParams.radius), mPaint);
            if (rHighLightViewParams.borderShow)
                drawRectBorder(rHighLightViewParams, canvas);
        }
    }

    /**
     * 绘制圆形边框
     */
    private void drawCircleBorder(RHighLightViewParams rHighLightViewParams, Canvas canvas, float centerX, float centerY, float radius) {
        Paint paint = new Paint();
        paint.reset();
        if (rHighLightViewParams.borderLineType == BorderLineType.DASH_LINE) {
            DashPathEffect pathEffect = new DashPathEffect(rHighLightViewParams.intervals, 1);
            paint.setPathEffect(pathEffect);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dip2px(rHighLightViewParams.borderWidth));
        paint.setAntiAlias(true);
        if (rHighLightViewParams.borderMargin != 0) {
            radius = radius + dip2px(rHighLightViewParams.borderMargin);
        }
        // Shader优先级更高
        if (rHighLightViewParams.onBorderShader != null) {
            RectF rectF = rHighLightViewParams.rectF;
            if (rHighLightViewParams.borderMargin != 0) {
                int borderMargin = dip2px(rHighLightViewParams.borderMargin);
                rectF.inset(-borderMargin, -borderMargin);
            }
            Shader shader = rHighLightViewParams.onBorderShader.createShader(rectF);
            // Shader优先级更高
            if (shader != null) {
                paint.setShader(shader);
            } else {
                paint.setColor(rHighLightViewParams.borderColor);
            }
        } else {
            paint.setColor(rHighLightViewParams.borderColor);
        }
        canvas.drawCircle(centerX, centerY, radius, paint);
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

        RectF rectF = rHighLightViewParams.rectF;
        if (rHighLightViewParams.borderMargin != 0) {
            int borderMargin = dip2px(rHighLightViewParams.borderMargin);
            rectF.inset(-borderMargin, -borderMargin);
        }
        // Shader优先级更高
        if (rHighLightViewParams.onBorderShader != null) {
            Shader shader = rHighLightViewParams.onBorderShader.createShader(rectF);
            // Shader优先级更高
            if (shader != null) {
                paint.setShader(shader);
            } else {
                paint.setColor(rHighLightViewParams.borderColor);
            }
        } else {
            paint.setColor(rHighLightViewParams.borderColor);
        }
        canvas.drawRoundRect(rectF, dip2px(rHighLightViewParams.radius), dip2px(rHighLightViewParams.radius), paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mMaskBitmap, 0, 0, null);
        super.onDraw(canvas);
    }

    /**
     * 滑动坐标
     */
    private float downX, downY;
    private float moveX, moveY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moveY = event.getY();
                for (RHighLightViewParams rHighLightViewParams : rHighLightViewParamsList) {
                    if (rHighLightViewParams.onDecorScrollListener != null) {
                        float offsetX = Math.abs(moveX - downX);
                        float offsetY = Math.abs(moveY - downY);
                        if ((offsetX > offsetY) && (offsetX > scaledTouchSlop)) {
                            int axis = (moveX > downX) ? OnDecorScrollListener.AXIS_POSITIVE : OnDecorScrollListener.AXIS_NEGATIVE;
                            rHighLightViewParams.onDecorScrollListener.onScroll(rHighLightViewParams.decorLayoutView,
                                    OnDecorScrollListener.SCROLL_HORIZONTAL, axis);
                        } else if (offsetY > scaledTouchSlop) {
                            int axis = (moveY > downY) ? OnDecorScrollListener.AXIS_POSITIVE : OnDecorScrollListener.AXIS_NEGATIVE;
                            rHighLightViewParams.onDecorScrollListener.onScroll(rHighLightViewParams.decorLayoutView,
                                    OnDecorScrollListener.SCROLL_VERTICAL, axis);
                        }
                    }
                }
                downX = moveX;
                downY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                float upY = event.getY();

                for (RHighLightViewParams rHighLightViewParams : rHighLightViewParamsList) {
                    if (rHighLightViewParams.onHLViewClickListener != null) {
                        if (rHighLightViewParams.rectF.contains(upX, upY)) {
                            rHighLightViewParams.onHLViewClickListener.onHighLightViewClick(rHighLightViewParams.highView,
                                    rHighLightViewParams.highViewId);
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public int dip2px(float dpValue) {
        if (dpValue == 0) return 0;
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
