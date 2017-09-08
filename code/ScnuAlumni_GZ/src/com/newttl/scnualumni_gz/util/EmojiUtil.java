package com.newttl.scnualumni_gz.util;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 微信emoji表情工具类
 * @author lgc
 *
 * @date 2017年7月8日 下午4:59:39
 */
public class EmojiUtil {

	private static String qqFaceRegex="/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:p-\\(|/::'\\||/:x-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:∞|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-O|/:hiphot|/:kiss|/:<&|/:&>";
	private static String anQqFace="/::)/::~/::B/::|/:8-)/::</::$/::X/::Z/::'(/::-|/::@/::P/::D/::O/::([囧]/::Q/::T/:,@P/:,@-D/::d/:,@o/:|-)/::!/::L/::>/::,@/:,@f/::-S/:?/:,@x/:,@@/:,@!/:!!!/:xx/:bye/:wipe/:dig/:handclap/:B-)/:<@/:@>/::-O/:>-|/:P-(/::'|/:X-)/::*/:8*/:pd/:<W>/:beer/:coffee/:pig/:rose/:fade/:showlove/:heart/:break/:cake/:bome/:shit/:moon/:sun/:hug/:strong/:weak/:share/:v/:@)/:jj/:@@/:ok/:jump/:shake/:<O>/:circle";
	
	/**
     * 显示不可见字符的Unicode
     * 
     * @param input
     * @return
     */
    public static String escapeUnicode(String input) {
        StringBuilder sb = new StringBuilder(input.length());
        @SuppressWarnings("resource")
        Formatter format = new Formatter(sb);
        for (char c : input.toCharArray()) {
            if (c < 128) {
                sb.append(c);
            } else {
                format.format("\\u%04x", (int) c);
            }
        }
        return sb.toString();
    }

    /**
     * 将emoji替换为unicode
     * 
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ue000-\uefff]", Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            Map<String, String> tmpMap = new HashMap<>();
            while (emojiMatcher.find()) {
                // System.out.println(escapeUnicode(emojiMatcher.group()));
                // System.out.println(emojiMatcher.start());
                String key = emojiMatcher.group();
                String value = escapeUnicode(emojiMatcher.group());
                //System.out.println("key：" + key);
                //System.out.println("value：" + value);
                tmpMap.put(key, value);
                // source =
                // emojiMatcher.replaceAll(escapeUnicode(emojiMatcher.group()));
            }
            if (!tmpMap.isEmpty()) {
                for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    source = source.replace(key, value);
                }
            }
        }
        return source;
    }
    
    /**
     * 判断用户发送过来的是不是QQ表情
     * @param content 文本消息内容
     * @return true|false
     */
    public static boolean isQqFace(String content){
    	boolean result=false;
    	//判断QQ表情的正则表达式
    	Pattern pattern=Pattern.compile(qqFaceRegex);
    	Matcher matcher=pattern.matcher(content);
    	if (matcher.matches()) {
			result=true;
		}
    	/*if (anQqFace.contains(content)) {
    		result=true;
		}*/
    	return result;
    }
    
    
}
