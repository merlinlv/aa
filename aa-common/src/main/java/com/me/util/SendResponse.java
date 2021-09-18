package com.me.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
 public class SendResponse {
    private int code;
    private String msg;
    private Data data;



    @Override
    public String toString() {
        return "code=" + code + ", "
                + "msg=" + msg + ", "
                + "data=" + data;
    }
    @Setter
    @Getter
    class Data {
        private int result;
        private String requestId;


        @Override
        public String toString() {
            return "result=" + result + ", "
                    + "requestId=" + requestId ;
        }
    }
}
