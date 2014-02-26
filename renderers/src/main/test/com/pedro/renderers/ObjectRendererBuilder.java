package com.pedro.renderers;

import java.util.List;

/**
 * RendererBuilder created only for testing purposes.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class ObjectRendererBuilder extends RendererBuilder<Object> {

    public ObjectRendererBuilder(List<Renderer<Object>> prototypes) {
        super(prototypes);
    }

    @Override
    protected Class getPrototypeClass(Object content) {
        return null;
    }
}
