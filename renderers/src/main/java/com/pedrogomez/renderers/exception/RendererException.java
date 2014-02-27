package com.pedrogomez.renderers.exception;

/**
 * Top class in the renderer exception hierarchy.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RendererException extends RuntimeException {

    public RendererException(String detailMessage) {
        super(detailMessage);
    }
}
