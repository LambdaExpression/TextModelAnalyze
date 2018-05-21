package org.tcat.tools;

import java.util.*;

/**
 * 模板解析工具
 *
 * @author lin
 * @date 2018/05/20
 */
public class ModelAnalyzeUtil {


    private static final int MIN = 33;
    private static final int MAX = 94;
    private static char[] ch = null;

    private ModelAnalyzeUtil() {
    }

    /**
     * 解析
     *
     * @param model   模板
     * @param content 内容
     * @param param   解析参数F
     * @return 解析结果
     */
    public static Map<String, String> analyze(final String model, final String content, final Map<String, String> param) {
        if (isEmpty(model) || isEmpty(content)
                || param == null || param.size() == 0) {
            return new HashMap<>(4);
        }
        if (param.size() > (MAX - MIN)) {
            throw new RuntimeException("文本参数过多");
        }
        Map<String, String> tichMap = new HashMap<>(16);
        String mo = model + "";
        int i = 0;
        for (Map.Entry<String, String> entry : param.entrySet()) {
            String c = getChar(i++);
            mo = mo.replace(entry.getValue(), c);
            tichMap.put(c, entry.getKey());
        }
        DiffMatchPatch dmp = new DiffMatchPatch();
        dmp.Diff_Timeout = 0;
        LinkedList<DiffMatchPatch.Diff> diffs = dmp.diff_main(mo, content, false);
        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        for (int z = 0; z < diffs.size() - 1; z++) {
            if (DiffMatchPatch.Operation.DELETE.equals(diffs.get(z).operation)
                    && DiffMatchPatch.Operation.INSERT.equals(diffs.get(z + 1).operation)) {
                key.add(diffs.get(z).text);
                value.add(diffs.get(z + 1).text);
            }
        }
        Map<String, String> result = new HashMap<>(16);
        for (int y = 0; y < key.size(); y++) {
            result.put(tichMap.get(key.get(y)), value.get(y));
        }
        return result;
    }

    /**
     * 判断是否为空
     *
     * @param str
     * @return
     */
    private static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 获取替换用字符
     *
     * @param size
     * @return
     */
    private static String getChar(int size) {
        return String.valueOf(getCh()[size]);
    }

    /**
     * 获取字符
     *
     * @return
     */
    private static char[] getCh() {
        if (ch == null) {
            synchronized (ModelAnalyzeUtil.class) {
                if (ch == null) {
                    ch = new char[MAX - MIN + 1];
                    int y = 0;
                    for (int i = MIN; i <= MAX; i++) {
                        ch[y++] = (char) i;
                    }
                }
            }
        }
        return ch;
    }

}
