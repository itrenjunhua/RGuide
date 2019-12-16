package com.renj.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.renj.guide.RGuideViewManager;
import com.renj.guide.callback.OnDecorClickListener;
import com.renj.guide.cover.RCoverViewParams;


/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2016-08-03    15:01
 * <p/>
 * 描述：
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class ThreeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        addViewToLayout();
    }

    private void addViewToLayout() {
        RCoverViewParams rCoverViewParams1 = RCoverViewParams.create(this)
                .setCoverLayoutId(R.layout.layout_three1)
                .setOnCoverViewListener(new RCoverViewParams.OnCoverViewListener() {
                    @Override
                    public void onCoverView(final RCoverViewParams rCoverViewParams, final View decorLayoutView) {
                        decorLayoutView.findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RGuideViewManager.getInstance().removeCoverView(rCoverViewParams, decorLayoutView, false);
                                RGuideViewManager.getInstance().showCoverView();
                                //RGuideViewManager.getInstance().skipAllCoverView();
                            }
                        });
                    }
                })
                .setOnDecorClickListener(new OnDecorClickListener() {
                    @Override
                    public void onClick() {
                        Toast.makeText(ThreeActivity.this, "覆盖层1被点击了", Toast.LENGTH_SHORT).show();
                    }
                });
        RCoverViewParams rCoverViewParams2 = RCoverViewParams.create(this)
                .setCoverLayoutId(R.layout.layout_three2)
                .setOnDecorClickListener(new OnDecorClickListener() {
                    @Override
                    public void onClick() {
                        Toast.makeText(ThreeActivity.this, "覆盖层2被点击了", Toast.LENGTH_SHORT).show();
                    }
                });
        RGuideViewManager.getInstance()
                .addCoverView(rCoverViewParams1)
                .addCoverView(rCoverViewParams2)
                .showCoverView();
    }
}
