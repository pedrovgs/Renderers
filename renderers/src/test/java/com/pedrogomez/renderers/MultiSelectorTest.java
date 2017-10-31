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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(emulateSdk = 16) @RunWith(RobolectricTestRunner.class) public class MultiSelectorTest {

  private static final String ANY_ITEM_ID = "1";
  private static final Object ANY_ITEM = new Object();

  private MultiSelector<Object> selector;

  @Mock private ObjectRenderer mockedRenderer;

  @Before public void setUp() {
    initializeSelector();
    initializeMocks();
  }

  @Test public void shouldNotSelectAnyItemIfItIsNotInSelectableState() {
    givenABindedRendererWithItemId();
    givenANotSelectableSelector();
    givenASelectedItem();

    Map<String, Object> selectedItems = selector.getSelectedItems();

    assertEquals(0, selectedItems.size());
    assertFalse(selector.isSelected(ANY_ITEM_ID));
  }

  @Test public void shouldSelectTheItemIfItIsInSelectableState() {
    givenABindedRendererWithItemId();
    givenASelectableSelector();
    givenASelectedItem();

    Map<String, Object> selectedItems = selector.getSelectedItems();

    assertTrue(selectedItems.containsKey(ANY_ITEM_ID));
    assertTrue(selector.isSelected(ANY_ITEM_ID));
  }

  @Test public void shouldRefreshRendererWhenItsSelected() {
    givenABindedRendererWithItemId();
    givenASelectableSelector();

    selector.setSelected(true, ANY_ITEM_ID, ANY_ITEM);

    verify(mockedRenderer).render();
  }

  @Test public void shouldRefreshAllRenderersWhenDisablingSelectableState() {
    givenABindedRendererWithItemId();
    givenASelectableSelector();
    givenASelectedItem();

    selector.setSelectable(false);

    verify(mockedRenderer, times(2)).render();
  }

  private void givenASelectedItem() {
    selector.setSelected(true, ANY_ITEM_ID, ANY_ITEM);
  }

  private void givenANotSelectableSelector() {
    selector.setSelectable(false);
  }

  private void givenASelectableSelector() {
    selector.setSelectable(true);
  }

  private void givenABindedRendererWithItemId() {
    when(mockedRenderer.getItemId()).thenReturn(ANY_ITEM_ID);
    selector.onBindRenderer(mockedRenderer);
  }

  private void initializeSelector() {
    selector = new MultiSelector<>();
  }

  private void initializeMocks() {
    MockitoAnnotations.initMocks(this);
  }
}
