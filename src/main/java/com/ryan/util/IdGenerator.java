package com.ryan.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:生成不重复随机数
 * User: Ryan
 * Time: 2018/2/26 15:49
 */
public class IdGenerator {
    public static  String getId() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String  formDate =sdf.format(date);
        String no = formDate.substring(9);
        return no;
    }

    public static void main(String args[]){
        System.out.print(IdGenerator.getId());
    }

}