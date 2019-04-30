package com.wxh.bootMybatis.utils;

import java.lang.reflect.Method;

public class Reflect {
	/**
	 * @Description 反射调用方法
	 * 
	 * @param newObj 实例化的一个对象
	 * @param methodName 要调用的对象的方法名
	 * @param args 调用目标方法所需要的参数
	 * 
	 * @Date 2019-04-23 pm
	 * 
	 * @Author wxh
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object invokeMethod(Object newObj, String methodName, Object[] args) throws Exception {
		Class ownerClass = newObj.getClass(); 
        Class[] argsClass = new Class[args.length]; 
        for (int i = 0, j = args.length; i < j; i++) { 
            argsClass[i] = args[i].getClass(); 
        } 
        Method method = ownerClass.getMethod(methodName, argsClass); 
        return method.invoke(newObj, args); 
	}
}
