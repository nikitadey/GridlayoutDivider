package com.sourcecode.panchakanya.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * ItemDecoration implementation that applies and inset margin
 * around each child of the RecyclerView. It also draws item dividers
 * that are expected from a vertical list implementation, such as
 * ListView.
 */
public class GridDividerDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = { android.R.attr.listDivider };

    private Drawable mDividerHorizontalLeft,mDividerHorizontalRight, mDividerVerticalTop,mDividerVerticalBottom,mdividerNormal;
    private int mInsets;
    private int spanCount;

    public GridDividerDecoration(Context context,int spanCount,int drawableHorizontalLeft,int drawableHorizontalRight,int drawableVerticalTop,int drawableVerticalBottom,int normalDrawable) {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
//        mDividerHorizontalLeft = a.getDrawable(0);
        this.spanCount=spanCount;
        mDividerHorizontalLeft = ContextCompat.getDrawable(context,drawableHorizontalLeft);
        mDividerHorizontalRight= ContextCompat.getDrawable(context,drawableHorizontalRight);
        mDividerVerticalTop =ContextCompat.getDrawable(context,drawableVerticalTop);
        mDividerVerticalBottom=ContextCompat.getDrawable(context,drawableVerticalBottom);
        mdividerNormal=ContextCompat.getDrawable(context,normalDrawable);

        a.recycle();

        mInsets = 2;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    /** Draw dividers at each expected grid interval */
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        if (parent.getChildCount() == 0) return;

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();

            final int left = child.getLeft() - params.leftMargin - mInsets;
            final int right = child.getRight() + params.rightMargin + mInsets;
            final int top = child.getBottom() + params.bottomMargin + mInsets;


            if(i>0 &&i%spanCount>0&&i%spanCount<spanCount-1){
                final int bottom = top + mdividerNormal.getIntrinsicHeight();
                mdividerNormal.setBounds(left, top, right, bottom);
                mdividerNormal.draw(c);

            }
            else if(i>0&& i%spanCount==spanCount-1){

                final int bottom = top + mDividerHorizontalRight.getIntrinsicHeight();
                mDividerHorizontalRight.setBounds(left, top, right, bottom);
                mDividerHorizontalRight.draw(c);
            }
            else {

                final int bottom = top + mDividerHorizontalLeft.getIntrinsicHeight();
                mDividerHorizontalLeft.setBounds(left, top, right, bottom);
                mDividerHorizontalLeft.draw(c);
            }

        }
    }

    /** Draw dividers to the right of each child view */
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();

            final int left = child.getRight() + params.rightMargin + mInsets;

            final int top = child.getTop() - params.topMargin - mInsets;
            final int bottom = child.getBottom() + params.bottomMargin + mInsets;

            if(i<spanCount){
                final int right = left + mDividerVerticalTop.getIntrinsicWidth();
                mDividerVerticalTop.setBounds(left, top, right, bottom);
                mDividerVerticalTop.draw(c);
            }
            else if(i>=childCount-spanCount  &&( (i% spanCount<=childCount%spanCount && childCount%spanCount!=0) || (i% spanCount>=childCount%spanCount && childCount%spanCount==0))){

                final int right = left + mDividerVerticalBottom.getIntrinsicWidth();
                mDividerVerticalBottom.setBounds(left, top, right, bottom);
                mDividerVerticalBottom.draw(c);
            }
            else{
                final int right = left + mdividerNormal.getIntrinsicWidth();
                mdividerNormal.setBounds(left, top, right, bottom);
                mdividerNormal.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //We can supply forced insets for each item view here in the Rect
        outRect.set(mInsets, mInsets, mInsets, mInsets);
    }
}