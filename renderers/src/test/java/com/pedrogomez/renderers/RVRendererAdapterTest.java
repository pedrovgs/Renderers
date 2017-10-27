/*
 * Copyright (C) 2014 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pedrogomez.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedrogomez.renderers.exception.NullRendererBuiltException;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class created to check the correct behaviour of RVRendererAdapter.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
@Config(emulateSdk = 16) @RunWith(RobolectricTestRunner.class) public class RVRendererAdapterTest {

  private static final int ANY_SIZE = 11;
  private static final int ANY_POSITION = 2;
  private static final Object ANY_OBJECT = new Object();
  private static final Collection<Object> ANY_OBJECT_COLLECTION = new LinkedList<Object>();
  private static final int ANY_ITEM_VIEW_TYPE = 3;

  private RVRendererAdapter<Object> adapter;

  @Mock private RendererBuilder mockedRendererBuilder;
  @Mock private AdapteeCollection<Object> mockedCollection;
  @Mock private View mockedConvertView;
  @Mock private ViewGroup mockedParent;
  @Mock private ObjectRenderer mockedRenderer;
  @Mock private View mockedView;
  @Mock private RendererViewHolder mockedRendererViewHolder;

  @Before public void setUp() throws Exception {
    initializeMocks();
    initializeRVRendererAdapter();
  }

  @Test public void shouldReturnTheAdapteeCollection() {
    assertEquals(mockedCollection, adapter.getCollection());
  }

  @Test public void shouldReturnCollectionSizeOnGetCount() {
    when(mockedCollection.size()).thenReturn(ANY_SIZE);

    assertEquals(ANY_SIZE, adapter.getItemCount());
  }

  @Test public void shouldReturnItemAtCollectionPositionOnGetItem() {
    when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
  }

  @Test public void shouldReturnPositionAsItemId() {
    assertEquals(ANY_POSITION, adapter.getItemId(ANY_POSITION));
  }

  @Test public void shouldDelegateIntoRendererBuilderToGetItemViewType() {
    when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererBuilder.getItemViewType(ANY_OBJECT)).thenReturn(ANY_ITEM_VIEW_TYPE);

    assertEquals(ANY_ITEM_VIEW_TYPE, adapter.getItemViewType(ANY_POSITION));
  }

  @Test public void shouldBuildRendererUsingAllNeededDependencies() {
    when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererBuilder.buildRendererViewHolder((Selector) any())).thenReturn(mockedRendererViewHolder);

    adapter.onCreateViewHolder(mockedParent, ANY_ITEM_VIEW_TYPE);

    verify(mockedRendererBuilder).withParent(mockedParent);
    verify(mockedRendererBuilder).withLayoutInflater((LayoutInflater) notNull());
    verify(mockedRendererBuilder).withViewType(ANY_ITEM_VIEW_TYPE);
    verify(mockedRendererBuilder).buildRendererViewHolder((Selector) any());
  }

  @Test public void shouldGetRendererFromViewHolderAndCallUpdateRendererExtraValuesOnBind() {
    when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererViewHolder.getRenderer()).thenReturn(mockedRenderer);

    adapter.onBindViewHolder(mockedRendererViewHolder, ANY_POSITION);

    verify(adapter).updateRendererExtraValues(ANY_OBJECT, mockedRenderer, ANY_POSITION);
  }

  @Test(expected = NullRendererBuiltException.class)
  public void shouldThrowNullRendererBuiltException() {
    adapter.onCreateViewHolder(mockedParent, ANY_ITEM_VIEW_TYPE);
  }

  @Test public void shouldAddElementToAdapteeCollection() {
    adapter.add(ANY_OBJECT);

    verify(mockedCollection).add(ANY_OBJECT);
  }

  @Test public void shouldAddAllElementsToAdapteeCollection() {
    adapter.addAll(ANY_OBJECT_COLLECTION);

    verify(mockedCollection).addAll(ANY_OBJECT_COLLECTION);
  }

  @Test public void shouldRemoveElementFromAdapteeCollection() {
    adapter.remove(ANY_OBJECT);

    verify(mockedCollection).remove(ANY_OBJECT);
  }

  @Test public void shouldRemoveAllElementsFromAdapteeCollection() {
    adapter.removeAll(ANY_OBJECT_COLLECTION);

    verify(mockedCollection).removeAll(ANY_OBJECT_COLLECTION);
  }

  @Test public void shouldClearElementsFromAdapteeCollection() {
    adapter.clear();

    verify(mockedCollection).clear();
  }

  @Test public void shouldGetRendererFromViewHolderAndUpdateContentOnBind() {
    when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererViewHolder.getRenderer()).thenReturn(mockedRenderer);

    adapter.onBindViewHolder(mockedRendererViewHolder, ANY_POSITION);

    verify(mockedRenderer).setContent(ANY_OBJECT);
  }

  @Test public void shouldGetRendererFromViewHolderAndRenderItOnBind() {
    when(mockedCollection.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererViewHolder.getRenderer()).thenReturn(mockedRenderer);

    adapter.onBindViewHolder(mockedRendererViewHolder, ANY_POSITION);

    verify(mockedRenderer).render();
  }

  @Test public void shouldSetAdapteeCollection() throws Exception {
    RVRendererAdapter<Object> adapter = new RVRendererAdapter<Object>(mockedRendererBuilder);

    adapter.setCollection(mockedCollection);

    assertEquals(mockedCollection, adapter.getCollection());
  }

  @Test public void shouldBeEmptyWhenItsCreatedWithJustARendererBuilder() {
    RVRendererAdapter<Object> adapter = new RVRendererAdapter<Object>(mockedRendererBuilder);

    assertEquals(0, adapter.getItemCount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenSetNullCollection() {
    RVRendererAdapter<Object> adapter = new RVRendererAdapter<Object>(mockedRendererBuilder);

    adapter.setCollection(null);
  }

  private void initializeMocks() {
    MockitoAnnotations.initMocks(this);
    when(mockedParent.getContext()).thenReturn(Robolectric.application);
  }

  private void initializeRVRendererAdapter() {
    adapter = new RVRendererAdapter<Object>(mockedRendererBuilder, mockedCollection);
    adapter = spy(adapter);
  }
}
