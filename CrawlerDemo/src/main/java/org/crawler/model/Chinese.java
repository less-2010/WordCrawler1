package org.crawler.model;

/**
 * @author BING
 * @date 2020/10/9 16:14 星期五
 */
public class Chinese {
    private String value;

    public Chinese(String value) {
        this.value = value;
    }

    public Chinese() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Chinese{" +
                "value='" + value + '\'' +
                '}';
    }
}
