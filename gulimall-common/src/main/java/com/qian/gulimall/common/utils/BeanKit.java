/**
 * BeanKit.java Sep 21, 2016
 */
package com.qian.gulimall.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <b>BeanKit</b> is 对象转换
 * </p>
 *
 * @since Sep 21, 2016
 * @author QIAN
 */
public class BeanKit {

	protected static Logger log = LoggerFactory.getLogger(BeanKit.class);

	static {

		Converter stringDateConverter  = new Converter() {

			private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public Object convert(Class aClass, Object value) {
				if(value == null){
					return null;
				}

				if("java.lang.String".equals(aClass.getName())){
					if(value instanceof Date){
						return sdf.format(value);
					}

					if(value instanceof Long){
						return String.valueOf(value);
					}

					if(value instanceof Integer){
						return String.valueOf(value);
					}

					if(value instanceof Double){
						return String.valueOf(value);
					}

					if(value instanceof Float){
						return String.valueOf(value);
					}

					if(value instanceof String){
						return value;
					}
				}

				if("java.util.Date".equals(aClass.getName())){

					if(value instanceof Date){
						return value;
					}

					if (value instanceof Long) {
						Long longValue = (Long) value;
						return new Date(longValue.longValue());
					}

					if (value instanceof String) {
						String valueStr = (String)value;
						Date dateTime = null;

						try {
							String regexp1 = "([0-9]{4})-([0-1][0-9])-([0-3][0-9])T([0-2][0-9]):([0-6][0-9]):([0-6][0-9])";
							String regexp2 = "([0-9]{4})-([0-1][0-9])-([0-3][0-9]) ([0-2][0-9]):([0-6][0-9]):([0-6][0-9])";
							String regexp3 = "([0-9]{4})-([0-1][0-9])-([0-3][0-9])";
							String regexp4 = "([0-9]{4})-([0-1][0-9])-([0-3][0-9]) ([0-2][0-9]):([0-6][0-9])";
							if(valueStr.matches(regexp1)){
                                valueStr = valueStr.split("T")[0]+" "+valueStr.split("T")[1];
                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                dateTime = sdf.parse(valueStr);
                                return dateTime;
                            }else if(valueStr.matches(regexp2)){
                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                dateTime = sdf.parse(valueStr);
                                return dateTime;
                            }else if(valueStr.matches(regexp3)){
                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                dateTime = sdf.parse(valueStr);
                                return dateTime;
                            } else if(valueStr.matches(regexp4)){
								DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								dateTime = sdf.parse(valueStr);
								return dateTime;
							} else{
                                return valueStr;
                            }
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
				return value;
			}
		};

		ConvertUtils.register(stringDateConverter, Date.class);
		ConvertUtils.register(stringDateConverter, String.class);
	}

	/**
	 * 对象复制属性，转换。转换失败会返回 null。
	 * 工具方法，尽量代码层面保证功能正常。
	 * @param clazz 需要转换类型
	 * @param orig 源数据对象
	 * @return
	 */
	public static <T> T convertBean(Class<T> clazz, Object orig) {
		try {
			T newObj = clazz.newInstance();
			if(orig != null){
				BeanUtils.copyProperties(newObj, orig);
			}
			return newObj;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("beanConvert-err", e);
		}
		return null;
	}

	/**
	 * 转换为列表，转换失败的对象不会被返回。
	 * 工具方法，尽量代码层面保证功能正常。
	 * @param clazz
	 * @param list
	 * @return
	 */
	public static <T> List<T> convertList(Class<T> clazz, List<?> list) {
		List<T> newList = new ArrayList<T>();
		if(list != null && list.size() > 0){
			for (Object obj : list) {
				T newObj = convertBean(clazz, obj);
				if (newObj != null) {
					newList.add(newObj);
				}
			}
		}
		return newList;
	}


}
