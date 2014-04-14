package codio.lee.rssreader;

/**
 * Created by Veronika Lee on 14.04.2014.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import codio.lee.rssreader.Constants.DlgType;
import android.content.DialogInterface.OnCancelListener;


public class AsyncWorker extends AsyncTask<String, Integer, Integer> implements
        DialogInterface.OnCancelListener {
    private WeakReference<Context> parentContext;
    private ProgressDialog pd;
    private DlgType dlgType;
    private final Integer numberOfSteps = 10;
    private final Integer delay = 5;

    AsyncWorker(final DlgType type, final Context context) {
        dlgType = type;
        parentContext = new WeakReference<Context>(context);
    }

    @Override
    protected final void onPreExecute() {
        switch (dlgType) {

            case progressIndicator:
                pd = new ProgressDialog(parentContext.get());
                pd.setTitle("Go...");
                pd.setCancelable(true);
                pd.setOnCancelListener(this);
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setMax(numberOfSteps);
                pd.show();
                break;
            default:
                Log.d("AsyncWorker", "it is an error bud");
                break;
        }

    }

    @Override
    protected final void onProgressUpdate(final Integer... progress) {
        pd.setProgress(progress[0]);
    }

    @Override
    protected final void onPostExecute(final Integer result) {
        pd.dismiss();

    }

    protected final Integer doInBackground(final String... strings) {

        for (int i = 0; i < numberOfSteps; i++) {
            try {
                Thread.sleep(delay);
                publishProgress(i + 1);

            } catch (InterruptedException e) {
                Log.d("AsyncWorker", String.format(
                        "Crazy world, I just done %1 steps!",
                        Integer.valueOf(i + 1)));
            }

        }

        return 1;
    }

    @Override
    public final void onCancel(final DialogInterface dialog) {
        pd.dismiss();
        this.cancel(true);

    }
}
