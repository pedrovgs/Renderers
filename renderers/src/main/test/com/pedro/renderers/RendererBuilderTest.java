package com.pedro.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedro.renderers.exception.NeedsPrototypesException;
import com.pedro.renderers.exception.NullContentException;
import com.pedro.renderers.exception.NullLayoutInflaterException;
import com.pedro.renderers.exception.NullParentException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Test class created to check the correct behaviour of RendererBuilder
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RendererBuilderTest {

    /*
     * Test data
     */

    private ObjectRendererBuilder rendererBuilder;

    private List<Renderer<Object>> prototypes;
    private ObjectRenderer objectRenderer;
    private SubObjectRenderer subObjectRenderer;
    /*
     * Mocks
     */

    @Mock
    private View mockedConvertView;
    @Mock
    private ViewGroup mockedParent;
    @Mock
    private LayoutInflater mockedLayoutInflater;
    @Mock
    private Object mockedContent;
    @Mock
    private View mockedRendererdView;


    /*
     * Before and after methods
     */

    @Before
    public void setUp() {
        initializeMocks();
        initializePrototypes();
        initializeRendererBuilder();
    }

    /*
     * Test methods
     */

    @Test(expected = NeedsPrototypesException.class)
    public void shouldThrowNeedsPrototypeExceptionIfPrototypesIsNull() {
        rendererBuilder = new ObjectRendererBuilder(null);
    }

    @Test(expected = NeedsPrototypesException.class)
    public void shouldThrowNeedsPrototypeExceptionIfPrototypesIsEmpty() {
        prototypes = new LinkedList<Renderer<Object>>();
        initializeRendererBuilder();

        rendererBuilder = new ObjectRendererBuilder(prototypes);
    }

    @Test(expected = NullContentException.class)
    public void shouldThrowNullContentExceptionIfBuildRendererWithoutContent() {
        buildRenderer(null, mockedConvertView, mockedParent, mockedLayoutInflater);
    }

    @Test(expected = NullParentException.class)
    public void shouldThrowNullParentExceptionIfBuildRendererWithoutParent() {
        buildRenderer(mockedContent, mockedConvertView, null, mockedLayoutInflater);
    }


    @Test(expected = NullLayoutInflaterException.class)
    public void shouldThrowNullParentExceptionIfBuildARendererWithoutLayoutInflater() {

        buildRenderer(mockedContent, mockedConvertView, mockedParent, null);

    }

    @Test
    public void shouldReturnCreatedRenderer() {
        when(rendererBuilder.getPrototypeClass(mockedContent)).thenReturn(ObjectRenderer.class);

        Renderer<Object> renderer = buildRenderer(mockedContent, null, mockedParent, mockedLayoutInflater);

        assertEquals(objectRenderer.getClass(), renderer.getClass());
    }


    @Test
    public void shouldReturnRecycledRenderer() {
        when(rendererBuilder.getPrototypeClass(mockedContent)).thenReturn(ObjectRenderer.class);
        when(mockedConvertView.getTag()).thenReturn(objectRenderer);

        Renderer<Object> renderer = buildRenderer(mockedContent, mockedConvertView, mockedParent, mockedLayoutInflater);

        assertEquals(objectRenderer, renderer);
    }

    @Test
    public void shouldCreateRendererEvenIfTagInConvertViewIsNotNull() {
        when(rendererBuilder.getPrototypeClass(mockedContent)).thenReturn(ObjectRenderer.class);
        when(mockedConvertView.getTag()).thenReturn(subObjectRenderer);

        Renderer<Object> renderer = buildRenderer(mockedContent, mockedConvertView, mockedParent, mockedLayoutInflater);

        assertEquals(objectRenderer.getClass(), renderer.getClass());
    }





    /*
     * Auxiliary methods
     */

    private void initializeMocks() {
        MockitoAnnotations.initMocks(this);
    }

    private void initializePrototypes() {
        prototypes = new LinkedList<Renderer<Object>>();
        objectRenderer = new ObjectRenderer();
        objectRenderer.setView(mockedRendererdView);
        subObjectRenderer = new SubObjectRenderer();
        subObjectRenderer.setView(mockedRendererdView);
        prototypes.add(objectRenderer);
        prototypes.add(subObjectRenderer);

    }

    private void initializeRendererBuilder() {
        rendererBuilder = new ObjectRendererBuilder(prototypes);
        rendererBuilder = spy(rendererBuilder);
    }


    private Renderer<Object> buildRenderer(Object content, View convertView, ViewGroup parent, LayoutInflater layoutInflater) {
        rendererBuilder.withContent(content);
        rendererBuilder.withParent(parent);
        rendererBuilder.withLayoutInflater(layoutInflater);
        rendererBuilder.withConvertView(convertView);
        return rendererBuilder.build();
    }
}
