package com.pedrogomez.renderers;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class created to check the correct behaviour of ListAdapteeCollection.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class ListAdapteeCollectionTest {

  private AdapteeCollection<Object> collection;

  @Before public void setUp() {
    collection = new ListAdapteeCollection<Object>();
  }

  @Test public void shouldAddElement() {
    Object element = new Object();

    collection.add(element);

    assertEquals(1, collection.size());
    assertEquals(element, collection.get(0));
  }

  @Test public void shouldCountNumberOfElementsAdded() {
    collection.add(new Object());
    collection.add(new Object());

    assertEquals(2, collection.size());
  }

  @Test public void shouldRemoveElement() {
    Object element1 = new Object();
    Object element2 = new Object();

    collection.add(element1);
    collection.add(element2);
    collection.remove(element1);

    assertEquals(1, collection.size());
    assertEquals(element2, collection.get(0));
  }

  @Test public void shouldAddCollection() {
    Object element1 = new Object();
    Object element2 = new Object();
    List<Object> elements = Arrays.asList(element1, element2);

    collection.addAll(elements);

    assertEquals(2, collection.size());
    assertEquals(element1, collection.get(0));
    assertEquals(element2, collection.get(1));
  }

  @Test public void shouldRemoveCollection() {
    Object element1 = new Object();
    Object element2 = new Object();
    Object element3 = new Object();
    List<Object> elements = Arrays.asList(element1, element2);

    collection.addAll(elements);
    collection.add(element3);
    collection.removeAll(elements);

    assertEquals(1, collection.size());
    assertEquals(element3, collection.get(0));
  }

  @Test public void shouldClearCollection() {
    Object element1 = new Object();
    Object element2 = new Object();
    List<Object> elements = Arrays.asList(element1, element2);

    collection.addAll(elements);
    collection.clear();

    assertEquals(0, collection.size());
  }
}
