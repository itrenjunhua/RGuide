package com.renj.guide.utils;

import static android.renderscript.Allocation.USAGE_SCRIPT;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2022-08-08   14:03
 * <p>
 * 描述：工具类。处理 {@link View} 和 {@link Bitmap}，以及实现图片高斯模糊效果
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RGuideUtils {

    /**
     * 将 {@link View} 变为带高斯模糊效果的图片，使用 {@link RenderScript} 方式
     *
     * @param context 上下文
     * @param view    需要转换的 {@link View}
     * @param radius  模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     * @return 高斯模糊后的 {@link Bitmap}
     */
    public static Bitmap viewToBlurBitmap(Context context, View view, float radius) {
        return viewToBlurBitmap(context, view, radius, 1);
    }

    /**
     * 将 {@link View} 变为带高斯模糊效果的图片，使用 {@link RenderScript} 方式
     *
     * @param context 上下文
     * @param view    需要转换的 {@link View}
     * @param radius  模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     * @param scale   图片缩放值
     * @return 高斯模糊后的 {@link Bitmap}
     */
    public static Bitmap viewToBlurBitmap(Context context, View view, float radius, float scale) {
        return bitmapBlur(context, viewToBitmap(view), radius, scale);
    }

    /**
     * 实现高斯模糊效果，使用 {@link RenderScript} 方式
     *
     * @param context 上下文
     * @param source  原图片
     * @param radius  模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     * @return 高斯模糊后的 {@link Bitmap}
     */
    public static Bitmap bitmapBlur(Context context, Bitmap source, float radius) {
        return bitmapBlur(context, source, radius, 1);
    }


    /**
     * 实现高斯模糊效果，使用 {@link RenderScript} 方式
     *
     * @param context 上下文
     * @param source  原图片
     * @param radius  模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     * @param scale   图片缩放值
     * @return 高斯模糊后的 {@link Bitmap}
     */
    public static Bitmap bitmapBlur(Context context, Bitmap source, float radius, float scale) {
        if (source == null) {
            RGuideLog.w("source == null");
            return null;
        }

        int scaleWidth = (int) (source.getWidth() * scale);
        int scaleHeight = (int) (source.getHeight() * scale);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(source, scaleWidth,
                scaleHeight, false);

        // 创建RenderScript
        RenderScript renderScript = RenderScript.create(context);
        // 创建Allocation
        Allocation input = Allocation.createFromBitmap(renderScript, inputBitmap, Allocation.MipmapControl.MIPMAP_NONE, USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(renderScript, input.getType());

        // 创建ScriptIntrinsic
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        intrinsicBlur.setInput(input);
        intrinsicBlur.setRadius(radius);
        intrinsicBlur.forEach(output);
        output.copyTo(inputBitmap);
        source.recycle();
        renderScript.destroy();

        return inputBitmap;
    }

    /**
     * 将 {@link Drawable} 转换为 {@link Bitmap}
     *
     * @param drawable 需要转换的 {@link Drawable}
     * @return 转换后的 {@link Bitmap}
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            RGuideLog.w("drawable == null");
            return null;
        }

        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if ((drawable.getIntrinsicWidth() <= 0) || (drawable.getIntrinsicHeight() <= 0)) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将View 转换为 {@link Bitmap}
     *
     * @param view 需要转换的 {@link View}
     * @return 转后后的 {@link Bitmap}
     */
    public static Bitmap viewToBitmap(View view) {
        if (view == null) {
            RGuideLog.w("View == null");
            return null;
        }

        int width = view.getWidth();
        int height = view.getHeight();
        if (width <= 0 || height <= 0) {
            RGuideLog.w("参数View的宽或者高等于0");
            return null;
        }
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }
}
