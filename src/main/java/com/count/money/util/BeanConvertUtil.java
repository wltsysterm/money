package com.count.money.util;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.plugins.Page;
import com.count.money.core.common.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaBean转换工具
 */
public class BeanConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(BeanConvertUtil.class);
    /**
     * 根据filed的set,get方法，将源对象转换为目标对象
     * @param source 源对象
     * @param target 目标对象类型
     * @return
     * @throws InvocationTargetException 
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T beanConvert(Object source,Class<T> target) throws Exception {
       try {
           if(source == null) {
               return null;
           }
           T instance = target.newInstance();
           BeanUtils.copyProperties(source, instance);
           return instance;
       } catch (Exception e) {
           logger.warn("bean转换异常",e);
           throw new Exception("bean转换异常");
       }
    }
    /**
     * 将源对象列表转换为目标对象列表
     * @param source 源对象列表
     * @param target 目标对象类型
     * @return
     */
    public static <T> List<T> beanListConvert(List<?> source,Class<T> target) throws Exception {
        try{
            List<T> targetList = new ArrayList<>();
            if(source == null || source.size() == 0) {
                return targetList; 
            }
            
            for (Object object : source) {
                T instance = target.newInstance();
                BeanUtils.copyProperties(object, instance);
                targetList.add(instance);
            }
            return targetList;
        } catch (Exception e) {
            logger.warn("bean转换异常",e);
            throw new Exception("bean转换异常");
        }
    }
    /**
     * 将对象转为map
     * @param bean
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static Map<String,Object> convertBean2Map(Object bean) throws Exception {
        try {
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(bean.getClass());
            Map<String,Object> returnMap = new HashMap<>();  
            for(int i = 0; i< propertyDescriptors.length; i++) {  
                PropertyDescriptor descriptor = propertyDescriptors[i];  
                String propertyName = descriptor.getName();  
                if (!propertyName.equals("class")) {  
                    Method readMethod = descriptor.getReadMethod();  
                    Object result = readMethod.invoke(bean, new Object[0]);  
                    if (result != null) {  
                        returnMap.put(propertyName, result);  
                    } 
                }  
            }  
            return returnMap;  
        } catch (Exception e) {
            logger.warn("bean转换异常",e);
            throw new Exception("bean转换异常");
        }
    }
    /**
     * 将Map转Bean对象
     * @param map
     * @param target
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static <T> T convertMap2Bean(Map<String,Object> map,Class<T> target) throws Exception {
        try {
            T obj = target.newInstance(); 
            // 给 JavaBean 对象的属性赋值  
            PropertyDescriptor[] propertyDescriptors =  BeanUtils.getPropertyDescriptors(target);
            for (int i = 0; i< propertyDescriptors.length; i++) {  
                PropertyDescriptor descriptor = propertyDescriptors[i];  
                String propertyName = descriptor.getName();  
      
                if (map.containsKey(propertyName)) {  
                    Object[] args = new Object[]{ map.get(propertyName) };  
                    descriptor.getWriteMethod().invoke(obj, args);  
                }  
            }  
            return obj; 
        } catch (Exception e) {
            logger.warn("bean转换异常",e);
            throw new Exception("bean转换异常");
        }
    } 
    
    /**
     * 将对象深度遍历为字符串，对象不可序列化时会抛异常
     * @param object
     * @return
     * @throws UnsupportedEncodingException
     */
//    public static String Obj2String(Object object) throws UnsupportedEncodingException{
//        SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;
//        byte[] bytes = JSON.toJSONBytes(object, feature);
//        return new String(bytes, "UTF-8");
//    }
    
	/**
	 * 分页对象转换
	 * @param page
	 * @return
	 */
    public static PageResult pageConvert(Page page) throws Exception {
       try {
           if(page == null) {
               return null;
           }
           PageResult pr = new PageResult();
           pr.setRows(page.getRecords());
           pr.setTotal(page.getTotal());
           return pr;
       } catch (Exception e) {
           logger.warn("page转换异常",e);
           throw new Exception("bean转换异常");
       }
    }

    /**
     * 分页对象转换
     * @param list
     * @return
     */
    public static PageResult listToPageConvert(List list) throws Exception {
        try {
            if(list == null) {
                return null;
            }
            PageResult pr = new PageResult();
            pr.setRows(list);
            pr.setTotal(list.size());
            return pr;
        } catch (Exception e) {
            logger.warn("page转换异常",e);
            throw new Exception("page转换异常");
        }
    }

    /**
     * 分页对象转换
     * @param <T>
     * @param page
     * @return
     */
    public static <T> PageResult pageConvert(Page page,Class<T> target) throws Exception {
        try {
            if(page == null) {
                return null;
            }
            PageResult pr = new PageResult();
            pr.setRows(BeanConvertUtil.beanListConvert(page.getRecords(),target));
            pr.setTotal(page.getTotal());
            return pr;
        } catch (Exception e) {
            logger.warn("page转换异常",e);
            throw new Exception("page转换异常");
        }
    }
}