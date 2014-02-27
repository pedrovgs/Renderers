package com.pedrogomez.renderers.exception;

/**
 * Exception created to be thrown when a renderer is not inflating a view. All your renderers have to inflate a view
 * and return it in inflateView method.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class NotInflateViewException extends RendererException {

    public NotInflateViewException(String detailMessage) {
        super(detailMessage);
    }
}
