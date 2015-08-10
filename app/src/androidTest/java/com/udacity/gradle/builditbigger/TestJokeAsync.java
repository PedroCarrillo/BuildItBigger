package com.udacity.gradle.builditbigger;
import android.test.AndroidTestCase;
import android.test.UiThreadTest;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Pedro on 7/8/2015.
 */
public class TestJokeAsync extends AndroidTestCase  implements  IAsyncTaskListener{

    CountDownLatch signal;

    @UiThreadTest
    public void testAsyncTask() {
        signal = new CountDownLatch(1);
        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask(this);
        assertTrue(1==1);
        jokeAsyncTask.execute();
        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResult(Object o) {
        String result = (String)o;
        assertTrue(result != null);
        signal.countDown();
    }
}
