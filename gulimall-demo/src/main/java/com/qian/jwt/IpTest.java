package com.qian.jwt;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * IpTest is
 *
 * @author QIAN
 * Date 2020/06/02
 * Time 13:43
 */
public class IpTest {

    public static void main(String[] args) {

        List<Integer> integers = Arrays.asList(1, 23, 5, 6);
        Collections.sort(integers, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                if (o2 == 23) {
                    System.out.println(2222222);
                    return 0;
                }
                System.out.println("o1 " + o1 + " , o2 " + o2);
                return o1 > o2 ? 1 : -1;
            }
        });
        System.out.println(integers);

        Map<String, Double> map = new HashMap<String, Double>();
        map.put("AKE", 1D);
        map.put("PMC", 2D);
        map.put("BLK", 5D);
        System.out.println(map);

//        for (int i = 0; i < 10000; i++) {
//            System.out.println(getRandomIp());
//        }
    }
    private static String getRandomIp() {
        //ip范围
        int[][] range = {{607649792, 608174079},//36.56.0.0-36.63.255.255
                {1038614528, 1039007743},//61.232.0.0-61.237.255.255
                {1783627776, 1784676351},//106.80.0.0-106.95.255.255
                {2035023872, 2035154943},//121.76.0.0-121.77.255.255
                {2078801920, 2079064063},//123.232.0.0-123.235.255.255
                {-1950089216, -1948778497},//139.196.0.0-139.215.255.255
                {-1425539072, -1425014785},//171.8.0.0-171.15.255.255
                {-1236271104, -1235419137},//182.80.0.0-182.92.255.255
                {-770113536, -768606209},//210.25.0.0-210.47.255.255
                {-569376768, -564133889}, //222.16.0.0-222.95.255.255
        };
        int index = new Random().nextInt(10);
        return num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
    }
    /*
     * 将十进制转换成ip地址
     */
    public static String num2ip(int ip) {
        int[] b = new int[4];
        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        return Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);
    }
}
