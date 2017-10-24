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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(emulateSdk = 16) @RunWith(RobolectricTestRunner.class) public class MultiSelectorTest {

  private static final java.lang.String ANY_ITEM_ID = "1";

  private MultiSelector<Object> selector;

  @Mock private ObjectRenderer mockedRenderer;

  @Before
  public void setUp() {
    initializeSelector();
    initializeMocks();
  }

  @Test public void shouldNotSelectAnyItemIfItIsNotInSelectableState() {
    givenABindedRendererWithItemId();
    givenANotSelectableSelector();
    givenASelectedItem();

    Set<String> selectedItemIds = selector.getSelectedItemIds();

    assertEquals(0, selectedItemIds.size());
    assertFalse(selector.isSelected(ANY_ITEM_ID));
  }

  @Test public void shouldSelectTheItemIfItIsInSelectableState() {
    givenABindedRendererWithItemId();
    givenASelectableSelector();
    givenASelectedItem();

    Set<String> selectedItemIds = selector.getSelectedItemIds();

    assertTrue(selectedItemIds.contains(ANY_ITEM_ID));
    assertTrue(selector.isSelected(ANY_ITEM_ID));
  }

  @Test public void shouldRefreshRendererWhenItsSelected() {
    givenABindedRendererWithItemId();
    givenASelectableSelector();

    selector.setSelected(true, ANY_ITEM_ID);

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
    selector.setSelected(true, ANY_ITEM_ID);
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
