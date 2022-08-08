package com.renj.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.renj.guide.RGuideViewManager;
import com.renj.guide.cover.CoverViewHelp;
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
public class ThreeActivity extends AppCompatActivity {

    private Button btShow;
    private boolean showFinish = false;
    private CoverViewHelp coverViewHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        btShow = findViewById(R.id.bt_show);

        coverViewHelp = RGuideViewManager.createCoverViewHelp();
        btShow.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                addViewToLayout();
                btShow.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        findViewById(R.id.bt_back).setOnClickListener((v) -> {
            finish();
        });
        btShow.setOnClickListener((v) -> {
            if (showFinish) {
                // 显示完成了，重新显示
                addViewToLayout();
            } else {
                // 未显示完成，继续显示
                coverViewHelp.showCoverView();
            }
        });
    }

    private void addViewToLayout() {
        RCoverViewParams rCoverViewParams1 = RCoverViewParams.create(this)
                .setAutoRemoveAndShowNextView(false)
                .setCoverLayoutId(R.layout.layout_three1)
                .setOnCViewInflateListener((rCoverViewParams, coverView) -> {
                    Button btSkip = coverView.findViewById(R.id.tv_click_skip);
                    Button btRemove = coverView.findViewById(R.id.tv_click_remove);

                    // 移除自己并跳过后面的遮罩
                    btSkip.setOnClickListener(v -> {
                        showFinish = true;
                        btShow.setText("重新显示");
                        coverViewHelp.removeCoverView();
                        // 上面一句相当于以下两句
                        // coverViewHelp.removeCoverView(rCoverViewParams, decorLayoutView,false);
                        // coverViewHelp.skipAllCoverView();
                    });

                    // 只移除自己
                    btRemove.setOnClickListener((v) -> {
                        showFinish = false;
                        btShow.setText("继续显示");
                        coverViewHelp.removeCoverView(false);
                    });
                })
                .setOnDecorClickListener(() -> {
                            Toast.makeText(ThreeActivity.this, "覆盖层1被点击了", Toast.LENGTH_SHORT).show();
                            coverViewHelp.showCoverView();
                        }
                );

        RCoverViewParams rCoverViewParams2 = RCoverViewParams.create(this)
                .setCoverLayoutId(R.layout.layout_three2)
                .setMaskBlur(true, 20) // 设置背景包含高斯模糊效果
                .setOnDecorClickListener(() -> {
                    showFinish = true;
                    btShow.setText("重新显示");
                    Toast.makeText(ThreeActivity.this, "覆盖层2被点击了", Toast.LENGTH_SHORT).show();
                });
        coverViewHelp
                .addCoverView(rCoverViewParams1)
                .addCoverView(rCoverViewParams2)
                .showCoverView();
    }
}
