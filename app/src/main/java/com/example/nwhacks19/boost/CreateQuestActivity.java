package com.example.nwhacks19.boost;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.snapchat.kit.sdk.SnapCreative;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi;
import com.snapchat.kit.sdk.creative.exceptions.SnapStickerSizeException;
import com.snapchat.kit.sdk.creative.media.SnapMediaFactory;
import com.snapchat.kit.sdk.creative.media.SnapSticker;
import com.snapchat.kit.sdk.creative.models.SnapContent;
import com.snapchat.kit.sdk.creative.models.SnapLiveCameraContent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CreateQuestActivity extends AppCompatActivity {

    private SnapCreativeKitApi snapCreativeKitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quest);
        snapCreativeKitApi = SnapCreative.getApi(this);
        sendStickerSnap(createSticker());
    }

    private void sendStickerSnap(SnapSticker snapSticker){
        SnapLiveCameraContent snapLiveCameraContent = new SnapLiveCameraContent();

        snapSticker.setWidth(300);
        snapSticker.setHeight(300);

        snapSticker.setPosX(0.5f);
        snapSticker.setPosY(0.5f);
        snapLiveCameraContent.setSnapSticker(snapSticker);
        snapCreativeKitApi.send(snapLiveCameraContent);
    }

    private SnapSticker createSticker(){
        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(this);
        SnapSticker sticker = null;
        try{
            sticker = snapMediaFactory.getSnapStickerFromFile(getStickerFromDrawable());
        } catch (SnapStickerSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sticker;
    }

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
