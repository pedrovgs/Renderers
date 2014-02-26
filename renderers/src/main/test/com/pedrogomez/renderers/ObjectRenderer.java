package com.pedrogomez.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Renderer created only for testing purposes.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class ObjectRenderer extends Renderer<Object> {

    private View view;

    @Override
    protected void setupView(View rootView) {

    }

    @Override
    protected void hookListeners(View rootView) {

    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return view;
    }

    @Override
    protected void render() {

    }

    public void setView(View view) {
        this.view = view;
    }


}
