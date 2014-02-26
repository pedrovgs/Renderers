package com.pedro.renderers;

import android.view.LayoutInflater;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class created to check the correct behavoiur of RendererAdapter.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
@RunWith(RobolectricTestRunner.class)
public class RendererAdapterTest {


    /*
     * Constants
     */

    private static final int ANY_SIZE = 11;
    private static final int ANY_POSITION = 2;
    private static final Object ANY_OBJECT = new Object();
    private static final int ANY_ITEM_VIEW_TYPE = 3;
    private static final int ANY_VIEW_TYPE_COUNT = 4;

    /*
     * Test data
     */

    private RendererAdapter<Object> rendererAdapter;

    /*
     * Mocks
     */

    @Mock
    private LayoutInflater mockedLayoutInflater;
    @Mock
    private RendererBuilder mockedRendererBuilder;
    @Mock
    private List<Object> mockedCollection;

    /*
     * Before and after methods
     */

    @Before
    public void setUp() throws Exception {
        initializeMocks();
        initializeRendererAdapter();
    }

    /*
     * Test methods
     */

    @Test
    public void shouldReturnCollectionSizeOnGetCount() {
        when(mockedCollection.size()).thenReturn(ANY_SIZE);

        assertEquals(ANY_SIZE, rendererAdapter.getCount());
    }

    @Test
    public void shouldReturnItemAtCollectionPositionOnGetItem() {
        when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    }

    @Test
    public void shouldReturnPositionAsItemId() {
        assertEquals(ANY_POSITION, rendererAdapter.getItemId(ANY_POSITION));
    }

    @Test
    public void shouldDelegateIntoRendererBuilderToGetItemViewType() {
        when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
        when(mockedRendererBuilder.getItemViewType(ANY_OBJECT)).thenReturn(ANY_ITEM_VIEW_TYPE);

        assertEquals(ANY_ITEM_VIEW_TYPE, rendererAdapter.getItemViewType(ANY_POSITION));
    }

    @Test
    public void shouldDelegateIntoRendererBuilderToGetViewTypeCount() {
        when(mockedRendererBuilder.getViewTypeCount()).thenReturn(ANY_VIEW_TYPE_COUNT);

        assertEquals(ANY_VIEW_TYPE_COUNT, rendererAdapter.getViewTypeCount());
    }

    /*
     * Auxiliary methods
     */

    private void initializeMocks() {
        MockitoAnnotations.initMocks(this);
    }

    private void initializeRendererAdapter() {
        rendererAdapter = new RendererAdapter<Object>(mockedLayoutInflater, mockedRendererBuilder, mockedCollection);
    }

}
