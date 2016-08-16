package com.yue.core;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Scanner;

/**
 * Created by yue on 2016/6/29. 11
 *
 * @see java.util.concurrent.Callable
 * @see Runnable
 * @see java.util.concurrent.ExecutorService
 * @see java.util.concurrent.Future
 * @see java.util.concurrent.FutureTask
 */
public class Test {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int number = 61693;
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://www.kanunu8.com/book/4542/";
        // String url = "http://www.kanunu8.com/files/terrorist/200909/858/";
        while (true) {
            System.out.println(" enter y or n");
            String judge = scanner.next();
            String uri = url + number + ".html";
            if (judge.equalsIgnoreCase("y")) {
                try {
                    HttpGet httpget = new HttpGet(uri);
                    CloseableHttpResponse response = client.execute(httpget);
                    int stateCode = response.getStatusLine().getStatusCode();//返回码
                    if (stateCode == HttpStatus.SC_OK) {
                        HttpEntity entity = response.getEntity();
                        String html = EntityUtils.toString(entity);
                        html = new String(html.getBytes("ISO8859-1"), "GBK");
                        Document doc = Jsoup.parse(html);
                        Element body = doc.body();
                        Elements elements = body.select("td");
                        for (Element e : elements) {
                            read(e.ownText());
                            Elements elements1 = e.select("p");
                            for (Element e1 : elements1) {
                                read(e1.ownText());
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                number++;
                System.out.println(number);
            } else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private static void read(String str) {
        CharBuffer buffer = CharBuffer.allocate(24);
        for (char c : str.toCharArray()) {
            buffer.put(c);
            if (buffer.position() == 24) {
                read(buffer);
                buffer.clear();
            }
        }
    }

    private static void read(CharBuffer buffer) {
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println();
    }

}
