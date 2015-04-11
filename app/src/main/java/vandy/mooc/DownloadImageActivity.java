package vandy.mooc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        // @@ DoneTODO -- you fill in here.
        super.onCreate(savedInstanceState);

        // Get the URL associated with the Intent data.
        // @@ DoneTODO -- you fill in here.
        final String inputUrl = getIntent().getDataString();

        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.
        final Intent resultIntent = new Intent();

        // @@ DoneTODO -- you fill in here using the Android "HaMeR"
        // concurrency framework.  Note that the finish() method
        // should be called in the UI thread, whereas the other
        // methods should be called in the background thread.  See
        // http://stackoverflow.com/questions/20412871/is-it-safe-to-finish-an-android-activity-from-a-background-thread
        // for more discussion about this topic.

        // New Handler object and connect to the UI thread
        final Handler mHandler = new Handler(Looper.myLooper());

        new Thread(new Runnable() {
            @Override
            public void run() {

                // Use DownloadUtils method downloadImage to download image from web
                Uri downloadUrl = DownloadUtils.downloadImage(getApplicationContext(),
                        Uri.parse(inputUrl));

                // Add downloaded image path to the result intent
                resultIntent.setData(downloadUrl);
                setResult(RESULT_OK, resultIntent);

                // Post this runnable to UI thread
                // See alternate code below where I used runOnUiThread w/o Handler
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        DownloadImageActivity.this.finish();
                    }
                });
            }
        }).start();

        // Alternatively I can have following code. It partially uses HaMeR framework.
        // It doesn't use Handler and uses runOnUiThread.
        // Uncomment below code and comment above code that starts with Handler declaration
        // and compile & execute to see it running.

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                // Use DownloadUtis method downloadImage to download image from web
//                Uri downloadUrl = DownloadUtils.downloadImage(getApplicationContext(),
//                        Uri.parse(inputUrl));
//
//                // Add downloaded image path to the result intent
//                resultIntent.setData(downloadUrl);
//                setResult(RESULT_OK, resultIntent);
//
//                DownloadImageActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        finish();
//                    }
//                });
//            }
//        }).start();

    }
}
