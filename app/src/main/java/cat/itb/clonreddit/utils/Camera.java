package cat.itb.clonreddit.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import id.zelory.compressor.Compressor;

public class Camera {
    private final Context context;

    public Camera(Context context) {
        this.context = context;
    }

    public byte[] comprimirImagen(File file) throws IOException {
        Bitmap thumbBitmap = new Compressor(context)
                .setMaxHeight(125)
                .setMaxWidth(125)
                .setQuality(90)
                .compressToBitmap(file);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
