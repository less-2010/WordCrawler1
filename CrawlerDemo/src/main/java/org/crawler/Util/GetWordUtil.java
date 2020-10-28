package org.crawler.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;


/**
 * @author BING
 * @date 2020/10/9 11:06 星期五
 */
public class GetWordUtil {
    /**
     * jsoup方式 获取网页列表信息
     *
     * @param url 要爬取信息的地址url
     * @return
     */
    public static List<String> jsoupList(String url) {

        List<String> stringList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();

            // 使用 css选择器 提取列表新闻 a 标签
            Elements elements = document.select("div [class=air_con f-f0] p");
            //添加到列表
            int i = 0;
            for (Element element : elements) {
                String worlds = element.ownText();
                System.out.println(i++ + ":" + worlds);

                if (i > 3 && i < elements.size() - 1) {
                    stringList.add(worlds);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }

    /**
     * 通过url获取单词集合
     *
     * @param url
     * @return 返回单词Map集合
     */
    public static Map<String, String> getWords(String url) {

        Map<String, String> stringMap = new HashMap<>();
        List<String> stringList = jsoupList(url);
        for (String word : stringList
        ) {
            //按空格切分
            String[] strArr = word.split(" ");
            //unicode空格用trim去不掉
            //用Ascll空格替换Unicode(12288)空格
            String keyName = strArr[0].replace((char) 12288, ' ').trim();
            //减去单词，获得翻译
            String subValue = word.substring(keyName.length() + 2);
            //添加到map集合
            stringMap.put(keyName, subValue);
        }
        return stringMap;
    }

    /**
     *  将单词txt文件写入到map集合
     * @param path
     * @return  返回单词Map集合
     */
    public static Map<String, String> getWordsByTxt(String path) {
        Map<String, String> wordMap = new HashMap<>();
        InputStreamReader isr = null;
        BufferedReader br = null;

        StringBuffer sb=null;
        try {
            //使用字符输入流读取
            isr = new InputStreamReader(new FileInputStream(path));
            br = new BufferedReader(isr);
            String read;
            //ascll
            //A-Z（65-90）
            //+32```
            //a-z(97-123)
            while ((read = (br.readLine())) != null) {
                //使用字符串缓冲器
                sb = new StringBuffer(read);
                for (int i = 65; i < 91; i++) {
                    //首字母（忽略大小写）
                    if (sb.charAt(0) == (char) i || sb.charAt(0) == (char) (i + 32)) {
                        //截取翻译字符串
                        String translation = sb.substring(sb.indexOf(" ")).trim();
                        //截取单词字符串
                        String word = sb.substring(0, sb.indexOf(" ")).trim();
                        //System.out.println(word + ":" + translation);
                        wordMap.put(word,translation);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wordMap;
    }

}
