package codio.lee.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private String finalUrl="http://kamala-maniva.box.preview.codiodev.com/first.xml";
    //private String finalUrl="https://damask-pokunt.codio.io/new.xml";
    private HandleXML obj;
    private EditText title,link,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (EditText)findViewById(R.id.editText1);
        link = (EditText)findViewById(R.id.editText2);
        description = (EditText)findViewById(R.id.editText3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void fetch(View view){
        obj = new HandleXML(finalUrl);
        obj.fetchXML();
        while(obj.parsingComplete);
        title.setText(obj.getTitle());
        link.setText(obj.getLink());
        description.setText(obj.getDescription());
    }
}

