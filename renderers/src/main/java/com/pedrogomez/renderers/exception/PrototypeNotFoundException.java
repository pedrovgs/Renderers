package com.pedrogomez.renderers.exception;

/**
 * Exception created to be thrown when RendererBuilders returns one renderer class not found in prototypes collection.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class PrototypeNotFoundException extends RendererException {

    public PrototypeNotFoundException(String detailMessage) {
        super(detailMessage);
    }
}
