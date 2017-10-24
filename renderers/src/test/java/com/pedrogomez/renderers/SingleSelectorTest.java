package com.pedrogomez.renderers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(emulateSdk = 16) @RunWith(RobolectricTestRunner.class) public class SingleSelectorTest {

  private static final String FIRST_ITEM_ID = "1";
  private static final String SECOND_ITEM_ID = "2";

  private SingleSelector<Object> selector;

  @Mock private ObjectRenderer mockedFirstRenderer;
  @Mock private ObjectRenderer mockedSecondRenderer;

  @Before
  public void setUp() {
    initializeSelector();
    initializeMocks();
  }

  @Test public void shouldDeselectAnyPreviousItemWhenAnotherItemIsSelected() {
    givenBindedRenderersWithItemIds();
    givenASelectableSelector();
    givenFirstSelectedItem();

    selector.setSelected(true, SECOND_ITEM_ID);
    Set<String> selectedItemIds = selector.getSelectedItemIds();

    assertFalse(selector.isSelected(FIRST_ITEM_ID));
    assertTrue(selectedItemIds.contains(SECOND_ITEM_ID));
    assertEquals(1, selectedItemIds.size());
  }

  private void givenFirstSelectedItem() {
    selector.setSelected(true, FIRST_ITEM_ID);
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
