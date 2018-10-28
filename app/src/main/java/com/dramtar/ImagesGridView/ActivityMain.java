package com.dramtar.ImagesGridView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.dramtar.library.ImagesGridView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dramtar on 28.10.2018
 */
public class ActivityMain extends AppCompatActivity {

    private final static Object[] mIds = {"https://cdn.pixabay.com/photo/2018/10/21/21/28/autumn-3763897_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/10/18/19/02/woman-3757184_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/10/18/23/53/cactus-3757657_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/10/19/20/51/walnut-3759573_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/10/14/12/16/lago-federa-3746335_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/10/05/14/39/sun-3726030_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/10/14/13/01/background-3746423_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/10/12/22/08/flamingo-3743094_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/10/11/23/08/hahn-3741129_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/09/06/23/37/hydrangea-3659614_1280.jpg",
            "https://cdn.pixabay.com/photo/2018/10/07/10/24/sail-3729599_1280.jpg"};
    @BindView(R.id.grid)
    ImagesGridView mGridView;
    private Unbinder mUnBinder;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.main_activity);
        mUnBinder = ButterKnife.bind(this);
        List mSList = Arrays.asList(mIds);
        mGridView.initFromList(mSList);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }
}
