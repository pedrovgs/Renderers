package com.pedrogomez.renderers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedrogomez.renderers.exception.NotInflateViewException;

/**
 * Base class created to work as a root ViewHolder in the classic list rootView / adapter implementation. This entity will
 * be extended by other renderes.
 * <p/>
 * A renderer have to encapsulate the presentation logic for ech row of your ListView. Every renderer have inside the
 * view is rendering and the content is using to get the info.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public abstract class Renderer<T> implements Cloneable {

    /*
     * Attributes
     */

    private View rootView;
    private T content;


    /**
     * Method called when the renderer is going to be created. This method has the responsibility of inflate the xml
     * layout using the layoutInflater and the parent ViewGroup, set itself to the tag and call setUpView and hookListeners.
     *
     * @param content        to render.
     * @param layoutInflater used to inflate the view.
     * @param parent         used to inflate the view.
     */
    void onCreate(T content, LayoutInflater layoutInflater, ViewGroup parent) {
        this.content = content;
        this.rootView = inflate(layoutInflater, parent);
        if (rootView == null) {
            throw new NotInflateViewException("Renderers have to return a not null view in inflateView method");
        }
        this.rootView.setTag(this);
        setUpView(rootView);
        hookListeners(rootView);
    }

    /**
     * Method called wieh the renderer has been recycled. This method has the responsibility of update the content stored
     * in the renderer.
     *
     * @param content to render.
     */
    void onRecycle(T content) {
        this.content = content;
    }

    /**
     * Method to access the main view rendered in the renderer.
     *
     * @return top view in the view hierarchy of one renderer.
     */
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
     * Map all the widgets from the rootView to renderer members.
     */
    protected abstract void setUpView(View rootView);

    /**
     * Set all the listeners to members mapped in setUpView method.
     */
    protected abstract void hookListeners(View rootView);

    /**
     * Inflates the Layout of the Renderer. The view inflated can't be null. If this method returns a null view a
     * NotInflateViewException will be thrown.
     *
     * @param inflater LayoutInflater service to inflate.
     * @return View with the inflated layout.
     */
    protected abstract View inflate(LayoutInflater inflater, ViewGroup parent);

    /**
     * Method where the presentation logic algorithm have to be declared or implemented.
     */
    protected abstract void render();

    /**
     * Create a clone of the renderer. This method is the base of the prototype mechanism implemented to avoid create
     * new objects from RendererBuilder
     *
     * @return a copy of the current renderer.
     */
    protected Renderer copy() {
        Renderer copy = null;
        try {
            copy = (Renderer) this.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("Renderer", "All your renderers should be clonables.");
        }
        return copy;
    }

}
