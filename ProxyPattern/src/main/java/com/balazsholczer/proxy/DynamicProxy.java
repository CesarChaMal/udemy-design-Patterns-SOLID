package com.balazsholczer.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy {
    
    public static class ImageInvocationHandler implements InvocationHandler {
        private final String filename;
        private RealImage realImage;
        
        public ImageInvocationHandler(String filename) {
            this.filename = filename;
        }
        
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("display".equals(method.getName())) {
                if (realImage == null) {
                    realImage = new RealImage(filename);
                }
                return method.invoke(realImage, args);
            }
            return null;
        }
    }
    
    public static Image createProxy(String filename) {
        return (Image) Proxy.newProxyInstance(
            Image.class.getClassLoader(),
            new Class[]{Image.class},
            new ImageInvocationHandler(filename)
        );
    }
}