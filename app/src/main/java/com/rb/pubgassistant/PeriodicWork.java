package com.rb.pubgassistant;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PeriodicWork extends Worker {

    private Context context;
    public static final String APP_PREFERENCES = "my_settings";
    public static final String APP_PREFERENCES_NAME = "name";
    public static final String APP_PREFERENCES_SERVER = "server";

    public PeriodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("MyTag", "periodic work class -->");

        TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();

        // Callable newsUpdateCallable = new NewsUpdateCallable(10);
        // taskRunner.executeAsync(newsUpdateCallable);
        // Callable newsClearCallable = new NewsClearCallable();
        // taskRunner.executeAsync(newsClearCallable);
        // Callable newsNotificationCallable = new NewsNotificationCallable();
        // taskRunner.executeAsync(newsNotificationCallable);

        Callable tweetsUpdateCallable = new TweetsUpdateCallable();
        taskRunner.executeAsync(tweetsUpdateCallable);

        Callable tweetsClearCallable = new TweetsClearCallable();
        taskRunner.executeAsync(tweetsClearCallable);

        Callable playerUpdateCallable = new PlayerUpdateCallable();
        taskRunner.executeAsync(playerUpdateCallable);

        return Result.success();
    }
}
