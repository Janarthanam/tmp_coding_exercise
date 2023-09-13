package com.walmart.code.impl;

import com.walmart.code.DocIds;
import com.walmart.code.RangeContainer;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class RangeContainerImpl implements RangeContainer {
    private final NavigableMap<Long,SortedSet<Short>> index = new TreeMap<>();

    public RangeContainerImpl(final long[] data) {
        //go through and index based on the values
        for (short i = 0; i < data.length; i++) {
            var v = index.get(data[i]);
            if (v == null) {
                index.put(data[i], new ConcurrentSkipListSet<>());
            }
            index.get(data[i]).add(i);
        }
    }

    @Override
    public DocIds findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        //use the submap to shorten the range search. this will then be O(n) on the range
        // rather than on the data. This can be a b-tree index on file in an extended implementation.
        var range = index.subMap(fromValue, fromInclusive, toValue, toInclusive);
        //sort the already sorted sets.
        // we can do better here. Individual sets are already sorted. This is an n way merge
        var sortedRange = range.values().stream().flatMap(Collection::stream).sorted().toList()
                .iterator();
        return (DocIds) () -> {
            if (!sortedRange.hasNext()) {
                return DocIds.END_OF_IDS;
            }
            return sortedRange.next();
        };
    }
}