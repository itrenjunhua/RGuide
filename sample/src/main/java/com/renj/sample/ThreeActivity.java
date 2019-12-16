package com.renj.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.renj.guide.RGuideViewManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        btShow = findViewById(R.id.bt_show);

        addViewToLayout();

        findViewById(R.id.bt_back).setOnClickListener((v) -> {
            // 注意，如果未完成显示的情况下，并且后面也不需要再次显示了，
            // 那么必须调用以下方法方法移除已添加的遮罩层，否则将出现未知效果
            // RGuideViewManager.getInstance().skipAllCoverView();
            // 仔细查看 removeCoverView(RCoverViewParams, View, boolean) 方法注释
            if (!showFinish)
                RGuideViewManager.getInstance().skipAllCoverView();
            finish();
        });
        btShow.setOnClickListener((v) -> {
            if (showFinish) {
                // 显示完成了，重新显示
                addViewToLayout();
            } else {
                // 未显示完成，继续显示
                RGuideViewManager.getInstance().showCoverView();
            }
        });
    }

    private void addViewToLayout() {
        RCoverViewParams rCoverViewParams1 = RCoverViewParams.create(this)
                .setCoverLayoutId(R.layout.layout_three1)
                .setOnCoverViewListener((rCoverViewParams, decorLayoutView) -> {
                    Button btSkip = decorLayoutView.findViewById(R.id.tv_click_skip);
                    Button btRemove = decorLayoutView.findViewById(R.id.tv_click_remove);

                    // 移除自己并跳过后面的遮罩
                    btSkip.setOnClickListener(v -> {
                        showFinish = true;
                        btShow.setText("重新显示");
                        RGuideViewManager.getInstance().removeCoverView(rCoverViewParams, decorLayoutView);
                        // 上面一句相当于以下两句
                        // RGuideViewManager.getInstance().removeCoverView(rCoverViewParams, decorLayoutView,false);
                        // RGuideViewManager.getInstance().skipAllCoverView();
                    });

                    // 只移除自己
                    btRemove.setOnClickListener((v) -> {
                        showFinish = false;
                        btShow.setText("继续显示");
                        // 注意，仔细查看 removeCoverView(RCoverViewParams, View, boolean) 方法注释
                        RGuideViewManager.getInstance().removeCoverView(rCoverViewParams, decorLayoutView, false);
                    });
                })
                .setOnDecorClickListener(() ->
                        Toast.makeText(ThreeActivity.this, "覆盖层1被点击了", Toast.LENGTH_SHORT).show()
                );


        RCoverViewParams rCoverViewParams2 = RCoverViewParams.create(this)
                .setCoverLayoutId(R.layout.layout_three2)
                .setOnDecorClickListener(() -> {
                    showFinish = true;
                    btShow.setText("重新显示");
                    Toast.makeText(ThreeActivity.this, "覆盖层2被点击了", Toast.LENGTH_SHORT).show();
                });
        RGuideViewManager.getInstance()
                .addCoverView(rCoverViewParams1)
                .addCoverView(rCoverViewParams2)
                .showCoverView();
    }
}
