package codio.lee.rssreader;

/**
 * Created by Veronika Lee on 28.03.14.
 */
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class HandleXML {

    private String title = "title";
    private String quantity = "quantity";
    private String link = "link";
    private String description = "description";

    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    public HandleXML(String url){
        this.urlString = url;
    }
    public String getTitle(){
        return title;
    }
    public String getQuantity(){
        return quantity;
    }
    public String getLink(){
        return link;
    }
    public String getDescription(){
        return description;
    }
    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equals("title")){
                            title = text;
                        }
                        else if(name.equals("quantity")){
                            quantity = text;
                        }
                        else if(name.equals("link")){
                            link = text;
                        }
                        else if(name.equals("description")){
                            description = text;
                        }
                        else{
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Main activity", "Data is wrong, sorry, bud");
        }
    }
    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setConnection(conn);
                    InputStream stream = conn.getInputStream();
                    parseNow(stream);
                    stream.close();
                } catch (Exception e) {
                    Log.d("Main activity", "Having problems");
                }
            }
        });
        thread.start();
    }

    private void setConnection(HttpURLConnection conn) {
        try{
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
        }
        catch (Exception e) {
            Log.d("Main activity", "Connection problem, bud");
        }
    }

    private void parseNow(InputStream stream) {
        try{
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(stream, null);
            parseXMLAndStoreIt(myParser);

        } catch (Exception e) {
            Log.d("Main activity", "Parsing problem, bud");
        }
    }
}