package com.walmart.code;

public interface DocIds {
    /**
     * return the next id in sequence, -1 if at end of data.
     * The ids should be in sorted order (from lower to higher) to facilitate * the query distribution into multiple containers.
     */
    static final short END_OF_IDS = -1;;
    short nextId();;
}
