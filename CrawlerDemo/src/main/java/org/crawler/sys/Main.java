package org.crawler.sys;


import org.crawler.Util.GetWordUtil;
import org.crawler.db.DBController;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;


/**
 * @author BING
 * @date 2020/10/9 11:11 星期五
 */
public class Main {
    static  DBController dbCon = new DBController();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        GetWordUtil.getWords("http://cet4-6.xdf.cn/202009/11107179.html");

//        putInDB("http://cet4-6.xdf.cn/202009/11107171.html");

//        putInWordDB();
        ShowAllWord();

    }

    private static void putInWordDB() {
        Map<String, String> words = GetWordUtil.getWordsByTxt("C:\\Users\\BING\\IdeaProjects\\WordCrawler\\CrawlerDemo\\src\\main\\resource\\words.txt");
        words.forEach((s1, s2) -> {
//            System.out.println(s1+"\t" + s2);
            if (!dbCon.wordExists(s1)) {
                boolean addWord = dbCon.AddWord(s1, s2);
                if (addWord == true) {
                    System.out.println("成功将：" + s1 + "添加到数据库！");
                }
            }else {
                System.out.println("单词:"+s1+"已经存在!");
            }
        });
    }

    private static void updateView() {
        System.out.println("请输入要更改的单词：");
        String word = scanner.nextLine();
        if (dbCon.wordExists(word)){
            System.out.println("将单词修改为：");
            String newWord = scanner.nextLine();

            System.out.println("将单词翻译修改为：");
            String translation = scanner.nextLine();

            boolean edit = dbCon.edit(word, newWord, translation);
            if (edit==true){
                System.out.println("修改成功！");
            }else {
                System.out.println("修改失败！");
            }
        }else {
            System.out.println("单词不存在！");
        }
    }

    private static void delView() {
        System.out.println("请输入要删除的单词：");
        String Word = scanner.next();
        boolean delWord = dbCon.delWord(Word);
        if (dbCon.wordExists(Word)){
            System.out.println("删除成功！");
        }else {
            System.out.println("单词不存在");
        }
    }

    /**
     *
     * @param url
     */
    private static void putWordInDBByURL(String url) {
        Map<String, String> stringMap = GetWordUtil.getWords(url);
        stringMap.forEach((s, s2) -> {
            boolean addWord = dbCon.AddWord(s, s2);
            if (addWord==true){
                System.out.println("成功将："+s+"添加到数据库！");
            }
        });
    }

    private static void ShowAllWord() {
        Map<String, String> allWord = dbCon.getALLWord();
        allWord.forEach((s1,s2)-> System.out.println(s1+":\t"+s2));
        System.out.println(allWord.size()+"一共个单词！");
    }
}
