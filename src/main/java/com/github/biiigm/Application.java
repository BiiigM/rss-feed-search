package com.github.biiigm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter an URL: ");
        Document doc = Jsoup.connect(reader.readLine()).get();
        List<String> rssLinks = doc.select("link")
                .stream()
                .filter(node ->
                        "application/rss+xml".equals(node.attr("type")))
                .map(element -> element.attr("href"))
                .toList();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(rssLinks.get(0)))
                        .build();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request,
                    BodyHandlers.ofString());
            RSSFeedParser rssFeedParser = new RSSFeedParser();
            List<ArticleItem> articleItemList =
                    rssFeedParser.readFeeds(response.body());
            articleItemList.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
