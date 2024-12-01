package br.ufpr.tads.receiptscan.utils;

import br.ufpr.tads.receiptscan.mapper.ElementNotFoundException;
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

    public static Elements getElements(Document document, String select) {
        return Optional.ofNullable(document.select(select).first())
                .map(Element::children)
                .orElseThrow(() -> new ElementNotFoundException("Element not found or has no children: " + select));
    }

}

