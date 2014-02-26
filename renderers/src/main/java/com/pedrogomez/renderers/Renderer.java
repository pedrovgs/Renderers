package com.pedrogomez.renderers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedrogomez.renderers.exception.NotInflateViewException;

/**
 * Base class created to work as a root ViewHolder in the classic list rootView / adapter implementation. This entity will
 * be extended by other renderes.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public abstract class Renderer<T> implements Cloneable {

    /*
     * Attributes
     */

    private View rootView;
    private T content;


    /*
     * Public methods
     */

    void onCreate(T content, LayoutInflater layoutInflater, ViewGroup parent) {
        this.content = content;
        this.rootView = inflate(layoutInflater, parent);
        if (rootView == null) {
            throw new NotInflateViewException();
        }
        this.rootView.setTag(this);
        setupView(rootView);
        hookListeners(rootView);
    }

    void onRecycle(T content) {
        this.content = content;
    }

    View getRootView() {
        return rootView;
    }

    /*
     * Protected and abstract methods
     */

    /**
     * @return the content stored in the renderer.
     */
    protected T getContent() {
        return content;
    }

    /**
     * Map all the widgets from the rootView.
     */
    protected abstract void setupView(View rootView);

    /**
     * Set all the listeners to widgets.
     */
    protected abstract void hookListeners(View rootView);

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
    protected abstract void render();


    Renderer copy() {
        Renderer copy = null;
        try {
            copy = (Renderer) this.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("Renderer", "All your renderers should be clonables.");
        }
        return copy;
    }

}
