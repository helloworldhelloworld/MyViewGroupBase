package com.xj.myapplication.myviewgroup.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xjcxy on 2015/1/30.
 */
public class CascadeLayout extends ViewGroup {
    private int mHorSpacing;
    private int mVerSpacing;
    public CascadeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Cascade_Layout);

        mHorSpacing = a.getDimensionPixelSize(R.styleable.Cascade_Layout_cascade_hor_space,
                getResources().getDimensionPixelSize(R.dimen.cascade_hor_spacing));
        mVerSpacing = a.getDimensionPixelSize(R.styleable.Cascade_Layout_cascade_ver_space,
                getResources().getDimensionPixelSize(R.dimen.cascade_ver_spacing));

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        final int count = getChildCount();
        for (int index = 0; index < count; index++)
        {
            View child = getChildAt(index);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x,lp.y,lp.x+getMeasuredWidth(),lp.y+getMeasuredHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = 0;
        int height = getPaddingTop();

        final int counts = getChildCount();
        for (int i= 0; i <counts ;i++)
        {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            width = getPaddingLeft() + mHorSpacing*i;
            lp.x = width;
            lp.y = height;
            width += getMeasuredWidth();
            height += mVerSpacing;

        }

        width += getPaddingRight();
        height += getChildAt(getChildCount()-1).getMeasuredHeight()+getPaddingBottom();
        setMeasuredDimension(resolveSize(width,widthMeasureSpec),resolveSize(height,heightMeasureSpec));
    }
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams
    {
        int x;
        int y;
        public int verticalSpacing;
        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);

            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.CascadeLayout_LayoutParams);
            try {
                verticalSpacing = a
                        .getDimensionPixelSize(
                                R.styleable.CascadeLayout_LayoutParams_layout_vertical_spacing,
                                -1);
            } finally {
                a.recycle();
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }
}
