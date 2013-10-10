package com.alorma.megsjc.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.alorma.megsjc.data.contract.AgrupamentContract;
import com.alorma.megsjc.data.cursor.AgrupamentsCursor;
import com.alorma.megsjc.data.model.Agrupament;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Bernat on 7/10/13.
 */
public class ParseKmlService extends IntentService {

    public static final String EXTRA_FILENAME = "EXTRA_FILENAME";

    public ParseKmlService() {
        super("ParseKmlService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InputStream is = null;
        if (intent.getExtras() != null && intent.getExtras().containsKey(EXTRA_FILENAME)) {
                try {
                    is = readKml(intent.getExtras().getString(EXTRA_FILENAME));
                    if (is != null) {
                        try {
                            List<Agrupament> agrupaments = parseKml(is);
                            saveToDb(agrupaments);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


    }

    private void saveToDb(List<Agrupament> agrupaments) {
        ContentResolver cr = getContentResolver();
        ContentValues[] values = new AgrupamentsCursor().setValuesArray(agrupaments);

        cr.delete(AgrupamentContract.CONTENT_URI, null, null);
        int inserted = cr.bulkInsert(AgrupamentContract.CONTENT_URI, values);
        Log.i("TAG", "Inserted: " + inserted);
    }

    private List<Agrupament> parseKml(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        KmlParser kmlParser = new KmlParser();

        SAXParserFactory saxPF = SAXParserFactory.newInstance();

        SAXParser saxP = saxPF.newSAXParser();

        XMLReader xmlR = saxP.getXMLReader();

        xmlR.setContentHandler(kmlParser);
        xmlR.parse(new InputSource(is));

        return kmlParser.getData();
    }

    private InputStream readKml(String fileName) throws IOException {
        return getAssets().open(fileName);
    }
}
