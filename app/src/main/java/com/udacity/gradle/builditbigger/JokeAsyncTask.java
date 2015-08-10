package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.pedro.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by Pedro on 4/8/2015.
 */
public class JokeAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService = null;
    private IAsyncTaskListener iAsyncTaskListener;

    public JokeAsyncTask(IAsyncTaskListener iAsyncTaskListener) {
        this.iAsyncTaskListener = iAsyncTaskListener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://built-it-bigger.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        try {
            return myApiService.getJokes().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        iAsyncTaskListener.onResult(result);
    }

}
