package org.tcat.tools;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lin
 * @date 2018/05/20
 */
public class Test {

    public static void main(String[] args) {

        String model = "%name%您好，您预订的机票订单%orderId%，航班%flight%，%starttime%-%endtime%，乘机人为：%name%，订单支付成功，请留意出票短信。";
        String content = "XXX您好，您预订的机票订单FN171128021260，航班南航 CZ3106 北京首都机场T2-广州白云机场，12月10日13:30-12月10日17:05，乘机人为：XXX，订单支付成功，请留意出票短信。";

        Map<String, String> params = new HashMap<>(16);
        params.put("name", "%name%");
        params.put("orderId", "%orderId%");
        params.put("flight", "%flight%");
        params.put("starttime", "%starttime%");
        params.put("endtime", "%endtime%");

        Map<String, String> result = ModelAnalyzeUtil.analyze(model, content, params);

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(result));
    }

}
