/**
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Baidu company (the "License");
 * you may not use this file except in compliance with the License.
 *
 */
package com.baidu.bjf.remoting.protobuf.idlgenerate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.IDLProxyObject;
import com.baidu.bjf.remoting.protobuf.ProtobufIDLGenerator;
import com.baidu.bjf.remoting.protobuf.ProtobufIDLProxy;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.enumeration.EnumAttrPOJO;
import com.baidu.bjf.remoting.protobuf.enumeration.EnumPOJOClass;

/**
 * 
 * Test class for enum idl proxy
 *
 * @author xiemalin
 * @since 1.4.0
 */
public class EnumIDLGeneratorTest {

    @Test
    public void testEnumIDLProxy() throws IOException {
        
        String idl = ProtobufIDLGenerator.getIDL(EnumPOJOClass.class);
        IDLProxyObject idlProxyObject = ProtobufIDLProxy.createSingle(idl);
        
        idlProxyObject.put("enumAttr", "STRING");
        
        byte[] bytes = idlProxyObject.encode();
        Codec<EnumPOJOClass> codec = ProtobufProxy.create(EnumPOJOClass.class);
        EnumPOJOClass enumPOJOClass = codec.decode(bytes);
        Assert.assertEquals(enumPOJOClass.enumAttr.value(), EnumAttrPOJO.STRING.value());
    }
    
    @Ignore
    @Test
    public void testComplextIDLProxy() throws IOException {
        InputStream fis = EnumIDLGeneratorTest.class.getResourceAsStream("si_product_biz.proto");

        Map<String, IDLProxyObject> map = ProtobufIDLProxy.create(fis);
        Assert.assertNotNull(map);
        Assert.assertEquals(23, map.size());
        
        boolean containsKey = map.containsKey("ProductAuditRejectRequest");
        Assert.assertTrue(containsKey);
        
        IDLProxyObject idlProxyObject = map.get("ProductAuditRejectRequest");
        
        idlProxyObject.put("userid", 500);
        idlProxyObject.put("head.reserved", 100);
        
        
        byte[] bytes = idlProxyObject.encode();

        
        IDLProxyObject decodeProxyObject = idlProxyObject.decode(bytes);
        Assert.assertEquals(500, decodeProxyObject.get("userid"));
        Assert.assertEquals(100, decodeProxyObject.get("head.reserved"));
    }
}
