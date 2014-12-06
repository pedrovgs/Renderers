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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Generic AdapteeCollection implementation based on ArrayList<T>. Library clients can use this
 * class instead of create his own AdapteeCollections.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class ListAdapteeCollection<T> implements AdapteeCollection<T> {

  private final List<T> list;

  public ListAdapteeCollection() {
    this(new ArrayList<T>());
  }

  public ListAdapteeCollection(List<T> list) {
    this.list = new ArrayList<T>();
    this.list.addAll(list);
  }

  @Override public int size() {
    return list.size();
  }

  @Override public T get(int index) {
    return list.get(index);
  }

  @Override public void add(T element) {
    this.list.add(element);
  }

  @Override public void remove(T element) {
    this.list.remove(element);
  }

  @Override public void addAll(Collection<T> elements) {
    this.list.addAll(elements);
  }

  @Override public void removeAll(Collection<T> elements) {
    this.list.removeAll(elements);
  }

  @Override public void clear() {
    this.list.clear();
  }
}
