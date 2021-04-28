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
import android.view.ViewGroup;

import com.pedrogomez.renderers.exception.NullRendererBuiltException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class created to check the correct behaviour of VPRendererAdapter.
 *
 * @author Jc Miñarro
 */
@Config(sdk = 16) @RunWith(RobolectricTestRunner.class) public class VPRendererAdapterTest {

  private static final int ANY_SIZE = 11;
  private static final int ANY_POSITION = 2;
  private static final Object ANY_OBJECT = new Object();
  private static final Collection<Object> ANY_OBJECT_COLLECTION = new LinkedList<Object>();
  private static final int ANY_ITEM_VIEW_TYPE = 3;

  private VPRendererAdapter<Object> adapter;

  @Mock private RendererBuilder mockedRendererBuilder;
  @Mock private AdapteeCollection<Object> mockedCollection;
  @Mock private List<Object> mockedList;
  @Mock private ViewGroup mockedParent;
  @Mock private ObjectRenderer mockedRenderer;

  @Before public void setUp() throws Exception {
    initializeMocks();
    initializeVPRendererAdapter();
  }

  @Test public void shouldReturnTheList() {
    assertEquals(mockedList, adapter.getList());
  }

  @Test public void shouldReturnCollectionSizeOnGetCount() {
    when(mockedList.size()).thenReturn(ANY_SIZE);

    assertEquals(ANY_SIZE, adapter.getCount());
  }

  @Test public void shouldBuildRendererUsingAllNeededDependencies() {
    when(mockedList.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererBuilder.build()).thenReturn(mockedRenderer);

    adapter.instantiateItem(mockedParent, ANY_POSITION);

    verify(mockedRendererBuilder).withParent(mockedParent);
    verify(mockedRendererBuilder).withContent(ANY_OBJECT);
    verify(mockedRendererBuilder).withLayoutInflater((LayoutInflater) notNull());
    verify(mockedRendererBuilder).build();
  }

  @Test(expected = NullRendererBuiltException.class)
  public void shouldThrowNullRendererBuiltException() {
    adapter.instantiateItem(mockedParent, ANY_ITEM_VIEW_TYPE);
  }

  @Test public void shouldAddElementToList() {
    adapter.add(ANY_OBJECT);

    verify(mockedList).add(ANY_OBJECT);
  }

  @Test public void shouldAddAllElementsToList() {
    adapter.addAll(ANY_OBJECT_COLLECTION);

    verify(mockedList).addAll(ANY_OBJECT_COLLECTION);
  }

  @Test public void shouldRemoveElementFromList() {
    adapter.remove(ANY_OBJECT);

    verify(mockedList).remove(ANY_OBJECT);
  }

  @Test public void shouldRemoveAllElementsFromList() {
    adapter.removeAll(ANY_OBJECT_COLLECTION);

    verify(mockedList).removeAll(ANY_OBJECT_COLLECTION);
  }

  @Test public void shouldClearElementsFromList() {
    adapter.clear();

    verify(mockedList).clear();
  }

  @Test public void shouldGetRendererAndRenderIt() {
    when(mockedList.get(ANY_POSITION)).thenReturn(ANY_OBJECT);
    when(mockedRendererBuilder.build()).thenReturn(mockedRenderer);

    adapter.instantiateItem(mockedParent, ANY_POSITION);

    verify(mockedRenderer).render();
  }

  @Test public void shouldSetAdapteeCollection() {
    ListAdapteeCollection collection = new ListAdapteeCollection();
    collection.add("test");
    VPRendererAdapter<Object> adapter = new VPRendererAdapter<Object>(mockedRendererBuilder);

    adapter.setCollection(collection);

    assertArrayEquals(collection.toArray(), ((ListAdapteeCollection) adapter.getCollection()).toArray());
  }

  @Test public void shouldSetList() {
    VPRendererAdapter<Object> adapter = new VPRendererAdapter<Object>(mockedRendererBuilder);

    adapter.setList(mockedList);

    assertEquals(mockedList, adapter.getList());
  }

  @Test public void shouldBeEmptyWhenItsCreatedWithJustARendererBuilder() {
    VPRendererAdapter<Object> adapter = new VPRendererAdapter<Object>(mockedRendererBuilder);

    assertEquals(0, adapter.getCount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenSetNullCollection() {
    VPRendererAdapter<Object> adapter = new VPRendererAdapter<Object>(mockedRendererBuilder);

    adapter.setCollection(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenSetNullList() {
    VPRendererAdapter<Object> adapter = new VPRendererAdapter<Object>(mockedRendererBuilder);

    adapter.setList(null);
  }

  private void initializeMocks() {
    MockitoAnnotations.initMocks(this);
    when(mockedParent.getContext()).thenReturn(RuntimeEnvironment.application);
  }

  private void initializeVPRendererAdapter() {
    adapter = new VPRendererAdapter<>(mockedRendererBuilder, mockedList);
    adapter = spy(adapter);
  }
}
