package com.me;

import cn.hutool.core.util.RandomUtil;

public class FlowNumUtils
{   public static String getFlowNumBothLowerLetterAndNum(int len){
    return  RandomUtil.randomString(len);
    }
    public static String getFlowNumOnlyNum(int len){
        return  RandomUtil.randomNumbers(len);
    }
    public static String getFlowNumOnlyLowerLetter(int len){
        return  RandomUtil.randomString("abcdefghijklmnopqrstuvwxyz",len);
    }
    public static String getFlowNum(int len){
      return  RandomUtil.randomString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",len);
    }
    public static  void main(String [] args){
        /*for(int i=0;i<200000;i++){
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(getFlowNum());
                        }
                    }
            ).start();
        }*/
        System.out.println(getFlowNumBothLowerLetterAndNum(6)) ;
    }
}
