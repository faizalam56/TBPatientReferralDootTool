package zmq.com.tbpatientreferraldoottool.utility;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by zmq162 on 3/11/16.
 */
public class MySound {

    public static MediaPlayer mediaPlayer= new MediaPlayer();

    public static void playSound(Context context,int uri){
        stopSound();
        mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.start();

    }

    public static void stopSound(){
        if(mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
}
