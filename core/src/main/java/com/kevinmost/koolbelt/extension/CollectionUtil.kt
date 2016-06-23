package com.kevinmost.koolbelt.extension

// Thanks to http://stackoverflow.com/a/37936456 and to Klutter

private class ImmutableIterator<T>(private val inner: Iterator<T>) : Iterator<T> by inner

private class ImmutableListIterator<T>(private val inner: ListIterator<T>) : ListIterator<T> by inner

private class ImmutableCollection<T>(private val delegate: Collection<T>) : Collection<T> by delegate {
  override fun iterator(): Iterator<T> {
    return delegate.iterator()
  }
}


private class ImmutableList<T>(private val inner: List<T>) : List<T> by inner {
  override fun subList(fromIndex: Int, toIndex: Int): List<T> {
    return inner.subList(fromIndex, toIndex).toImmutable()
  }

  override fun listIterator(index: Int): ListIterator<T> {
    return inner.listIterator(index).toImmutable()
  }

  override fun listIterator(): ListIterator<T> {
    return inner.listIterator().toImmutable()
  }

  override fun iterator(): Iterator<T> {
    return inner.iterator().toImmutable()
  }
}

private class ImmutableMap<K, V>(private val inner: Map<K, V>) : Map<K, V> by inner {
  override val entries: Set<Map.Entry<K, V>>
    get() = inner.entries.toImmutable()

  override val keys: Set<K>
    get() = inner.keys.toImmutable()

  override val values: Collection<V>
    get() = inner.values.toImmutable()

}

private class ImmutableSet<T>(private val inner: Set<T>) : Set<T> by inner {
  override fun iterator(): Iterator<T> {
    return inner.iterator().toImmutable()
  }
}

fun <T> Iterator<T>.toImmutable(): Iterator<T> = if (this is Iterator<T>) this else ImmutableIterator(this)

fun <T> ListIterator<T>.toImmutable(): ListIterator<T> =
    if (this is ListIterator<T>) this else ImmutableListIterator(this)

fun <T> Collection<T>.toImmutable(): Collection<T> =
    if (this is ImmutableCollection<T>) this else ImmutableCollection(this)

fun <T> List<T>.toImmutable(): List<T> = if (this is ImmutableList<T>) this else ImmutableList(this)

fun <K, V> Map<K, V>.toImmutable(): Map<K, V> = if (this is ImmutableMap<K, V>) this else ImmutableMap(this)

fun <T> Set<T>.toImmutable(): Set<T> = if (this is ImmutableSet<T>) this else ImmutableSet(this)
