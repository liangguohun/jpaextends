package com.app.utils;

import java.lang.reflect.Field;

/**
 * todo   	数据库hibernate jpa缺陷等的辅助
 * author 	liangguohun
 * datetime 2017年12月1日 下午11:21:47
 */
public class _DBHelper {
	/**
	 * todo:		更新的临时特殊处理，
	 * 因为这里没法实现动态根据只传递部分字段的对象进行更新
	 * 把记录查询出来后, 反射判断实体类那些字段是有值的更新后
	 * 执行保存。返回的是更新过后的旧对象
	 * author:		梁国魂
	 * datetime:	2017年12月1日 下午11:27:17
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T merge(T old, T newObj) throws IllegalArgumentException, IllegalAccessException{
		//得到类对象 
		Class<T> objNew = (Class<T>) newObj.getClass();  
		Class<T> objOld = (Class<T>) old.getClass();
        /* 
         * 得到类中的所有属性集合 
         */  
        Field[] fs = objNew.getDeclaredFields();
        Field[] oldfs = objOld.getDeclaredFields();
        int size = fs.length;
        for(int i = 0 ; i < size; i++){  
            Field f = fs[i];  
            //不是final修饰才行
            if(!java.lang.reflect.Modifier.isFinal(f.getModifiers())){
            	f.setAccessible(true); 			//设置些属性是可以访问的  
            	Object val = f.get(newObj);		//得到此属性的值     
                if(null!=val) {
                	Field _f_old = oldfs[i];
                    _f_old.setAccessible(true);
                    _f_old.set(old, val);
                }
            }
        }
		return old;
	};
	
}
