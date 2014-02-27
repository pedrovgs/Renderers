package com.pedrogomez.renderers;

import com.pedrogomez.renderers.exception.RendererException;

/**
 * RendererException created to be thrown when the RendererBuilder implementation returns a null prototype class.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class NullPrototypeClassException extends RendererException {

    public NullPrototypeClassException(String detailMessage) {
        super(detailMessage);
    }

}
