package com.gymapp.helper;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.gymapp.R;
import com.gymapp.main.GymApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ImageHelper {
    public static final String TAG = ImageHelper.class.getSimpleName();
    private static final int DEFAULT_MIN_WIDTH_QUALITY = 400;
    private static final String TEMP_IMAGE_NAME = "tempImage";
    private static final String PROFILE_AVATAR_FILE_EXTENSION = ".jpg";

    public ImageHelper() {
    }


    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() > 0 && drawable.getIntrinsicHeight() > 0) {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", (String) null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Uri uri, Context ctx) {
        Cursor cursor = ctx.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
        if (cursor != null && cursor.moveToFirst()) {
            int idx = cursor.getColumnIndex("_data");
            String path = cursor.getString(idx);
            cursor.close();
            return path;
        } else {
            return "";
        }
    }

    public static Intent getPickImageIntent(Context context) {
        Intent chooserIntent = null;
        Intent pickIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        takePhotoIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        takePhotoIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
        takePhotoIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
        takePhotoIntent.putExtra("return-data", true);
        Uri fileUri = FileProvider.getUriForFile(context, context.getString(R.string.file_provider_authority), getTempFile(context));
        takePhotoIntent.putExtra("output", fileUri);
        List intentList = addIntentsToList(context, new ArrayList(), pickIntent);
        intentList = addIntentsToList(context, intentList, takePhotoIntent);
        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser((Intent) intentList.remove(intentList.size() - 1), context.getString(R.string.update_photo));
            chooserIntent.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) intentList.toArray(new Parcelable[0]));
        }

        return chooserIntent;
    }

    public static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), "tempImage");
        imageFile.getParentFile().mkdirs();
        return imageFile;
    }

    public static Uri saveProfilePic(Context context, String cif, Bitmap bitmap) throws IOException {
        File imageFile = new File(context.getExternalCacheDir(), cif + ".jpg");
        imageFile.getParentFile().mkdirs();
        FileOutputStream fOut = new FileOutputStream(imageFile);
        bitmap.compress(CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return Uri.fromFile(imageFile);
    }

    public static Bitmap getProfilePic(Context context, String cif) {
        File imageFile = new File(context.getExternalCacheDir(), cif + ".jpg");
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
    }

    public static Uri getProfilePicUri(Context context, String cif) {
        File imageFile = new File(context.getExternalCacheDir(), cif + ".jpg");
        return Uri.fromFile(imageFile);
    }

    public static boolean deleteRegisteredUSerProfilePic(Context context, String photoName) {
        File imageFile = new File(context.getExternalCacheDir(), photoName + ".jpg");
        return imageFile.delete();
    }

    @Nullable
    public static Bitmap getImageFromResult(Context context, int resultCode, Intent imageReturnedIntent) {
        if (resultCode != -1) {
            return null;
        } else {
            File imageFile = getTempFile(context);
            boolean isCamera = imageReturnedIntent == null || imageReturnedIntent.getData() == null || imageReturnedIntent.getData().toString().contains(imageFile.toString());
            Uri selectedImage = isCamera ? Uri.fromFile(imageFile) : imageReturnedIntent.getData();
            Bitmap bm = decodeBitmap(selectedImage, ScreenDimenHelper.getScreenWidth(), ScreenDimenHelper.getScreenHeight());
            return rotate(bm, getRotation(context, selectedImage, isCamera));
        }
    }

    @Nullable
    public static Uri getImageUriFromResult(Context context, int resultCode, Intent imageReturnedIntent) {
        if (resultCode != -1) {
            return null;
        } else {
            File imageFile = getTempFile(context);
            boolean isCamera = imageReturnedIntent == null || imageReturnedIntent.getData() == null || imageReturnedIntent.getData().toString().contains(imageFile.toString());
            return isCamera ? Uri.fromFile(imageFile) : imageReturnedIntent.getData();
        }
    }

    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = new String[]{"_data"};
        Cursor cursor = context.getContentResolver().query(uri, proj, (String) null, (String[]) null, (String) null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }

            cursor.close();
        }

        if (result == null) {
            result = "Not found";
        }

        return result;
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;

            for (int halfWidth = width / 2; halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth; inSampleSize *= 2) {
            }
        }

        return inSampleSize;
    }

    public static int getRotation(Context context, Uri imageUri, boolean isCamera) {
        int rotation = isCamera ? getRotationFromCamera(context, imageUri) : getRotationFromGallery(context, imageUri);
        return rotation;
    }

    public static int getRotationFromGallery(Context context, Uri imageUri) {
        int result = 0;
        String[] columns = new String[]{"orientation"};
        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(imageUri, columns, (String) null, (String[]) null, (String) null);
            if (cursor != null && cursor.moveToFirst()) {
                int orientationColumnIndex = cursor.getColumnIndex(columns[0]);
                result = cursor.getInt(orientationColumnIndex);
            }
        } catch (Exception var9) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }

        return result;
    }

    public static File saveToInternalStorage(Bitmap bitmapImage, Context ctx) {
        ContextWrapper cw = new ContextWrapper(ctx);
        File directory = cw.getDir("imageDir", 0);
        File file = new File(directory, "profile.png");
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            bitmapImage.compress(CompressFormat.PNG, 30, fos);
        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        }

        return file;
    }

    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        Iterator var4 = resInfo.iterator();

        while (var4.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo) var4.next();
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }

        return list;
    }

    static Bitmap decodeBitmap(Uri fileUri, int reqWidth, int reqHeight) {
        Bitmap bitmap = null;


        try {
            ParcelFileDescriptor parcelFileDescriptor = GymApplication.instance.getContentResolver().openFileDescriptor(fileUri, "r");
            Throwable var5 = null;

            try {
                FileDescriptor fileDescriptor = null;
                if (parcelFileDescriptor != null) {
                    fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                }

                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(fileDescriptor, (Rect) null, options);
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, (Rect) null, options);
            } catch (Throwable var16) {
                var5 = var16;
                throw var16;
            } finally {
                if (parcelFileDescriptor != null) {
                    if (var5 != null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (Throwable var15) {
                            var5.addSuppressed(var15);
                        }
                    } else {
                        parcelFileDescriptor.close();
                    }
                }

            }
        } catch (IOException var18) {
            var18.printStackTrace();
        }

        return bitmap;
    }

    private static int getRotationFromCamera(Context context, Uri imageFile) {
        short rotate = 0;

        try {
            context.getContentResolver().notifyChange(imageFile, (ContentObserver) null);
            ExifInterface exif = new ExifInterface(imageFile.getPath());
            int orientation = exif.getAttributeInt("Orientation", 1);
            switch (orientation) {
                case 3:
                    rotate = 180;
                    break;
                case 6:
                    rotate = 90;
                    break;
                case 8:
                    rotate = 270;
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return rotate;
    }

    private static Bitmap rotate(Bitmap bm, int rotation) {
        if (rotation == 0) {
            return bm;
        } else {
            Matrix matrix = new Matrix();
            matrix.postRotate((float) rotation);
            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        }
    }

    private static Bitmap decodeBitmap(Context context, Uri theUri, int sampleSize) {
        Options options = new Options();
        options.inSampleSize = sampleSize;
        AssetFileDescriptor fileDescriptor = null;

        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(theUri, "r");
        } catch (FileNotFoundException var6) {
            var6.printStackTrace();
        }

        return BitmapFactory.decodeFileDescriptor(Objects.requireNonNull(fileDescriptor).getFileDescriptor(), (Rect) null, options);
    }
}

