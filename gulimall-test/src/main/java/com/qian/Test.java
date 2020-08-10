package com.qian;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * Test is
 *
 * @author QIAN
 * Date 2020/05/07
 * Time 08:51
 */
public class Test {

    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());
        Map <String, String> map = new Hashtable <String, String>();
        map.put("a", "b");
        map.get("a");

        Map <String, String> map1 = new HashMap <String, String>();
        map1.put("a", "b");

        Map <String, String> aa = new ConcurrentHashMap <String, String>();
        aa.put("a", "b");
    }
}
