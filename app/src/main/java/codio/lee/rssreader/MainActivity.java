package codio.lee.rssreader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import codio.lee.rssreader.Constants.DlgType;

public class MainActivity extends Activity {

    //private String finalUrl="http://kamala-maniva.box.preview.codiodev.com/first.xml";
    private String finalUrl="https://damask-pokunt.codio.io/new.xml";
    private HandleXML obj;
    private EditText title,link,description,quantity;
    private AsyncWorker bgTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    protected void initComponents(){
        title = (EditText)findViewById(R.id.editText1);
        quantity = (EditText)findViewById(R.id.editText0);
        link = (EditText)findViewById(R.id.editText2);
        description = (EditText)findViewById(R.id.editText3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public final void commandListener(final View target) {
            bgTask = new AsyncWorker(DlgType.progressIndicator, this);
            bgTask.execute("Good", "Bad", "Ugly");
            Log.i("Main activity", "ok, running");
            fetch(target);

    }

    public void fetch(View view){
        obj = new HandleXML(finalUrl);
        obj.fetchXML();
        while(obj.parsingComplete);
        title.setText(obj.getTitle());
        quantity.setText(obj.getQuantity());
        link.setText(obj.getLink());
        description.setText(obj.getDescription());
    }
    protected final void onPause() {

        if ((bgTask != null)
                && (bgTask.getStatus() == AsyncTask.Status.RUNNING)) {
            bgTask.cancel(false);
        }
        super.onPause();
    }
}

