package com.pedro.renderers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Base class created to work as a view holder in the classic list view / adapter implementation. This entity will
 * be extended by other renderes.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public abstract class Renderer<T> implements Cloneable {

    /*
     * Attributes
     */

    private View view;
    private T content;
    private ViewGroup parent;


    /*
     * Public methods
     */
    public void onCreate(T content) {
        this.content = content;
        setupView();
        hookListeners();
    }

    public void onRecycle(T content) {
        this.content = content;
    }

    /*
     * Auxiliary methods
     */

    protected void setContent(T content) {
        this.content = content;
    }

    protected T getContent() {
        return content;
    }

    protected void setParent(ViewGroup parent) {
        this.parent = parent;
    }

    protected void setView(View view) {
        this.view = view;
    }

    protected View getView() {
        return view;
    }

    /*
     * AbstractMethods
     */

    /**
     * Map all the widgets from the view.
     */
    protected abstract void setupView();

    /**
     * Set all the listeners to widgets.
     */
    protected abstract void hookListeners();

    /**
     * Inflates the Layout of the Renderer.
     *
     * @param inflater LayoutInflater service to inflate.
     * @return View with the inflated layout.
     */
    protected abstract View inflate(LayoutInflater inflater, ViewGroup parent);

    /**
     * Abstract method to implement in subtypes. Each subtype has a different way of render the information.
     */
    public abstract View render();


    public Renderer copy() {
        Renderer copy = null;
        try {
            copy = (Renderer) this.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("Renderer", "All your renderers should be clonables.");
        }
        return copy;
    }
}
