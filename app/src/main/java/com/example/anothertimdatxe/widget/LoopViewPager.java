package com.example.anothertimdatxe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class LoopViewPager extends ViewPager {
    private static final boolean DEFAULT_BOUNDARY_CASHING = false;
    private static final boolean DEFAULT_BOUNDARY_LOOPING = true;

    private LoopPagerAdapterWrapper mAdapter;
    private boolean mBoundaryCaching = DEFAULT_BOUNDARY_CASHING;
    private boolean mBoundaryLooping = DEFAULT_BOUNDARY_LOOPING;
    private List<OnPageChangeListener> mOnPageChangeListeners;



    @Override
    public void setAdapter(PagerAdapter adapter) {
        mAdapter = new LoopPagerAdapterWrapper(adapter);
        mAdapter.setBoundaryCaching(mBoundaryCaching);
        mAdapter.setBoundaryLooping(mBoundaryLooping);
        super.setAdapter(mAdapter);
        setCurrentItem(0, false);
    }

    @Override
    public PagerAdapter getAdapter() {
        return mAdapter != null ? mAdapter.getRealAdapter() : null;
    }

    @Override
    public int getCurrentItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        int realItem = mAdapter.toInnerPosition(item);
        super.setCurrentItem(realItem, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
    }

    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }

    @Override
    public void removeOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    @Override
    public void clearOnPageChangeListeners() {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.clear();
        }
    }

    public LoopViewPager(Context context) {
        super(context);
        init(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        super.removeOnPageChangeListener(onPageChangeListener);
        super.addOnPageChangeListener(onPageChangeListener);
    }

    private final OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousOffset = -1;
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {

            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;

                if (mOnPageChangeListeners != null) {
                    for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                        OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                        if (listener != null) {
                            listener.onPageSelected(realPosition);
                        }
                    }
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realPosition = position;
            if (mAdapter != null) {
                realPosition = mAdapter.toRealPosition(position);

                if (positionOffset == 0 && mPreviousOffset == 0 && (position == 0
                        || position == mAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }

            mPreviousOffset = positionOffset;

            if (mOnPageChangeListeners != null) {
                for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        if (realPosition != mAdapter.getRealCount() - 1) {
                            listener.onPageScrolled(realPosition, positionOffset,
                                    positionOffsetPixels);
                        } else {
                            if (positionOffset > .5) {
                                listener.onPageScrolled(0, 0, 0);
                            } else {
                                listener.onPageScrolled(realPosition, 0, 0);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mAdapter != null) {
                int position = LoopViewPager.super.getCurrentItem();
                int realPosition = mAdapter.toRealPosition(position);
                if (state == ViewPager.SCROLL_STATE_IDLE && (position == 0
                        || position == mAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }

            if (mOnPageChangeListeners != null) {
                for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                    ViewPager.OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        listener.onPageScrollStateChanged(state);
                    }
                }
            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }

        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
