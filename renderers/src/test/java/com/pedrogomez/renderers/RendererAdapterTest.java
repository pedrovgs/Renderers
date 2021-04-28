package com.pedrogomez.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedrogomez.renderers.exception.NullRendererBuiltException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class created to check the correct behaviour of RendererAdapter.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
@Config(sdk = 16) @RunWith(RobolectricTestRunner.class) public class RendererAdapterTest {

  private static final int ANY_SIZE = 11;
  private static final int ANY_POSITION = 2;
  private static final Object ANY_OBJECT = new Object();
  private static final int ANY_ITEM_VIEW_TYPE = 3;
  private static final int ANY_VIEW_TYPE_COUNT = 4;
  private static final Collection<Object> ANY_OBJECT_COLLECTION = new LinkedList<>();

  private RendererAdapter<Object> rendererAdapter;

  @Mock private RendererBuilder mockedRendererBuilder;
  @Mock private List<Object> mockedList;
  @Mock(extraInterfaces = List.class) private AdapteeCollection<Object> mockedCollection;
  @Mock private View mockedConvertView;
  @Mock private ViewGroup mockedParent;
  @Mock private ObjectRenderer mockedRenderer;
  @Mock private View mockedView;

  @Before public void setUp() {
    initializeMocks();
    initializeRendererAdapter();
  }

  @Test public void shouldReturnTheList() {
    assertEquals(mockedList, rendererAdapter.getList());
  }

  @Test public void shouldReturnListSizeOnGetCount() {
    when(mockedList.size()).thenReturn(ANY_SIZE);

    assertEquals(ANY_SIZE, rendererAdapter.getCount());
  }

  @Test public void shouldReturnPositionAsItemId() {
    assertEquals(ANY_POSITION, rendererAdapter.getItemId(ANY_POSITION));
  }

  @Test public void shouldDelegateIntoRendererBuilderToGetItemViewType() {
    when(mockedList.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererBuilder.getItemViewType(ANY_OBJECT)).thenReturn(ANY_ITEM_VIEW_TYPE);

    assertEquals(ANY_ITEM_VIEW_TYPE, rendererAdapter.getItemViewType(ANY_POSITION));
  }

  @Test public void shouldDelegateIntoRendererBuilderToGetViewTypeCount() {
    when(mockedRendererBuilder.getViewTypeCount()).thenReturn(ANY_VIEW_TYPE_COUNT);

    assertEquals(ANY_VIEW_TYPE_COUNT, rendererAdapter.getViewTypeCount());
  }

  @Test public void shouldBuildRendererUsingAllNeededDependencies() {
    when(mockedList.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererBuilder.build()).thenReturn(mockedRenderer);

    rendererAdapter.getView(ANY_POSITION, mockedConvertView, mockedParent);

    verify(mockedRendererBuilder).withContent(ANY_OBJECT);
    verify(mockedRendererBuilder).withConvertView(mockedConvertView);
    verify(mockedRendererBuilder).withParent(mockedParent);
    verify(mockedRendererBuilder).withLayoutInflater((LayoutInflater) notNull());
  }

  @Test public void shouldBuildRendererAndCallUpdateRendererExtraValues() {
    when(mockedList.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererBuilder.build()).thenReturn(mockedRenderer);

    rendererAdapter.getView(ANY_POSITION, mockedConvertView, mockedParent);

    verify(rendererAdapter).updateRendererExtraValues(ANY_OBJECT, mockedRenderer, ANY_POSITION);
  }

  @Test(expected = NullRendererBuiltException.class)
  public void shouldThrowNullRendererBuiltException() {
    rendererAdapter.getView(ANY_POSITION, mockedConvertView, mockedParent);
  }

  @Test public void shouldRenderTheRendererBuilt() {
    when(mockedRendererBuilder.build()).thenReturn(mockedRenderer);

    rendererAdapter.getView(ANY_POSITION, mockedConvertView, mockedParent);

    verify(mockedRenderer).render();
  }

  @Test public void shouldReturnRendererRootView() {
    when(mockedRendererBuilder.build()).thenReturn(mockedRenderer);
    when(mockedRenderer.getRootView()).thenReturn(mockedView);

    View renderedView = rendererAdapter.getView(ANY_POSITION, mockedConvertView, mockedParent);

    assertEquals(mockedView, renderedView);
  }

  @Test public void shouldAddElementToList() {
    rendererAdapter.add(ANY_OBJECT);

    verify(mockedList).add(ANY_OBJECT);
  }

  @Test public void shouldAddAllElementsToList() {
    rendererAdapter.addAll(ANY_OBJECT_COLLECTION);

    verify(mockedList).addAll(ANY_OBJECT_COLLECTION);
  }

  @Test public void shouldRemoveElementFromList() {
    rendererAdapter.remove(ANY_OBJECT);

    verify(mockedList).remove(ANY_OBJECT);
  }

  @Test public void shouldRemoveAllElementsFromList() {
    rendererAdapter.removeAll(ANY_OBJECT_COLLECTION);

    verify(mockedList).removeAll(ANY_OBJECT_COLLECTION);
  }

  @Test public void shouldClearElementsFromList() {
    rendererAdapter.clear();

    verify(mockedList).clear();
  }

  @Test public void shouldSetAdapteeCollection() {
    AdapteeCollection collection = new ListAdapteeCollection();
    collection.add("test");
    RendererAdapter<Object> adapter = new RendererAdapter<Object>(mockedRendererBuilder);

    adapter.setCollection(collection);

    assertEquals(collection.get(0), adapter.getCollection().get(0));
  }

  @Test public void shouldSetList() {
    RendererAdapter<Object> adapter = new RendererAdapter<Object>(mockedRendererBuilder);

    adapter.setList(mockedList);

    assertEquals(mockedList, adapter.getList());
  }

  @Test public void shouldBeEmptyWhenItsCreatedWithJustARendererBuilder() {
    RendererAdapter<Object> adapter = new RendererAdapter<Object>(mockedRendererBuilder);

    assertEquals(0, adapter.getCount());
  }


  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenSetNullCollection() {
    RendererAdapter<Object> adapter = new RendererAdapter<Object>(mockedRendererBuilder);

    adapter.setCollection(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenSetNullList() {
    RendererAdapter<Object> adapter = new RendererAdapter<Object>(mockedRendererBuilder);

    adapter.setList(null);
  }

  private void initializeMocks() {
    MockitoAnnotations.initMocks(this);
    when(mockedParent.getContext()).thenReturn(RuntimeEnvironment.application);
  }

  private void initializeRendererAdapter() {
    rendererAdapter = new RendererAdapter<>(mockedRendererBuilder, mockedList);
    rendererAdapter = spy(rendererAdapter);
  }
}
