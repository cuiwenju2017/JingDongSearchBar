package com.dy.jingdongsearchbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 参考   https://blog.csdn.net/lhy349/article/details/92573680
 */
public class MainActivity extends AppCompatActivity {
    private AnimationNestedScrollView sv_view;
    private LinearLayout ll_search;
    private LinearLayout ll_back;
    private float LL_SEARCH_MIN_TOP_MARGIN, LL_SEARCH_MAX_TOP_MARGIN, LL_SEARCH_MAX_WIDTH, LL_SEARCH_MIN_WIDTH, TV_TITLE_MAX_TOP_MARGIN;
    private ViewGroup.MarginLayoutParams searchLayoutParams, titleLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        sv_view = findViewById(R.id.search_sv_view);
        ll_search = findViewById(R.id.search_ll_search);
        ll_back = findViewById(R.id.ll_back);
        searchLayoutParams = (ViewGroup.MarginLayoutParams) ll_search.getLayoutParams();
        titleLayoutParams = (ViewGroup.MarginLayoutParams) ll_back.getLayoutParams();

        LL_SEARCH_MIN_TOP_MARGIN = dp2px(this, 4.5f);//布局关闭时顶部距离
        LL_SEARCH_MAX_TOP_MARGIN = dp2px(this, 49f);//布局默认展开时顶部距离
        LL_SEARCH_MAX_WIDTH = getScreenWidth(this) - dp2px(this, 30f);//布局默认展开时的宽度


        /**
         * 安居客效果
         */
//        LL_SEARCH_MIN_WIDTH = getScreenWidth(this) - dp2px(this, 82f);//布局关闭时的宽度

        /**
         * 京东效果
         */
        LL_SEARCH_MIN_WIDTH = getScreenWidth(this) - dp2px(this, 140f);//布局关闭时的宽度


        TV_TITLE_MAX_TOP_MARGIN = dp2px(this, 11.5f);

        sv_view.setOnAnimationScrollListener(new AnimationNestedScrollView.OnAnimationScrollChangeListener() {
            @Override
            public void onScrollChanged(float dy) {
                float searchLayoutNewTopMargin = LL_SEARCH_MAX_TOP_MARGIN - dy;
                /**
                 * 安居客效果
                 */
//                float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - dy * 1.3f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率
                /**
                 * 京东效果
                 */
                float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - dy * 3.0f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率

                float titleNewTopMargin = (float) (TV_TITLE_MAX_TOP_MARGIN - dy * 0.5);

                //处理布局的边界问题
                searchLayoutNewWidth = searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH ? LL_SEARCH_MIN_WIDTH : searchLayoutNewWidth;

                if (searchLayoutNewTopMargin < LL_SEARCH_MIN_TOP_MARGIN) {
                    searchLayoutNewTopMargin = LL_SEARCH_MIN_TOP_MARGIN;
                }

                if (searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH) {
                    searchLayoutNewWidth = LL_SEARCH_MIN_WIDTH;
                }

                float titleAlpha = 255 * titleNewTopMargin / TV_TITLE_MAX_TOP_MARGIN;
                if (titleAlpha < 0) {
                    titleAlpha = 0;
                }

                ll_back.getBackground().setAlpha((int) titleAlpha);

                titleLayoutParams.topMargin = (int) titleNewTopMargin;
                ll_back.setLayoutParams(titleLayoutParams);

                searchLayoutParams.topMargin = (int) searchLayoutNewTopMargin;
                searchLayoutParams.width = (int) searchLayoutNewWidth;
                ll_search.setLayoutParams(searchLayoutParams);
            }
        });
    }

    public static int dp2px(Context context, float dpValue) {
        if (null == context) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        if (null == context) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
