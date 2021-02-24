package com.cbk.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
    public static String getPinYin(String name) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        StringBuilder sb = new StringBuilder();
        for (char ch : name.toLowerCase().toCharArray()) {
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                if (pinyinArray == null || pinyinArray.length == 0) {
                    sb.append(ch);
                    continue;
                }
                sb.append(pinyinArray[0]);
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public static String getPinYinFirst(String name) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        StringBuilder sb = new StringBuilder();
        for (char ch : name.toLowerCase().toCharArray()) {
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                if (pinyinArray == null || pinyinArray.length == 0) {
                    sb.append(ch);
                    continue;
                }
                sb.append(pinyinArray[0].charAt(0));
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getPinYinFirst("我说话特别可乐，和你一样，我喜欢和面，very good！"));
    }
}
