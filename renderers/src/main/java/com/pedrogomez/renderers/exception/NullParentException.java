package com.pedrogomez.renderers.exception;

/**
 * Exception created to be thrown when a RendererBuilder be created without parent. RendererBuilder needs a ViewGroup
 * parent to pass it as parameter to renderers.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class NullParentException extends RendererException {

    public NullParentException(String detailMessage) {
        super(detailMessage);
    }
}
