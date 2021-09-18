package com.me.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
 public class VerifyResponse {
    private int code;
    private String msg;
    private Data data;


    @Override
    public String toString() {
        return "code=" + code + ", "
                + "msg=" + msg + ", "
                + "data=" + data ;
    }
    @Setter
    @Getter
    public static class Data {
        private boolean match;
        private int reasonType;

        @Override
        public String toString() {
            return "match=" + match + ", "
                    + "reasonType=" + reasonType;
        }
    }
}
