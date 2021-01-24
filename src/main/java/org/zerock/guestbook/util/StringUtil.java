package org.zerock.guestbook.util;

public final class StringUtil {

    /**
     * 빈 문자열인지 체크
     * @param target
     * @return
     */
    public static boolean isEmpty(final String target) {
        return target == null || target.length() == 0;
    }

    /**
     * 빈 문자열이 아닌지 체크
     * @param target
     * @return
     */
    public static boolean isNotEmpty(final String target) {
        return !isEmpty(target);
    }
}
