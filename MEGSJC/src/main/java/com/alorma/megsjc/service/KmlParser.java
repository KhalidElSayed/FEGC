package com.alorma.megsjc.service;

import android.util.Log;

import com.alorma.megsjc.data.contract.AgrupamentContract;
import com.alorma.megsjc.data.model.Agrupament;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bernat on 7/10/13.
 */
public class KmlParser extends DefaultHandler {
    String elementValue = null;
    Boolean elementOn = false;
    public List<Agrupament> data = null;
    private Agrupament current;

    public List<Agrupament> getData() {
        return data;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

        data = new ArrayList<Agrupament>();
    }

    /**
     * This will be called when the tags of the XML starts.
     */
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        elementOn = true;
        if ("agrupament".equalsIgnoreCase(localName)) {
            current = new Agrupament();
        }
    }

    /**
     * This will be called when the tags of the XML end.
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        elementOn = false;
        /**
         * Sets the values after retrieving the values from the XML tags
         * */

        if (current != null) {
            if (AgrupamentContract.Fields.NAME.equalsIgnoreCase(localName)) {
                current.setName(elementValue);
            } else if (AgrupamentContract.Fields.ADDR.equalsIgnoreCase(localName)) {
                current.setAddr(elementValue);
            } else if (AgrupamentContract.Fields.CP.equalsIgnoreCase(localName)) {
                current.setCp(elementValue);
            } else if (AgrupamentContract.Fields.POBLACIO.equalsIgnoreCase(localName)) {
                current.setPoblacio(elementValue);
            } else if (AgrupamentContract.Fields.EMAIL.equalsIgnoreCase(localName)) {
                current.setEmail(elementValue);
            } else if (AgrupamentContract.Fields.WEB.equalsIgnoreCase(localName)) {
                current.setWeb(elementValue);
            } else if (AgrupamentContract.Fields.LOGO.equalsIgnoreCase(localName)) {
                current.setLogo(elementValue);
            } else if (AgrupamentContract.Fields.DEMARC.equalsIgnoreCase(localName)) {
                current.setDemarcacio(elementValue);
            } else if (AgrupamentContract.Fields.SOTS.equalsIgnoreCase(localName)) {
                current.setSots(elementValue);
            } else if (AgrupamentContract.Fields.LAT.equalsIgnoreCase(localName)) {
                Double lat = Double.parseDouble(elementValue);
                current.setLat(lat);
            } else if (AgrupamentContract.Fields.LON.equalsIgnoreCase(localName)) {
                Double lon = Double.parseDouble(elementValue);
                current.setLon(lon);
            } else if ("agrupament".equalsIgnoreCase(localName)) {
                data.add(current);
            }
        }
    }

    /**
     * This is called to get the tags value
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (elementOn) {
            elementValue = new String(ch, start, length);
            elementOn = false;
        }
    }
}
