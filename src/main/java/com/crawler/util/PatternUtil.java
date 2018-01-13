package com.crawler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

    private static final String SP_MARK = "(*)";

    static final Pattern p1 = Pattern.compile("\\s+");

    static final Pattern p2 = Pattern.compile("(&nbsp;)+");

    static final Pattern p3 = Pattern.compile("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f\\xA0]");

    static final Pattern p4 = Pattern.compile("<!DOCTYPE\\s+.*?>", Pattern.DOTALL);

    static final Pattern p5 = Pattern.compile("<!--.*?-->", Pattern.DOTALL);

    static final Pattern p6 = Pattern.compile("<script\\s*>.*?<\\/script\\s*>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    static final Pattern p7 = Pattern.compile("<script\\s+.*?<\\/script\\s*>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    static final Pattern p8 = Pattern.compile("<style\\s*>.*?<\\/style\\s*>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    static final Pattern p9 = Pattern.compile("<style\\s+.*?<\\/style\\s*>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    static final Pattern p10 = Pattern.compile("</[a-zA-Z]+\\s*>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    static final Pattern p11 = Pattern.compile("<[a-zA-Z]+[/]?>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    static final Pattern p12 = Pattern.compile("<\\[a-zA-Z]+(\\s*\\[a-zA-Z]+\\s*([=]\\s*('.*?'|\\\".*?\\\"|\\[a-zA-Z]+))?)*?[/]?>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    static final Pattern p13 = Pattern.compile("<.*?>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    private static final Pattern p_img = Pattern.compile("<img", Pattern.CASE_INSENSITIVE);

    private static final Pattern p_img_1 = Pattern.compile("\\[img_begin\\]");

    private static final Pattern p_img_2 = Pattern.compile("\\[img_end\\]");

    private static final Pattern p_empty_newline = Pattern.compile("[\r\n][\\s　]*[\r\n]");

    private static final Pattern p_space = Pattern.compile("[ 　]+");

    private static final Pattern p_newline_space = Pattern.compile("\r\n[ 　]+");

    public static String getMatch(String content, String spRegex) {
        return getMatch(content, spRegex, false);
    }

    public static String getMatch(String content, String spRegex, boolean caseInsensitive) {
        Pattern[] patterns = createPattern(spRegex, caseInsensitive);
        Matcher m = patterns[0].matcher(content);
        if (m.find()) {
            String match = m.group();
            if (patterns.length > 1) {
                match = patterns[1].matcher(match).replaceFirst("");
                match = patterns[2].matcher(match).replaceFirst("");
            }
            return match;
        }
        return "";
    }


    private static Pattern[] createPattern(String spRegex, boolean caseInsensitive) {
        Pattern[] patterns = null;
        int pos = -1;
        if ((pos = spRegex.indexOf(SP_MARK)) > -1) {
            patterns = new Pattern[3];
            String subRegex_0 = spRegex.substring(0, pos);
            String subRegex_1 = spRegex.substring(pos + SP_MARK.length());
            if (caseInsensitive) {
                patterns[0] = Pattern.compile(subRegex_0 + ".*?" + subRegex_1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                patterns[1] = Pattern.compile(subRegex_0, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                patterns[2] = Pattern.compile(subRegex_1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            } else {
                patterns[0] = Pattern.compile(subRegex_0 + ".*?" + subRegex_1, Pattern.DOTALL);
                patterns[1] = Pattern.compile(subRegex_0, Pattern.DOTALL);
                patterns[2] = Pattern.compile(subRegex_1, Pattern.DOTALL);
            }
        } else {
            patterns = new Pattern[1];
            if (caseInsensitive) {
                patterns[0] = Pattern.compile(spRegex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            } else {
                patterns[0] = Pattern.compile(spRegex, Pattern.DOTALL);
            }
        }
        return patterns;
    }


    public static List<String> getMatchList(String content, String spRegex) {
        return getMatchList(content, spRegex, false, 65536);
    }

    protected static List<String> getMatchList(String content, String spRegex, boolean caseInsensitive, int maxMatchNum) {
        List<String> matchList = new ArrayList<>();
        try {
            Pattern[] patterns = createPattern(spRegex, caseInsensitive);
            Matcher matcher = patterns[0].matcher(content);
            while (matcher.find()) {
                String match = matcher.group();
                if (patterns.length > 1) {
                    match = patterns[1].matcher(match).replaceFirst("");
                    match = patterns[2].matcher(match).replaceFirst("");
                }
                matchList.add(match);
                if (matchList.size() >= maxMatchNum) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchList;
    }

    public static String toDBCase(String src) {
        char[] ch = new char[src.length()];
        for (int i = 0; i < src.length(); i++) {
            int c = (int) src.charAt(i);
            if (c >= 65281 && c <= 65374) {
                ch[i] = (char) (c - 65248);
            } else {
                ch[i] = (char) c;
            }
        }
        return new String(ch);
    }

    public static String extractForHTML(String rawHtmlCode, String substitution) {
        if (rawHtmlCode == null) {
            return "";
        }

        String text = toDBCase(rawHtmlCode);


        Matcher m = p2.matcher(text);
        text = m.replaceAll(substitution);

        m = p3.matcher(text);
        text = m.replaceAll("");

        m = p4.matcher(text);
        text = m.replaceAll(substitution);

        m = p5.matcher(text);
        text = m.replaceAll(substitution);

        m = p6.matcher(text);
        text = m.replaceAll(substitution);

        m = p7.matcher(text);
        text = m.replaceAll(substitution);

        m = p8.matcher(text);
        text = m.replaceAll(substitution);

        m = p9.matcher(text);
        text = m.replaceAll(substitution);

        m = p10.matcher(text);
        text = m.replaceAll(substitution);

        m = p11.matcher(text);
        text = m.replaceAll(substitution);

        int pos1 = text.indexOf("<img");
        int pos2 = -1;
        while (pos1 > -1) {
            pos2 = text.indexOf(">", pos1);
            text = text.substring(0, pos2) + "[img_end]"
                    + text.substring(pos2 + 1);
            pos1 = text.indexOf("<img", pos2);
        }

        m = p_img.matcher(text);
        text = m.replaceAll("[img_begin]");

        m = p12.matcher(text);
        text = m.replaceAll(substitution);

        m = p13.matcher(text);
        text = m.replaceAll(substitution);


        text = p_img_1.matcher(text).replaceAll("<img");
        text = p_img_2.matcher(text).replaceAll(">");


        text = p_empty_newline.matcher(text).replaceAll("\r\n");

        text = p_space.matcher(text).replaceAll(" ");

        text = p_newline_space.matcher(text).replaceAll("\r\n");

        return text.trim();
    }
}
