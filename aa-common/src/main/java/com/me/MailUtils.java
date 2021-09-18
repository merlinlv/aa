package com.me;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

public class MailUtils {
    public static void sendMail(){
        MailAccount ma=new MailAccount();
        ma.setHost("smtp.126.com");
        ma.setPort(25);
        ma.setAuth(true);
        ma.setFrom("merlinlv@126.com");
        ma.setUser("merlinlv@126.com");
        ma.setPass("REQVJKIOLHYLHWDC");
        MailUtil.send(ma, CollUtil.newArrayList("15882247584@139.com"),"手机验证码","你的验证码："+FlowNumUtils.getFlowNum(6),false);
    }

    public static  void main(String [] args){
        sendMail() ;
    }
}
