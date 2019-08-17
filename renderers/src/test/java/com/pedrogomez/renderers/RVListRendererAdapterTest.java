package com.pedrogomez.renderers;

import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@Config(sdk = 16) @RunWith(RobolectricTestRunner.class) public class RVListRendererAdapterTest {

    private static final int ANY_SIZE = 11;
    private static final int ANY_POSITION = 2;
    private static final Object ANY_OBJECT = new Object();
    private static final Collection<Object> ANY_OBJECT_COLLECTION = new LinkedList<>();
    private static final int ANY_ITEM_VIEW_TYPE = 3;

    private RVListRendererAdapter<Object> adapter;

    @Mock private RendererBuilder mockedRendererBuilder;
    @Mock private AdapteeCollection<Object> mockedCollection;
    @Mock private DiffUtil.ItemCallback<Object> mockedDiffItemCallback;
    @Mock private ViewGroup mockedParent;
    @Mock private ObjectRenderer mockedRenderer;
    @Mock private RendererViewHolder mockedRendererViewHolder;

    @Before public void setUp() {
        initializeMocks();
        initializeRVListRendererAdapter();
    }

    @Test
    public void shouldReturnTheAdapteeCollection() {
        assertEquals(mockedCollection, adapter.getCollection());
    }

    @Test
    public void shouldReturnCollectionSizeOnGetCount() {
        when(mockedCollection.size()).thenReturn(ANY_SIZE);

        assertEquals(ANY_SIZE, adapter.getItemCount());
    }

    @Test
    public void shouldReturnItemAtCollectionPositionOnGetItem() {
        when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    }

    @Test
    public void shouldReturnPositionAsItemId() {
        assertEquals(ANY_POSITION, adapter.getItemId(ANY_POSITION));
    }

    @Test
    public void shouldDelegateIntoRendererBuilderToGetItemViewType() {
        when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
        when(mockedRendererBuilder.getItemViewType(ANY_OBJECT)).thenReturn(ANY_ITEM_VIEW_TYPE);

        assertEquals(ANY_ITEM_VIEW_TYPE, adapter.getItemViewType(ANY_POSITION));
    }

    @Test
    public void shouldBuildRendererUsingAllNeededDependencies() {
        when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
        when(mockedRendererBuilder.buildRendererViewHolder()).thenReturn(mockedRendererViewHolder);

        adapter.onCreateViewHolder(mockedParent, ANY_ITEM_VIEW_TYPE);

        verify(mockedRendererBuilder).withParent(mockedParent);
        verify(mockedRendererBuilder).withLayoutInflater((LayoutInflater) notNull());
        verify(mockedRendererBuilder).withViewType(ANY_ITEM_VIEW_TYPE);
        verify(mockedRendererBuilder).buildRendererViewHolder();
    }

    @Test
    public void shouldGetRendererFromViewHolderAndCallUpdateRendererExtraValuesOnBind() {
        when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
        when(mockedRendererViewHolder.getRenderer()).thenReturn(mockedRenderer);

        adapter.onBindViewHolder(mockedRendererViewHolder, ANY_POSITION);

        verify(adapter).updateRendererExtraValues(ANY_OBJECT, mockedRenderer, ANY_POSITION);
    }

    @Test(expected = NullRendererBuiltException.class)
    public void shouldThrowNullRendererBuiltException() {
        adapter.onCreateViewHolder(mockedParent, ANY_ITEM_VIEW_TYPE);
    }

    @Test
    public void shouldAddElementToAdapteeCollection() {
        adapter.add(ANY_OBJECT);

        verify(mockedCollection).add(ANY_OBJECT);
    }

    @Test
    public void shouldAddAllElementsToAdapteeCollection() {
        adapter.addAll(ANY_OBJECT_COLLECTION);

        verify(mockedCollection).addAll(ANY_OBJECT_COLLECTION);
    }

    @Test
    public void shouldRemoveElementFromAdapteeCollection() {
        adapter.remove(ANY_OBJECT);

        verify(mockedCollection).remove(ANY_OBJECT);
    }

    @Test
    public void shouldRemoveAllElementsFromAdapteeCollection() {
        adapter.removeAll(ANY_OBJECT_COLLECTION);

        verify(mockedCollection).removeAll(ANY_OBJECT_COLLECTION);
    }

    @Test
    public void shouldClearElementsFromAdapteeCollection() {
        adapter.clear();

        verify(mockedCollection).clear();
    }

    @Test
    public void shouldGetRendererFromViewHolderAndUpdateContentOnBind() {
        when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
        when(mockedRendererViewHolder.getRenderer()).thenReturn(mockedRenderer);

        adapter.onBindViewHolder(mockedRendererViewHolder, ANY_POSITION);

        verify(mockedRenderer).setContent(ANY_OBJECT);
    }

    @Test
    public void shouldGetRendererFromViewHolderAndRenderItOnBind() {
        when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
        when(mockedRendererViewHolder.getRenderer()).thenReturn(mockedRenderer);

        adapter.onBindViewHolder(mockedRendererViewHolder, ANY_POSITION);

        verify(mockedRenderer).render();
    }

    @Test
    public void shouldSetAdapteeCollection() {
        RVListRendererAdapter<Object> adapter = new RVListRendererAdapter<Object>(mockedRendererBuilder, mockedDiffItemCallback);

        adapter.setCollection(mockedCollection);

        assertEquals(mockedCollection, adapter.getCollection());
    }

    @Test
    public void shouldBeEmptyWhenItsCreatedWithJustARendererBuilder() {
        RVListRendererAdapter<Object> adapter = new RVListRendererAdapter<Object>(mockedRendererBuilder, mockedDiffItemCallback);

        assertEquals(0, adapter.getItemCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSetNullCollection() {
        RVListRendererAdapter<Object> adapter = new RVListRendererAdapter<Object>(mockedRendererBuilder, mockedDiffItemCallback);

        adapter.setCollection(null);
    }

    private void initializeMocks() {
        MockitoAnnotations.initMocks(this);
        when(mockedParent.getContext()).thenReturn(RuntimeEnvironment.application);
    }

    private void initializeRVListRendererAdapter() {
        adapter = new RVListRendererAdapter<>(mockedRendererBuilder, mockedDiffItemCallback, mockedCollection);
        adapter = spy(adapter);
    }
}