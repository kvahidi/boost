package com.example.nwhacks19.boost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.snapchat.kit.sdk.SnapCreative;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi;
import com.snapchat.kit.sdk.creative.exceptions.SnapStickerSizeException;
import com.snapchat.kit.sdk.creative.media.SnapMediaFactory;
import com.snapchat.kit.sdk.creative.media.SnapSticker;
import com.snapchat.kit.sdk.creative.models.SnapContent;
import com.snapchat.kit.sdk.creative.models.SnapLiveCameraContent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;

import static java.security.AccessController.getContext;

public class CreateQuestActivity extends Activity {

    private SnapCreativeKitApi snapCreativeKitApi;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quest);
        // gets API to use
        snapCreativeKitApi = SnapCreative.getApi(this);

        Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStickerSnap(createSticker());

            }
        });
    }

    private void sendStickerSnap(SnapSticker snapSticker){
        // gets live camera feed ready
        SnapLiveCameraContent snapLiveCameraContent = new SnapLiveCameraContent();

        snapSticker.setWidth(200);
        snapSticker.setHeight(200);

        snapSticker.setPosX(0.5f);
        snapSticker.setPosY(0.5f);
        // sets the sticker on the live camera feed
        snapLiveCameraContent.setSnapSticker(snapSticker);
        snapLiveCameraContent.setCaptionText("testing!!!");
        // launches snapchat with the live feed + sticker
        snapCreativeKitApi.send(snapLiveCameraContent);
    }

    private SnapSticker createSticker(){
        // gets factory class to create SnapSticker
        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(this);
        SnapSticker sticker = null;
        try{
            // creates sticker from file ( i made a quick one and put it under the raw res folder)
//
            Log.d("AAAGH", this.getFilesDir().toString());
            File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File[] listOfFiles = downloadDir.listFiles();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "purple-square-9.png");



//            Uri path = Uri.parse("android.resource://" + this.getPackageName() + "/raw/stickerbg");
//            File stickerFile = new File(URI.create(path.getPath()));
//            File stickerFile = new File(Uri.parse("android.resource://" + this.getPackageName() + "/raw/stickerbg.png").getPath());
            sticker = snapMediaFactory.getSnapStickerFromFile(file);
        } catch (SnapStickerSizeException e) {
            e.printStackTrace();
        } /*catch (IOException e) {
            e.printStackTrace();*
        }*/
        return sticker;    }

    private File getStickerFromDrawable() throws IOException {
        InputStream ins = getResources().openRawResource(R.raw.stickerbg);
        BufferedReader br = new BufferedReader(new InputStreamReader(ins));
        StringBuffer sb = null;
        String line;
        while((line = br.readLine()) != null){
            sb.append(line);
        }

        File f = new File(sb.toString());
        return f;
    }
}
