package org.crawler.db;


import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author BING
 * @date 2020/10/9 20:45 星期五
 */
public class DBController {
        Map<String, String> allWord = getALLWord();
        private static String defaultTableName = "wordstartingwitha";

    /**
     * 增加单词到数据库
     * @param word
     * @param translation
     * @return
     */
    public synchronized boolean AddWord(String word,String translation){
        //获取链接
        Connection conn = DBUtil.getConn();
        String tableName =generateTableName(word);
        //SQL语句
        String sql = ""+
                "insert into "+tableName+
                "(word,translation)"+
                "values(?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,word);
            preparedStatement.setString(2,translation);
           boolean execute = preparedStatement.execute();
            if (!(execute)) {
                return true;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据单词开头生成表名
     * @param word
     * @return
     */
    private String generateTableName(String word){
       String tableName = "wordstartingwith"+word.substring(0,1).toLowerCase();
       return tableName;
   }

    /**
     * 删除单词
     * @param word
     * @return 删除与否
     */
    public boolean delWord(String word){
        Connection conn = DBUtil.getConn();

        String tableName = generateTableName(word);

        boolean exists = wordExists(word);
        if (exists) {
            String sql = "" +
                    "DELETE FROM " +tableName+
                    " WHERE word = ?";
            try {
                PreparedStatement psm = conn.prepareStatement(sql);
                psm.setString(1, word);
                boolean execute = psm.execute();
                return execute;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 获取单词集合
     * @return  所有表的单词的集合
     */
    public  Map<String,String> getALLWord(){
        Connection conn = DBUtil.getConn();

        Map<String,String>words = new HashMap<>();

        String tableName = "wordstartingwith" ;

        //遍历数据表添加到集合
        for (int i = 97; i < 123 ; i++) {

            StringBuffer sb = new StringBuffer(tableName);
            sb.insert(tableName.length(), (char) i);
            String sql = "" +
                    "SELECT * FROM " + sb.toString();
            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    String word = resultSet.getString("word");
                    String translation = resultSet.getString("translation");
                    words.put(word, translation);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            return words;
    }

    /**
     * 更改单词信息
     * @param oldWord
     * @param newWord
     * @param newTranslation
     * @return
     */
    public  boolean edit(String oldWord,String newWord,String newTranslation){
        Connection conn = DBUtil.getConn();
        //获取表名
        String tableName = generateTableName(oldWord);
        //如果存在该单词
        if (translationExists(oldWord)){
            String sql = ""+
                    "UPDATE "+tableName+" SET word=?,translation=? WHERE word =?";
            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1,newWord);
                pst.setString(2,newTranslation);
                pst.setString(3,oldWord);
                return pst.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 单词是否存在
     * @param word
     * @return
     */
    public  boolean  wordExists( String word){
        Set<String> words = allWord.keySet();
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()){
            if (iterator.next().equals(word)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断单词翻译是否存在
     * @param word
     * @return
     */
    public  boolean  translationExists(String word){
        Set<String> words = allWord.keySet();
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()){
            //如果单词存在，且翻译为空返回true
            if (iterator.next().equals(word)&&allWord.get(iterator.next()).trim()==null){
                return false;
            }
        }
        return true;
    }

}
