package com.pedro.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedro.renderers.exception.NeedsPrototypesException;
import com.pedro.renderers.exception.NullContentException;
import com.pedro.renderers.exception.NullLayoutInflaterException;
import com.pedro.renderers.exception.NullParentException;

import java.util.List;

/**
 * Class created to work as builder for renderers. This class provides methods to create a renderer.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
public abstract class RendererBuilder<T> {

    /*
     * Attributes
     */
    private final List<Renderer<T>> prototypes;

    private T content;
    private View convertView;
    private ViewGroup parent;
    private LayoutInflater layoutInflater;

    /*
     * Constructor
     */

    public RendererBuilder(List<Renderer<T>> prototypes) {
        if (prototypes == null || prototypes.isEmpty()) {
            throw new NeedsPrototypesException();
        }
        this.prototypes = prototypes;
    }

    /*
     * Builder constructor
     */


    public RendererBuilder withContent(T content) {
        this.content = content;
        return this;

    }

    public RendererBuilder withConvertView(View convertView) {
        this.convertView = convertView;
        return this;

    }

    public RendererBuilder withParent(ViewGroup parent) {
        this.parent = parent;
        return this;
    }

    public RendererBuilder withLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        return this;
    }


    Renderer build() {
        validateAttributes();
        Renderer renderer;
        if (isRecyclable(convertView, content)) {
            renderer = recycle(convertView, content);
        } else {
            renderer = createRenderer(content, parent);
        }
        return renderer;
    }

    protected List<Renderer<T>> getPrototypes() {
        return prototypes;
    }

    /*
     * Recycle methods
     */

    private Renderer recycle(View convertView, T content) {
        Renderer renderer = (Renderer) convertView.getTag();
        renderer.onRecycle(content);
        return renderer;
    }

    private Renderer createRenderer(T content, ViewGroup parent) {
        int prototypeIndex = getPrototypeIndex(content);
        Renderer renderer = prototypes.get(prototypeIndex).copy();
        renderer.onCreate(content, layoutInflater, parent);
        return renderer;
    }


    private boolean isRecyclable(View convertView, T content) {
        return convertView != null && convertView.getTag() != null && getPrototypeClass(content).equals(convertView.getTag().getClass());
    }

    int getItemViewType(T content) {
        Class prototypeClass = getPrototypeClass(content);
        return getItemViewType(prototypeClass);
    }


    private int getPrototypeIndex(T content) {
        return getItemViewType(content);
    }


    int getViewTypeCount() {
        return prototypes.size();
    }


    /*
     * Abstract method
     */

    protected abstract Class getPrototypeClass(T content);

    /*
     * Auxiliary methods
     */

    private int getItemViewType(Class prototypeClass) {
        int itemViewType = 0;
        for (Renderer renderer : prototypes) {
            if (renderer.getClass().equals(prototypeClass)) {
                itemViewType = prototypes.indexOf(renderer);
                break;
            }
        }
        return itemViewType;
    }


    private void validateAttributes() {
        if (content == null) {
            throw new NullContentException();
        }

        if (parent == null) {
            throw new NullParentException();
        }

        if (layoutInflater == null) {
            throw new NullLayoutInflaterException();
        }
    }


}
