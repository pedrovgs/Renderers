package com.pedrogomez.renderers.exception;

/**
 * Exception created to be thrown when a RendererBuilder be created without any prototype. A RendererBuilder needs
 * prototypes to create or recycle new prototypes.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class NeedsPrototypesException extends RendererException {

    public NeedsPrototypesException(String detailMessage) {
        super(detailMessage);
    }
}
