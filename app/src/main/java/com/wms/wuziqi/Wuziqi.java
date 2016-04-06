package com.wms.wuziqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by wms1993 on 2016/4/6 0006.
 */
public class Wuziqi extends View {

    /**
     * 棋子大小是mLineHeight的3/4
     */
    private static final float RADIO_BITMAP = 3 * 1.0f / 4;
    /**
     * 五个棋子相连就赢
     */
    private static final int WIN_NUM = 5;
    /**
     * 棋盘的宽度
     */
    private int mPanelWidth;
    /**
     * 棋子的高度
     */
    private float mLineHeight;
    /**
     * 棋盘线的个数
     */
    private static final int LINE_NUM = 10;
    /**
     * 绘制棋盘的画笔
     */
    private Paint mPaint;
    /**
     * 保存白棋
     */
    private ArrayList<Point> mWhitePices = new ArrayList<>();
    /**
     * 保存黑棋
     */
    private ArrayList<Point> mBlackPices = new ArrayList<>();
    /**
     * 当前是否是白棋，默认白棋先下
     */
    private boolean isWhite = true;
    /**
     * 白棋图片
     */
    private Bitmap mWhiteBitmap;
    /**
     * 黑棋图片
     */
    private Bitmap mBlackBitmap;
    /**
     * 是否是白棋胜利
     */
    private boolean isWhiteWin;
    /**
     * 游戏是否结束
     */
    private boolean isGameOver;

    public Wuziqi(Context context) {
        this(context, null);
    }

    public Wuziqi(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Wuziqi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(0X88000000);
        mPaint.setStyle(Paint.Style.FILL);

        mWhiteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int resutSize = Math.min(widthSize, heightSize);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            resutSize = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            resutSize = widthSize;
        }

        setMeasuredDimension(resutSize, resutSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBoard(canvas);
        drawGoBang(canvas);
        checkGameOver();
    }

    /**
     * 检查游戏是否结束
     */
    private void checkGameOver() {
        boolean whiteWin = checkFiveInLine(mWhitePices);
        boolean blackWin = checkFiveInLine(mBlackPices);

        if (whiteWin || blackWin) {
            isGameOver = true;

            String text;
            if (whiteWin) {
                text = "白棋胜";
            } else {
                text = "黑棋胜";
            }
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断是否五子连珠
     *
     * @param points
     * @return
     */
    private boolean checkFiveInLine(ArrayList<Point> points) {
        for (Point point : points) {
            int x = point.x;
            int y = point.y;

            boolean isWin = checkHorizontal(x, y, points);
            if (isWin) return true;
            isWin = checkVertical(x, y, points);
            if (isWin) return true;
            isWin = checkLeftLean(x, y, points);
            if (isWin) return true;
            isWin = checkRightLean(x, y, points);
            return isWin;
        }
        return false;
    }

    /**
     * 判断水平方向是否有五个棋子
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkHorizontal(int x, int y, ArrayList<Point> points) {

        int count = 1;
        //左向五个
        for (int i = 1; i < WIN_NUM; i++) {
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }

        if (count == WIN_NUM) return true;

        //右向五个
        for (int i = 1; i < WIN_NUM; i++) {
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }

        if (count == WIN_NUM) return true;

        return false;
    }

    /**
     * 判断垂直方向是否有五个棋子
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkVertical(int x, int y, ArrayList<Point> points) {

        int count = 1;
        //上向五个
        for (int i = 1; i < WIN_NUM; i++) {
            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == WIN_NUM) return true;

        //下向五个
        for (int i = 1; i < WIN_NUM; i++) {
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == WIN_NUM) return true;

        return false;
    }

    /**
     * 判断左向倾斜方向是否有五个棋子
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkLeftLean(int x, int y, ArrayList<Point> points) {

        int count = 1;
        //左下向五个
        for (int i = 1; i < WIN_NUM; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == WIN_NUM) return true;

        //右上向五个
        for (int i = 1; i < WIN_NUM; i++) {
            if (points.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == WIN_NUM) return true;

        return false;
    }

    /**
     * 判断左向倾斜方向是否有五个棋子
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkRightLean(int x, int y, ArrayList<Point> points) {

        int count = 1;
        //右下向五个
        for (int i = 1; i < WIN_NUM; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == WIN_NUM) return true;

        //右上向五个
        for (int i = 1; i < WIN_NUM; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == WIN_NUM) return true;

        return false;
    }

    /**
     * 绘制黑白棋
     *
     * @param canvas
     */
    private void drawGoBang(Canvas canvas) {
        for (Point point : mWhitePices) {
            canvas.drawBitmap(mWhiteBitmap,
                    (point.x + (1 - RADIO_BITMAP) / 2) * mLineHeight,
                    (point.y + (1 - RADIO_BITMAP) / 2) * mLineHeight,
                    null);
        }

        for (Point point : mBlackPices) {
            canvas.drawBitmap(mBlackBitmap,
                    (point.x + (1 - RADIO_BITMAP) / 2) * mLineHeight,
                    (point.y + (1 - RADIO_BITMAP) / 2) * mLineHeight,
                    null);
        }
    }

    /**
     * 绘制棋盘
     *
     * @param canvas
     */
    private void drawBoard(Canvas canvas) {
        //绘制横线
        for (int i = 0; i < LINE_NUM; i++) {
            int startX = (int) (mLineHeight / 2);
            int endX = (int) (mPanelWidth - mLineHeight / 2);
            int y = (int) ((0.5 + i) * mLineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
        }

        //绘制竖线
        for (int i = 0; i < LINE_NUM; i++) {
            int x = (int) ((0.5 + i) * mLineHeight);
            int startY = (int) (mLineHeight / 2);
            int endY = (int) (mPanelWidth - mLineHeight / 2);
            canvas.drawLine(x, startY, x, endY, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isGameOver) return false;

        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point point = getValidPoint(x, y);

            if (mWhitePices.contains(point)) {
                return false;
            }

            if (mBlackPices.contains(point)) {
                return false;
            }

            if (isWhite) {
                mWhitePices.add(point);
            } else {
                mBlackPices.add(point);
            }

            isWhite = !isWhite;
            invalidate();
        }

        return true;
    }

    /**
     * 根据x，y坐标获取当前下子点
     *
     * @param x
     * @param y
     * @return
     */
    private Point getValidPoint(int x, int y) {
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / LINE_NUM;

        mWhiteBitmap = Bitmap.createScaledBitmap(mWhiteBitmap, (int) (mLineHeight * RADIO_BITMAP), (int) (mLineHeight * RADIO_BITMAP), false);
        mBlackBitmap = Bitmap.createScaledBitmap(mBlackBitmap, (int) (mLineHeight * RADIO_BITMAP), (int) (mLineHeight * RADIO_BITMAP), false);
    }


    private static final String INSTANCE = "instance";
    private static final String INSTANCE_ISWHITE = "instance_iswhite";
    private static final String INSTANCE_BLACK_LIST = "instance_black_list";
    private static final String INSTANCE_WHITE_LIST = "instance_white_list";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putParcelableArrayList(INSTANCE_BLACK_LIST, mBlackPices);
        bundle.putParcelableArrayList(INSTANCE_WHITE_LIST, mWhitePices);
        bundle.putBoolean(INSTANCE_ISWHITE, isWhite);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            isWhite = bundle.getBoolean(INSTANCE_ISWHITE);
            mWhitePices = bundle.getParcelableArrayList(INSTANCE_WHITE_LIST);
            mBlackPices = bundle.getParcelableArrayList(INSTANCE_BLACK_LIST);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
