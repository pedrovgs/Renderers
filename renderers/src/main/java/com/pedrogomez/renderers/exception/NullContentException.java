package com.pedrogomez.renderers.exception;


/**
 * Exception created to be thrown when a RendererBuilder be created without content. RendererBuilder needs a content
 * to create renderers.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class NullContentException extends RendererException {

    public NullContentException(String detailMessage) {
        super(detailMessage);
    }
}
