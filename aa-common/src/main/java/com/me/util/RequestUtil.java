package com.me.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

class RequestUtil {

    /**
     * 发起 HTTP POST 请求，提交表单
     *
     * @param uri 目标地址
     * @param params 表单数据
     * @param <R> 返回对象的类型
     */
    public static <R> R postForEntity(String uri, Map<String, String> params, Class<R> responseType) {
        try {
            String strResponse = Request.Post(uri)
                    .bodyForm(convertToFormData(params), StandardCharsets.UTF_8)
                    .execute()
                    .returnContent()
                    .asString();

            return parseResponse(strResponse, responseType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<NameValuePair> convertToFormData(Map<String, String> paramMap) {
        Form form = Form.form();
        paramMap.forEach(form::add);
        return form.build() ;
    }

    private static <R> R parseResponse(String strResponse, Class<R> responseType) {
        try {
            return JSON.parseObject(strResponse, responseType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}