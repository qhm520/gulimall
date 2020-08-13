package com.qian.jwt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * GcOverHeadDemo is
 *
 * @author QIAN
 * Date 2020/05/10
 * Time 17:39
 */
public class GcOverHeadDemo {

    public static void main(String[] args) {
        int i = 0;
        List <String> list = new ArrayList <>();

        try {
            while (true) {
                list.add(String.valueOf(i).intern());
            }
        } catch (Throwable e) {
            System.out.println("********************" + i);
            e.printStackTrace();
            throw e;
        }
    }
}
