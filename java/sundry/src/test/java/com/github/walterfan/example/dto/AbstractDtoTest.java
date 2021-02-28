package com.github.walterfan.example.dto;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * Created by yafan on 26/8/2017.
 */
public abstract class AbstractDtoTest<T> {

    protected abstract Class<T> getType();

    @Test
    public void testContructor() throws IllegalAccessException, InstantiationException {
        T obj = this.getType().newInstance();
        assertNotNull(obj);
    }

    @Test
    public void testSetterGetter() {
        //
    }
}
