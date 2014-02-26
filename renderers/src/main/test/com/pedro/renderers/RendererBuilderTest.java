package com.pedro.renderers;

import com.pedro.renderers.exception.NeedsPrototypesException;
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
