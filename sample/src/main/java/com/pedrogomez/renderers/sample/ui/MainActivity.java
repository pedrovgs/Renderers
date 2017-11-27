package com.pedrogomez.renderers.sample.ui;

import android.content.Intent;
import android.os.Bundle;

import com.pedrogomez.renderers.sample.R;

import butterknife.OnClick;

/**
 * ListViewActivity for the Renderers demo.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class MainActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    super.onCreate(savedInstanceState);
  }

  @OnClick(R.id.bt_open_lv_sample) public void openListViewSample() {
    open(ListViewActivity.class);
  }

  @OnClick(R.id.bt_open_rv_sample) public void openRecyclerViewSample() {
    open(RecyclerViewActivity.class);
  }

  @OnClick(R.id.bt_open_vp_sample) public void openViewPagerSample() {
    open(ViewPagerActivity.class);
  }

  private void open(Class activity) {
    Intent intent = new Intent(this, activity);
    startActivity(intent);
  }
}
