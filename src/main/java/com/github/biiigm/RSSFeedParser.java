package com.github.biiigm;

import com.github.biiigm.annotations.XMLClass;
import com.github.biiigm.annotations.XMLNode;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RSSFeedParser {

    public List<ArticleItem> readFeeds(String body) {
        List<ArticleItem> articleItemList = new ArrayList<>();
        try {
            ArticleItem articleItem = new ArticleItem();
            String baseItem = articleItem.getClass()
                    .getAnnotation(XMLClass.class).name();

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in =
                    new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName()
                            .getLocalPart();
                    List<Field> fields =
                            Arrays.stream(ArticleItem.class.getDeclaredFields())
                            .filter(field -> localPart.equals(field.getAnnotation(XMLNode.class)
                                    .name())).toList();
                    if (!fields.isEmpty()) {
                        setValueForFields(fields, articleItem,
                                getCharacterData(eventReader));
                    }
                } else if (event.isEndElement()) {
                    if (baseItem.equals(event.asEndElement().getName()
                            .getLocalPart())) {
                        articleItemList.add(articleItem);
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return articleItemList;
    }

    private String getCharacterData(XMLEventReader eventReader) throws XMLStreamException {
        String result = "";
        XMLEvent event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private void setValueForFields(List<Field> fields, Object target,
                                   Object value) {
        try {
            for (Field field : fields) {
                String fieldName = "set" + field.getName().substring(0, 1)
                        .toUpperCase() + field.getName().substring(1);
                Method method = target.getClass()
                        .getMethod(fieldName, field.getType());
                method.invoke(target, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
