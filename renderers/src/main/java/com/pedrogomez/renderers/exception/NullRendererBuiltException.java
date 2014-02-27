package com.pedrogomez.renderers.exception;

/**
 * Exception created to be thrown when a RendererBuilder returns a null renderer. Your RendererBuilder always have to
 * return a built renderer.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class NullRendererBuiltException extends RendererException {

    public NullRendererBuiltException(String detailMessage) {
        super(detailMessage);
    }
}
