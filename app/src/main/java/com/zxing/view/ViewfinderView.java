/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zxing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.zimi.zimixing.R;
import com.zimi.zimixing.utils.ScreenUtil;
import com.zxing.camera.CameraManager;

import java.util.Collection;
import java.util.HashSet;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial transparency outside it, as well as the laser
 * scanner animation and result points.
 */
@SuppressLint("DrawAllocation")
public final class ViewfinderView extends View {

    /** 点点的个�? */
    private static final int RESULT_POINT_SIZE = 5;
    /** 刷新界面的时�? */
    private static final long ANIMATION_DELAY = 10L;
    // /** 透明�?40% */
    // private static final int OPAQUE = 0xff;
    /** 四个绿色边角对应的长�? */
    private int ScreenRate;
    /** 四个绿色边角对应的宽�? */
    private static final int CORNER_WIDTH = 8;
    /** 扫描框中的中间线的宽�? */
    public static final int MIDDLE_LINE_WIDTH = 6;
    /** 扫描框中的中间线的与扫描框左右的间隙 */
    public static final int MIDDLE_LINE_PADDING = 5;
    /** 中间那条线每次刷新移动的距离 */
    private static final int SPEEN_DISTANCE = 3;
    /** 手机的屏幕密�? */
    // private static float density;
    /** 字体大小 */
    private static final int TEXT_SIZE = 13;
    /** 字体距离扫描框下面的距离 */
    private static final int TEXT_PADDING_TOP = 25;
    /** 画笔对象的引�? */
    private Paint paint;
    /** 中间滑动线的�?顶端位置 */
    private int slideTop;
    /** 中间滑动线的�?底端位置 */
    public int slideBottom;
    /** 将扫描的二维码拍下来，这里没有这个功能，暂时不�?�虑 */
    private Bitmap resultBitmap;
    /** 大背景颜色 */
    private int maskColor;
    // /** 扫描过程中 的黄点 */
    // public final int resultPointColor;
    private Collection<ResultPoint> possibleResultPoints;
    public Collection<ResultPoint> lastPossibleResultPoints;
    /** 是否为第一次画框 false==是 */
    private boolean isFirst;
    /** 蓝色框距离扫描框的位置大小 */
    private int margin = 100;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // density = context.getResources().getDisplayMetrics().density;
        // 将像素转换成dp
        ScreenRate = ScreenUtil.dip2px(20);
        margin = ScreenUtil.dip2px(10);

        paint = new Paint();

        maskColor = getResources().getColor(R.color.black_40);
        // resultPointColor = resources.getColor(R.color.scan_result_points);
        possibleResultPoints = new HashSet<ResultPoint>(RESULT_POINT_SIZE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 中间的扫描框，你要修改扫描框的大小，去CameraManager里面修改(CameraManager也可以修改扫描宽的位置)
        Rect frame = CameraManager.get().getFramingRect();
        if (frame == null) {
            return;
        }

        // 初始化中间线滑动的最上边和最下边
        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top;
            slideBottom = frame.bottom;
        }

        // 获取屏幕的宽和高
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        paint.setColor(maskColor);

        // 画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
        // 扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
        canvas.drawRect(0, 0, width, frame.top + CORNER_WIDTH, paint);
        canvas.drawRect(0, frame.top + CORNER_WIDTH, frame.left + CORNER_WIDTH, frame.bottom - CORNER_WIDTH, paint);
        canvas.drawRect(frame.right - CORNER_WIDTH, frame.top + CORNER_WIDTH, width + CORNER_WIDTH, frame.bottom - CORNER_WIDTH, paint);
        canvas.drawRect(0, frame.bottom - CORNER_WIDTH, width, height, paint);

        if (resultBitmap != null) {
            drawBitmap(canvas, frame);
        } else {

            // 绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
            slideTop += SPEEN_DISTANCE;
            if (slideTop >= frame.bottom) {
                slideTop = frame.top;
            }
            Rect lineRect = new Rect();
            lineRect.left = frame.left;
            lineRect.right = frame.right;
            lineRect.top = slideTop;
            lineRect.bottom = slideTop + 18;
            canvas.drawBitmap(((BitmapDrawable) (getResources().getDrawable(R.drawable.img_scan_line))).getBitmap(), null, lineRect, paint);

            // 画扫描框下面的字
            paint.setColor(0xff5c5c5c);
            paint.setTextSize(ScreenUtil.sp2px(TEXT_SIZE));
            // paint.setAlpha(0x40);
            paint.setTypeface(Typeface.create("System", Typeface.BOLD));
            String text = getResources().getString(R.string.scan_text_tips);
            float textWidth = paint.measureText(text);
            canvas.drawText(text, (width - textWidth) / 2, (float) (frame.bottom + (float) ScreenUtil.dip2px(TEXT_PADDING_TOP)), paint);

            // 只刷新扫描框的内容，其他地方不刷�?
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
        }

        // 画扫描框边上的角，�?�共8个部�?
        paint.setColor(Color.BLACK);
        canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate, frame.top + CORNER_WIDTH, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH, frame.top + ScreenRate, paint);
        canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right, frame.top + CORNER_WIDTH, paint);
        canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right, frame.top + ScreenRate, paint);
        canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left + ScreenRate, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - ScreenRate, frame.left + CORNER_WIDTH, frame.bottom, paint);
        canvas.drawRect(frame.right - ScreenRate, frame.bottom - CORNER_WIDTH, frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom - ScreenRate, frame.right, frame.bottom, paint);

        //        // 新建一只画笔，并设置为绿色属性
        //        Paint newPaint = new Paint();
        //        newPaint.setStyle(Paint.Style.STROKE);
        //        newPaint.setStrokeWidth(13);
        //        // 新建矩形r2
        //        RectF r2 = new RectF();
        //        r2.left = frame.left - margin;
        //        r2.right = frame.right + margin;
        //        r2.top = frame.top - margin;
        //        r2.bottom = frame.bottom + margin;
        //
        //        // 画出圆角矩形r2
        //        //newPaint.setColor(getResources().getColor(R.color.title_bg_color));
        //        newPaint.setColor(maskColor);
        //        canvas.drawRoundRect(r2, 40, 40, newPaint);
    }

    /**
     * 画图标
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-12,下午12:57:45
     * <br> UpdateTime: 2016-1-12,下午12:57:45
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param canvas
     * @param frame
     */
    private void drawBitmap(Canvas canvas, Rect frame) {

        Rect newDst = new Rect(// 画布区域
                frame.left + CORNER_WIDTH// left
                , frame.top + CORNER_WIDTH // top
                , frame.right - CORNER_WIDTH// ritht
                , frame.bottom - CORNER_WIDTH // bottom
        );

        // 绘制压缩图
        canvas.drawBitmap(resultBitmap, null, newDst, null);
    }

    /**
     * 去除取景框中的bitmap
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-29,下午2:36:27
     * <br> UpdateTime: 2016-1-29,下午2:36:27
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live scanning display.
     *
     * @param barcode
     *         An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }

}