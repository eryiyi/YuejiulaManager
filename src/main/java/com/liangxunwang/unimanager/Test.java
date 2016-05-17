package com.liangxunwang.unimanager;

import java.util.Random;

/**
 * Created by zhl on 2016/5/12.
 */
public class Test {
    public static void main(String[] args){

        Random random = new Random();

        int num = -1 ;

        while(true) {

            num = (int)(random.nextDouble()*(100000 - 10000) + 10000);

            if(!(num+"").contains("1")) break ;

        }

        System.out.println(num);

    }

}
