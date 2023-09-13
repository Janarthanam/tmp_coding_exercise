package com.walmart.code;

import com.walmart.code.impl.RangeContainerFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class RangeQueryBasicTest {
    private  RangeContainer rc;;
    @BeforeEach
    public void setUp(){
        RangeContainerFactory rf = new RangeContainerFactoryImpl();;
        this.rc = rf.createContainer(new long[]{10,12,17,21,2,15,16});;
    }
    @Test
    public void runARangeQuery() {
        DocIds ids = rc.findIdsInRange(14, 17, true, true);
        assertEquals(2, ids.nextId());
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(DocIds.END_OF_IDS, ids.nextId());
        ids = rc.findIdsInRange(14, 17, true, false);
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(DocIds.END_OF_IDS, ids.nextId());
        ids = rc.findIdsInRange(20, Long.MAX_VALUE, false, true);
        assertEquals(3, ids.nextId());
        assertEquals(DocIds.END_OF_IDS, ids.nextId());
    }
}
