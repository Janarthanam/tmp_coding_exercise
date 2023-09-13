package com.walmart.code.impl;

import com.walmart.code.RangeContainer;
import com.walmart.code.RangeContainerFactory;

public class RangeContainerFactoryImpl implements RangeContainerFactory {
    public RangeContainer createContainer(long[] data) {
        return new RangeContainerImpl(data);
    }    
}
