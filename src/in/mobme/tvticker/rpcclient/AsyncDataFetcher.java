package in.mobme.tvticker.rpcclient;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncDataFetcher extends AsyncTask<String, Integer, Long>{

	@Override
	protected Long doInBackground(String... params) {
		
		return null;
	}

	protected void onProgressUpdate(Integer... progress) {
        Log.i("AsyncDataFetcher", ""+progress[0]);
    }

    protected void onPostExecute(Long result) {
       //completed
    }

}
