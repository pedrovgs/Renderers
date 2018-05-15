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

import android.arch.paging.PagedList;
import android.support.v7.util.DiffUtil;
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


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Config(sdk = 27) @RunWith(RobolectricTestRunner.class) public class PagedRendererAdapterTest {

  private static final int ANY_POSITION = 2;
  private static final Object ANY_OBJECT = new Object();
  private static final int ANY_ITEM_VIEW_TYPE = 3;

  private PagedRendererAdapter<Object> adapter;

  @Mock private RendererBuilder mockedRendererBuilder;
  @Mock private ViewGroup mockedParent;
  @Mock private ObjectRenderer mockedRenderer;
  @Mock private RendererViewHolder mockedRendererViewHolder;
  @Mock private DiffUtil.ItemCallback mockDiffCallback;
  @Mock private PagedList mockPagedList;


  @Before public void setUp() {
    initializeMocks();
    initializeRVRendererAdapter();
  }

  @Test public void shouldReturnPositionAsItemId() {
    assertEquals(ANY_POSITION, adapter.getItemId(ANY_POSITION));
  }

  @Test public void shouldDelegateIntoRendererBuilderToGetItemViewType() {
    when(mockedRendererBuilder.getItemViewType(ANY_OBJECT)).thenReturn(ANY_ITEM_VIEW_TYPE);

    assertEquals(ANY_ITEM_VIEW_TYPE, adapter.getItemViewType(ANY_POSITION));
  }

  @Test public void shouldBuildRendererUsingAllNeededDependencies() {
    when(mockedRendererBuilder.buildRendererViewHolder()).thenReturn(mockedRendererViewHolder);

    adapter.onCreateViewHolder(mockedParent, ANY_ITEM_VIEW_TYPE);

    verify(mockedRendererBuilder).withParent(mockedParent);
    verify(mockedRendererBuilder).withLayoutInflater((LayoutInflater) notNull());
    verify(mockedRendererBuilder).withViewType(ANY_ITEM_VIEW_TYPE);
    verify(mockedRendererBuilder).buildRendererViewHolder();
  }

  @Test public void shouldGetRendererFromViewHolderAndCallUpdateRendererExtraValuesOnBind() {
    when(mockedRendererViewHolder.getRenderer()).thenReturn(mockedRenderer);

    adapter.onBindViewHolder(mockedRendererViewHolder, ANY_POSITION);

    verify(adapter).updateRendererExtraValues(ANY_OBJECT, mockedRenderer, ANY_POSITION);
  }

  @Test(expected = NullRendererBuiltException.class)
  public void shouldThrowNullRendererBuiltException() {
    adapter.onCreateViewHolder(mockedParent, ANY_ITEM_VIEW_TYPE);
  }

  @Test public void shouldGetRendererFromViewHolderAndUpdateContentOnBind() {
    when(mockedRendererViewHolder.getRenderer()).thenReturn(mockedRenderer);

    adapter.onBindViewHolder(mockedRendererViewHolder, ANY_POSITION);

    verify(mockedRenderer).setContent(ANY_OBJECT);
  }

  @Test public void shouldGetRendererFromViewHolderAndRenderItOnBind() {
    when(mockedRendererViewHolder.getRenderer()).thenReturn(mockedRenderer);

    adapter.onBindViewHolder(mockedRendererViewHolder, ANY_POSITION);

    verify(mockedRenderer).render();
  }


  @Test public void shouldBeEmptyWhenItsCreatedWithJustARendererBuilder() {
    RVRendererAdapter<Object> adapter = new RVRendererAdapter<Object>(mockedRendererBuilder);

    assertEquals(0, adapter.getItemCount());
  }


  private void initializeMocks() {
    MockitoAnnotations.initMocks(this);
    when(mockedParent.getContext()).thenReturn(RuntimeEnvironment.application);
  }

  private void initializeRVRendererAdapter() {
    when(mockPagedList.get(anyInt())).thenReturn(ANY_OBJECT);
    adapter = new PagedRendererAdapter<>(mockedRendererBuilder, mockDiffCallback);
    adapter.submitList(mockPagedList);
    adapter = spy(adapter);
  }
}
