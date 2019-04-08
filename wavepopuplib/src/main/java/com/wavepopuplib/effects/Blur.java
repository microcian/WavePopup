package com.wavepopuplib.effects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

class Blur {

    private static final String TAG = Blur.class.getSimpleName();

    @SuppressLint("NewApi")
    static Bitmap fastBlur(Context context, Bitmap sentBitmap, int radius) {

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        final RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius /* e.g. 3.f */);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);
        return bitmap;
    }

    static Bitmap convertFromView(View view) {
        Bitmap bitmap = null;
        Rect rect = new Rect();
        view.getDrawingRect(rect);
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        bitmap = view.getDrawingCache(true);

        if (bitmap == null) {
            view.measure(View.MeasureSpec.makeMeasureSpec(rect.width(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(rect.height(), View.MeasureSpec.EXACTLY)
            );
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.destroyDrawingCache();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache(true);
            bitmap = view.getDrawingCache(true);
        }

        return bitmap;
    }
}
