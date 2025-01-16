package br.ufpr.tads.receiptscan.utils;

import br.ufpr.tads.receiptscan.exception.ElementNotFoundException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

public class HtmlUtils {

    public static Element getElement(Document document, String select) {
        return Optional.ofNullable(document.select(select).first())
                .orElseThrow(() -> new ElementNotFoundException("Element not found: " + select));
    }

    public static Element getElement(Element element, String select) {
        return Optional.ofNullable(element.select(select).first())
                .orElseThrow(() -> new ElementNotFoundException("Element not found: " + select));
    }

    public static Optional<Element> getNullableElement(Document document, String select) {
        return Optional.ofNullable(document.select(select).first());
    }

    public static Elements getElements(Document document, String select) {
        return Optional.ofNullable(document.select(select).first())
                .map(Element::children)
                .orElseThrow(() -> new ElementNotFoundException("Element not found or has no children: " + select));
    }

    public static String getNextTextNodeFrom(Document document, String select) {
        Element element = getElement(document, select);
        return Optional.ofNullable(element.nextSibling())
                .map(text -> text.toString().trim())
                .orElseThrow(() -> new ElementNotFoundException("Text node not found"));
    }

    public static String getNullableNextTextNodeFrom(Document document, String select) {
        return getNullableElement(document, select)
                .map(Element::nextSibling)
                .map(Object::toString)
                .orElse(null);
    }

}

