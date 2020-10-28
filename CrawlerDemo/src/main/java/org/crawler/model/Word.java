package org.crawler.model;

import java.util.Objects;

/**
 * @author BING
 * @date 2020/10/9 14:13 星期五
 */
public class Word {
    //英文单词
    private String englishWord;
    //中文意思
    private String chineseWordMeaning;
    //词性
    private String partOfSpeech;
    //单词长度
    private int length;

    public Word(String englishWord, String chineseWordMeaning, String partOfSpeech, int length) {
        this.englishWord = englishWord;
        this.chineseWordMeaning = chineseWordMeaning;
        this.partOfSpeech = partOfSpeech;
        this.length = length;
    }

    public Word() {
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getChineseWordMeaning() {
        return chineseWordMeaning;
    }

    public void setChineseWordMeaning(String chineseWordMeaning) {
        this.chineseWordMeaning = chineseWordMeaning;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return length == word.length &&
                Objects.equals(englishWord, word.englishWord) &&
                Objects.equals(chineseWordMeaning, word.chineseWordMeaning) &&
                Objects.equals(partOfSpeech, word.partOfSpeech);
    }

    @Override
    public int hashCode() {
        return Objects.hash(englishWord, chineseWordMeaning, partOfSpeech, length);
    }

    @Override
    public String toString() {
        return "Word{" +
                "englishWord='" + englishWord + '\'' +
                ", chineseWordMeaning='" + chineseWordMeaning + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", length=" + length +
                '}';
    }
}
