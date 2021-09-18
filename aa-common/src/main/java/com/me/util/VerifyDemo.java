package com.me.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VerifyDemo {

    private static final String URI_SEND_SMS = "https://sms.dun.163.com/v2/sendsms";
    private static final String URI_VERIFY_OTP = "https://sms.dun.163.com/v2/verifysms";

    /**
     * SECRET_ID 和 SECRET_KEY 是产品密钥。可以登录易盾官网找到自己的凭证信息。请妥善保管，避免泄露。
     */
    private static String SECRET_ID = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static String SECRET_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" ;

    /**
     * 示例：发送验证码短信 并 校验用户的回填的验证码是否正确
     */
    public static void sendAndVerifyOtp() {
        // 发送验证码短信：指明由易盾生成验证码
        SendResponse sendResponse = sendOtp();
        String requestId = sendResponse.getData().getRequestId();

        // 过了一段时间（很短），你收到了用户回填的验证码
        // code 就是你的用户填写的验证码。你的业务系统收到此验证码后，就可以通过下述操作验证它是否正确。
        String code = "123456";

        // 验证验证码是否正确
        VerifyResponse verifyResponse = verifyOtp(requestId, code);

        if (verifyResponse.getData().isMatch()) {
            System.out.println("你的用户填写了正确的验证码。");
        } else {
            if (verifyResponse.getData().getReasonType() == 2) {
                System.out.println("你的用户填写的验证码是错误的。");
            } else {
                System.out.println("请求ID过期或不存在。");
            }
        }
    }

    /**
     * 发送验证码短信：指明由易盾生成验证码
     */
    private static SendResponse sendOtp() {
        // 这是你的 国内验证码短信 业务的ID。可以登录易盾官网查看此业务ID。
        String businessId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        // 这是你事先创建好的模板，且已通过审核。
        String templateId = "xxxxx";
        // 这是收信方号码。如，134开头的号码一般是中国移动的号码。
        String to = "xxxxxxxxxxx";

        // 此处假设目标模板内容里只有验证码一个变量，所以没有其它变量需要指定
        Map<String, String> variables = Collections.emptyMap();

        // 发国内短信时，不指定 Country Calling Code
        Map<String, String> param = createSendParam(businessId, templateId, variables, to, null);

        SendResponse response = RequestUtil.postForEntity(URI_SEND_SMS, param, SendResponse.class);

        System.out.println("response: " + response);

        return response;
    }

    /**
     * 验证用户回填的验证码
     *
     * @param requestId 之前发送验证码短信时，易盾返回的请求ID。
     * @param code 用户回填的验证码。
     */
    private static VerifyResponse verifyOtp(String requestId, String code) {
        // 业务ID。与前述发短信时所用的业务ID相同。
        String businessId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

        Map<String, String> param = createVerifyParam(businessId, requestId, code);

        VerifyResponse response = RequestUtil.postForEntity(URI_VERIFY_OTP, param, VerifyResponse.class);

        System.out.println("response: " + response);

        return response;
    }

    /**
     * 构建发送验证码短信的请求参数：指明由易盾生成验证码
     */
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

        // 指明由易盾生成验证码：
        // codeName 表示目标模板内容中，验证码的占位符变量名。如，模板内容为 “您的验证码为${code}，5分钟内有效，请勿泄露。”，则 codeName 的值应为 code
        params.put("codeName", "code");
        // codeLen 表示验证码的数字个数
        params.put("codeLen", "6");
        // codeValidSec 表示验证码的有效期。单位：秒
        params.put("codeValidSec", "300");

        // 在最后一步生成此次请求的签名
        params.put("signature", ParamUtil.genSignature(SECRET_KEY, params));

        return params;
    }

    /**
     * 构建验证码校验请求的参数
     */
    private static Map<String, String> createVerifyParam(String businessId, String requestId, String code) {
        Map<String, String> params = new HashMap<>();

        params.put("nonce", ParamUtil.createNonce());
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("version", "v2");

        params.put("secretId", SECRET_ID);
        params.put("businessId", businessId);

        params.put("requestId", requestId);
        params.put("code", code);

        // 在最后一步生成此次请求的签名
        params.put("signature", ParamUtil.genSignature(SECRET_KEY, params));

        return params;
    }
}