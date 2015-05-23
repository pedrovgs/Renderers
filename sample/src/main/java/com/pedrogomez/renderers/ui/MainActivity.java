package com.pedrogomez.renderers.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.pedrogomez.renderers.R;

/**
 * ListViewActivity for the Renderers demo.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class MainActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
  }

  @OnClick(R.id.bt_open_lv_sample) public void openListViewSample() {
    open(ListViewActivity.class);
  }

  @OnClick(R.id.bt_open_rv_sample) public void openRecyclerViewSample() {
    open(RecyclerViewActivity.class);
  }

  private void open(Class activity) {
    Intent intent = new Intent(this, activity);
    startActivity(intent);
  }
}
