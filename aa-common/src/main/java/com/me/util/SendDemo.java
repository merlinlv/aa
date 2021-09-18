package com.me.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SendDemo {
    private static final String URI_SEND_SMS = "https://sms.dun.163.com/v2/sendsms";

    /**
     * SECRET_ID 和 SECRET_KEY 是产品密钥。可以登录易盾官网找到自己的凭证信息。请妥善保管，避免泄露。
     */
    private static String SECRET_ID = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static String SECRET_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

    /**
     * 示例：发送一条验证码短信
     */
    public static void sendOtp() {
        // 这是你的 国内验证码短信 业务的ID。可以登录易盾官网查看此业务ID。
        String businessId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        // 这是你事先创建好的模板，且已通过审核。
        String templateId = "xxxxx";
        // 这是你自己的业务系统生成的验证码。如果你希望由易盾生成验证码，并通过验证码校验接口来验证，请参考 OptVerifyDemo。
        String code = "123456";
        // 这是收信方号码。如，134开头的号码一般是中国移动的号码。
        String to = "xxxxxxxxxxx";

        // 此处假设你的模板中只有验证码这一个变量，且变量名为 code。如，模板内容为 “您的验证码为${code}，5分钟内有效，请勿泄露。”
        Map<String, String> variables = new HashMap<>(4);
        variables.put("code", code);

        Map<String, String> param = createParam(businessId, templateId, variables, to);
        SendResponse response = send(param);

        System.out.println("response: " + response) ;
    }

    /**
     * 示例：发送一条通知短信
     */
    public static void sendArn() {
        // 这是你的 国内通知类短信 业务的ID。可以登录易盾官网查看此业务ID。
        String businessId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        // 这是你事先创建好的模板，且已通过审核。
        String templateId = "xxxxx";
        // 这是收信方号码。如，134开头的号码一般是中国移动的号码。
        String to = "xxxxxxxxxxx";

        // 此处假设你的模板中只有orderId这一个变量。如，模板内容为 “您的订单已发货，订单号为 ${orderId}。”
        Map<String, String> variables = new HashMap<>(4);
        variables.put("orderId", "1020304050");

        Map<String, String> param = createParam(businessId, templateId, variables, to);
        SendResponse response = send(param);

        System.out.println("response: " + response);
    }

    /**
     * 示例：发送一条营销短信
     */
    public static void sendMkt() {
        // 这是你的 国内营销类短信 业务的ID。可以登录易盾官网查看此业务ID。
        String businessId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        // 这是你事先创建好的模板，且已通过审核。
        String templateId = "xxxxx";
        // 这是收信方号码。如，134开头的号码一般是中国移动的号码。
        String to = "xxxxxxxxxxx";

        // 此处假设你的模板没有变量。如，模板内容为 “即日起至12月底，每日登录APP签到，就有机会参与新年抽奖活动。”
        Map<String, String> variables = Collections.emptyMap();

        Map<String, String> param = createParam(businessId, templateId, variables, to);
        SendResponse response = send(param);

        System.out.println("response: " + response);
    }

    /**
     * 示例：发送一条国际验证码短信
     */
    public static void sendInternationalOtp() {
        // 这是你的 国际验证码短信 业务的ID。可以登录易盾官网查看此业务ID。
        String businessId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        // 这是你事先创建好的模板，且已通过审核。
        String templateId = "xxxxx";
        // 这是你自己的业务系统生成的验证码。如果你希望由易盾生成验证码，并通过验证码校验接口来验证，请参考 OptVerifyDemo。
        String code = "123456";
        // 这是收信方号码。不包含国际电话区号。
        String to = "xxxxxxxxxxx";
        // 这是收信方的国际电话区号。如，美国是1，英国是44，法国是33，俄罗斯是79。
        String countryCallingCode = "1";

        // 此处假设你的模板中只有验证码这一个变量，且变量名为 code。如，模板内容为 “Your verification code is ${code}, valid for 5 minutes.”
        Map<String, String> variables = new HashMap<>(4);
        variables.put("code", code);

        Map<String, String> param = createParamForInternational(
                businessId, templateId, variables, to, countryCallingCode);
        SendResponse response = send(param);

        System.out.println("response: " + response);
    }

    private static SendResponse send(Map<String, String> params) {
        return RequestUtil.postForEntity(URI_SEND_SMS, params, SendResponse.class);
    }

    /**
     * 构建国内短信的请求参数
     *
     * @param businessId 业务ID
     * @param templateId 短信模板ID。你需要先在易盾官网创建模板并通过审核后才能使用。模板ID需要与业务ID匹配。即，该模板属于目标业务。
     * @param variables  短信模板中的变量值。如，你的模板内容为 “您的验证码为${code}，有效期${time}分钟。”，则此参数需指明 code 和 time 的值。
     * @param to         收信方的号码。如，134开头的号码一般是中国移动的号码。
     */
    private static Map<String, String> createParam(
            String businessId, String templateId, Map<String, String> variables, String to) {
        return createSendParam(businessId, templateId, variables, to, null);
    }

    /**
     * 构建国际短信的请求参数
     *
     * @param businessId         业务ID
     * @param templateId         短信模板ID。你需要先在易盾官网创建模板并通过审核后才能使用。模板ID需要与业务ID匹配。即，该模板属于目标业务。
     * @param variables          短信模板中的变量值。如，你的模板内容为 “Your verification code is ${code}, valid for ${time} minutes.”，则此参数需指明 code
     *                           和 time 的值。
     * @param to                 收信方的号码。不包含国际电话区号。
     * @param countryCallingCode 收信方号码的国际电话区号。如，美国是1，英国是44，法国是33，俄罗斯是79。
     */
    private static Map<String, String> createParamForInternational(
            String businessId, String templateId, Map<String, String> variables, String to, String countryCallingCode) {
        return createSendParam(businessId, templateId, variables, to, countryCallingCode);
    }

    private static Map<String, String> createSendParam(
            String businessId, String templateId, Map<String, String> variables, String to, String countryCallingCode) {
        Map<String, String> params = new HashMap<>();

        params.put("nonce", ParamUtil.createNonce());
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("version", "v2");

        params.put("secretId", SECRET_ID);
        params.put("businessId", businessId);

        params.put("templateId", templateId);
        params.put("mobile", to);

        // 如果要发送国际短信，则需要指明国际电话区号。如果不是国际短信，则不要指定此参数
        if (StringUtils.isNotBlank(countryCallingCode)) {
            params.put("internationalCode", countryCallingCode);
        }

        params.put("paramType", "json");
        params.put("params", ParamUtil.serializeVariables(variables));

        // 在最后一步生成此次请求的签名
        params.put("signature", ParamUtil.genSignature(SECRET_KEY, params));

        return params;
    }
}
