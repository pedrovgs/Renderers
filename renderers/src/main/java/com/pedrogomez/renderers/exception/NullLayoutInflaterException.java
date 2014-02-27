package com.pedrogomez.renderers.exception;

/**
 * Exception created to be thrown when a RendererBuilder be created without layout inflater. RendererBuilder needs
 * one LayoutInflater to pass it as parameter to renderers.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class NullLayoutInflaterException extends RendererException {

    public NullLayoutInflaterException(String detailMessage) {
        super(detailMessage);
    }
}
