package com.rb.pubgassistant;

import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static android.os.Environment.DIRECTORY_PICTURES;

public class ImageDownloadCallable implements Callable<String> {

    public String fileLink;

    public ImageDownloadCallable(String fileLink) {
        this.fileLink = fileLink;
    }

    @Override
    public String call() throws IOException {
        // String fileLink = "https://edgenews.ru/android/apexlegends/wallpapers/4265064.jpg";

        URL url = new URL(fileLink);
        Path path = Paths.get(url.getPath());
        String fileName = path.getFileName().toString();

        String fileSavePath = MyApplicationContext.getAppContext().getExternalFilesDir(DIRECTORY_PICTURES).toString() + "/" + fileName;
        Log.d("MyTag", fileSavePath);

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(fileLink).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileSavePath)){
            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Log.d("MyTag", e.getMessage());
        };

        setWallpaper(fileSavePath);

        return fileSavePath;
    }

    public void setWallpaper(String fileSavePath) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(MyApplicationContext.getAppContext());
        Bitmap bitmap = BitmapFactory.decodeFile(fileSavePath);

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        wallpaperManager.suggestDesiredDimensions(screenWidth, screenHeight);

        // int wallpaperWidth = wallpaperManager.getDesiredMinimumWidth();
        // int wallpaperHeight = wallpaperManager.getDesiredMinimumHeight();

        Bitmap bitmapWallpaper = Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, true);

        try {
            wallpaperManager.setBitmap(bitmapWallpaper);
        } catch (IOException e) {
            Log.d("MyTag", "Error setWallpaper: " + e.getMessage());
        }
    }
}
