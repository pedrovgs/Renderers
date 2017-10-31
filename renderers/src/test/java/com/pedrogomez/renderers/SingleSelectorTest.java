package com.pedrogomez.renderers;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@Config(emulateSdk = 16) @RunWith(RobolectricTestRunner.class) public class SingleSelectorTest {

  private static final String FIRST_ITEM_ID = "1";
  private static final String SECOND_ITEM_ID = "2";
  private static final Object FIRST_ITEM = new Object();
  private static final Object SECOND_ITEM = new Object();

  private SingleSelector<Object> selector;

  @Mock private ObjectRenderer mockedFirstRenderer;
  @Mock private ObjectRenderer mockedSecondRenderer;

  @Before public void setUp() {
    initializeSelector();
    initializeMocks();
  }

  @Test public void shouldDeselectAnyPreviousItemWhenAnotherItemIsSelected() {
    givenBindedRenderersWithItemIds();
    givenASelectableSelector();
    givenFirstSelectedItem();

    selector.setSelected(true, SECOND_ITEM_ID, SECOND_ITEM);
    Map<String, Object> selectedItems = selector.getSelectedItems();

    assertFalse(selector.isSelected(FIRST_ITEM_ID));
    assertTrue(selectedItems.containsKey(SECOND_ITEM_ID));
    assertEquals(1, selectedItems.size());
  }

  private void givenFirstSelectedItem() {
    selector.setSelected(true, FIRST_ITEM_ID, FIRST_ITEM);
  }

  private void givenASelectableSelector() {
    selector.setSelectable(true);
  }

  private void givenBindedRenderersWithItemIds() {
    when(mockedFirstRenderer.getItemId()).thenReturn(FIRST_ITEM_ID);
    when(mockedSecondRenderer.getItemId()).thenReturn(SECOND_ITEM_ID);
    selector.onBindRenderer(mockedFirstRenderer);
    selector.onBindRenderer(mockedSecondRenderer);
  }

  private void initializeSelector() {
    selector = new SingleSelector<>();
  }

  private void initializeMocks() {
    MockitoAnnotations.initMocks(this);
  }
}
