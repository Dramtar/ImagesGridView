package com.dramtar.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dramtar on 28.10.2018
 */
public class ImagesGridView extends LinearLayout {
    private int mNumColumns;
    private Context mContext;
    private RequestManager mManager;

    private LayoutParams mImageViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);

    private List<ImageView> mImageViews = new ArrayList<>();

    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE};

    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;

    public ImagesGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImagesGridView, 0, 0);
        try {
            int mHorizontalSpacing = ta.getDimensionPixelSize(R.styleable.ImagesGridView_android_horizontalSpacing, -1);
            int mVerticalSpacing = ta.getDimensionPixelSize(R.styleable.ImagesGridView_android_verticalSpacing, -1);

            mImageViewLayoutParams.setMargins(mVerticalSpacing / 2, mHorizontalSpacing / 2, mVerticalSpacing / 2, mHorizontalSpacing / 2);

            final int index = ta.getInt(R.styleable.ImagesGridView_android_scaleType, -1);
            if (index >= 0) {
                mScaleType = sScaleTypeArray[index];
            }

            mNumColumns = ta.getInt(R.styleable.ImagesGridView_android_numColumns, 0);

        } finally {
            ta.recycle();
        }

        mContext = context;
        mManager = Glide.with(mContext.getApplicationContext());
    }

    public ImagesGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mManager = Glide.with(mContext.getApplicationContext());
    }

    public ImagesGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mManager = Glide.with(mContext.getApplicationContext());
    }


    public void initFromList(@NonNull List items) {
        setOrientation(VERTICAL);
        int size = items.size();

        if (size == 0) {
            return;
        } else if (size == 1) {
            addView(genImageView(items.get(0)));
            return;
        }

        int numRows;
        if (mNumColumns == 0) {
            mNumColumns = (int) Math.floor(Math.sqrt(size));
            numRows = (int) Math.ceil(Math.sqrt(size));
        } else {
            numRows = (int) Math.ceil((double) size / mNumColumns);
        }

        int fromIndex = 0;

        List tempList;
        for (int i = 1; i < numRows + 1; i++) {
            int toIndex = mNumColumns * i;
            tempList = items.subList(fromIndex, toIndex < size ? toIndex : size);
            fromIndex = toIndex;
            addRow(tempList);
        }
    }

    public void setNumColumns(int numColumns) {
        mNumColumns = numColumns;
    }

    public void setImageViewLayoutParams(LayoutParams mImageViewLayoutParams) {
        mImageViewLayoutParams.weight = 1;
        this.mImageViewLayoutParams = mImageViewLayoutParams;
    }

    public ImageView getImageViewByIndex(int index) {
        return mImageViews.get(index);
    }

    public void setScaleType(ImageView.ScaleType mScaleType) {
        this.mScaleType = mScaleType;
    }

    private void addRow(List items) {
        LinearLayout rowLayout = new LinearLayout(mContext);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < items.size(); i++) {
            Object item = items.get(i);
            ImageView imageView = genImageView(item);
            rowLayout.addView(imageView);
            mImageViews.add(imageView);
        }

        addView(rowLayout);
    }

    private void loadImages(ImageView view, String url) {
        //TODO made setter for error glide drawable
        mManager.load(url).into(view);
    }

    private ImageView genImageView(Object item) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(mImageViewLayoutParams);
        imageView.setScaleType(mScaleType);
        if (item instanceof Integer) {
            imageView.setImageResource((int) item);
        } else if (item instanceof String) {
            loadImages(imageView, (String) item);
        } else if (item instanceof Drawable) {
            imageView.setImageDrawable((Drawable) item);
        }
        return imageView;
    }
}
