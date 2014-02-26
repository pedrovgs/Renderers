package com.pedro.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedro.renderers.exception.NeedsPrototypesException;
import com.pedro.renderers.exception.NullContentException;
import com.pedro.renderers.exception.NullLayoutInflaterException;
import com.pedro.renderers.exception.NullParentException;
import com.sun.tools.javac.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Test class created to check the correct behaviour of RendererBuilder
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RendererBuilderTest {

    /*
     * Constants
     */

    private static final Integer ANY_SIZE = 10;

    /*
     * Test data
     */

    private ObjectRendererBuilder rendererBuilder;

    /*
     * Mocks
     */

    @Mock
    private List<Renderer<Object>> mockedPrototypes;
    @Mock
    private View mockedConvertView;
    @Mock
    private ViewGroup mockedParent;
    @Mock
    private LayoutInflater mockedLayoutInflater;
    @Mock
    private Object mockedContent;

    /*
     * Before and after methods
     */

    @Before
    public void setUp() {
        initializeMocks();
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
        when(mockedPrototypes.isEmpty()).thenReturn(true);

        rendererBuilder = new ObjectRendererBuilder(mockedPrototypes);
    }

    @Test(expected = NullContentException.class)
    public void shouldThrowNullContentExceptionIfBuildRendererWithoutContent() {
        rendererBuilder.withConvertView(mockedConvertView);
        rendererBuilder.withParent(mockedParent);
        rendererBuilder.withLayoutInflater(mockedLayoutInflater);
        rendererBuilder.build();
    }

    @Test(expected = NullParentException.class)
    public void shouldThrowNullParentExceptionIfBuildRendererWithoutParent() {
        rendererBuilder.withConvertView(mockedConvertView);
        rendererBuilder.withContent(mockedContent);
        rendererBuilder.withLayoutInflater(mockedLayoutInflater);
        rendererBuilder.build();
    }


    @Test(expected = NullLayoutInflaterException.class)
    public void shouldThrowNullParentExceptionIfBuildARendererWithoutLayoutInflater() {
        rendererBuilder.withConvertView(mockedConvertView);
        rendererBuilder.withContent(mockedContent);
        rendererBuilder.withParent(mockedParent);
        rendererBuilder.build();
    }


    /*
     * Auxiliary methods
     */

    private void initializeMocks() {
        MockitoAnnotations.initMocks(this);
    }

    private void initializeRendererBuilder() {
        when(mockedPrototypes.isEmpty()).thenReturn(false);
        rendererBuilder = new ObjectRendererBuilder(mockedPrototypes);
        rendererBuilder = spy(rendererBuilder);
    }
}
