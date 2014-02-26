package com.pedrogomez.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedrogomez.renderers.exception.NeedsPrototypesException;
import com.pedrogomez.renderers.exception.NullContentException;
import com.pedrogomez.renderers.exception.NullLayoutInflaterException;
import com.pedrogomez.renderers.exception.NullParentException;

import java.util.Collection;

/**
 * Class created to work as builder for renderers. This class provides methods to create a renderer.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
public abstract class RendererBuilder<T> {

    /*
     * Attributes
     */
    private final Collection<Renderer<T>> prototypes;

    private T content;
    private View convertView;
    private ViewGroup parent;
    private LayoutInflater layoutInflater;

    /*
     * Constructor
     */

    public RendererBuilder(Collection<Renderer<T>> prototypes) {
        if (prototypes == null || prototypes.isEmpty()) {
            throw new NeedsPrototypesException();
        }
        this.prototypes = prototypes;
    }

    /*
     * Builder constructor
     */

    protected Collection<Renderer<T>> getPrototypes() {
        return prototypes;
    }

    RendererBuilder withContent(T content) {
        this.content = content;
        return this;

    }

    RendererBuilder withConvertView(View convertView) {
        this.convertView = convertView;
        return this;

    }

    RendererBuilder withParent(ViewGroup parent) {
        this.parent = parent;
        return this;
    }

    RendererBuilder withLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        return this;
    }

    int getItemViewType(T content) {
        Class prototypeClass = getPrototypeClass(content);
        return getItemViewType(prototypeClass);
    }

    int getViewTypeCount() {
        return prototypes.size();
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

    private Renderer recycle(View convertView, T content) {
        Renderer renderer = (Renderer) convertView.getTag();
        renderer.onRecycle(content);
        return renderer;
    }

    private Renderer createRenderer(T content, ViewGroup parent) {
        int prototypeIndex = getPrototypeIndex(content);
        Renderer renderer = getPrototypeByIndex(prototypeIndex).copy();
        renderer.onCreate(content, layoutInflater, parent);
        return renderer;
    }

    private Renderer getPrototypeByIndex(final int prototypeIndex) {
        Renderer prototypeSelected = null;
        int i = 0;
        for (Renderer prototype : prototypes) {
            if (i == prototypeIndex) {
                prototypeSelected = prototype;
            }
            i++;
        }
        return prototypeSelected;
    }


    private boolean isRecyclable(View convertView, T content) {
        return convertView != null && convertView.getTag() != null && getPrototypeClass(content).equals(convertView.getTag().getClass());
    }

    private int getPrototypeIndex(T content) {
        return getItemViewType(content);
    }

    private int getItemViewType(Class prototypeClass) {
        System.out.println("->" + prototypeClass);
        int itemViewType = 0;
        for (Renderer renderer : prototypes) {
            System.out.println("Evaluando " + renderer.getClass());
            if (renderer.getClass().equals(prototypeClass)) {
                System.out.println("Prototype found");
                itemViewType = getPrototypeIndex(renderer);
                break;
            }
        }
        //Si llega aquí hay que lanzar una excepción
        //Significa que Prototype class not found
        return itemViewType;
    }

    private int getPrototypeIndex(Renderer renderer) {
        int index = 0;
        for (Renderer prototype : prototypes) {
            if (prototype.getClass().equals(renderer.getClass())) {
                break;
            }
            index++;
        }
        return index;
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

    /*
     * Abstract method
     */

    protected abstract Class getPrototypeClass(T content);


}
