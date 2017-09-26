package com.example.who.githuborganizations.views;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.pojo.Organization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by who on 26.09.2017.
 */

public class OrganizationItemView extends RelativeLayout {

    public static final String TAG = OrganizationItemView.class.getSimpleName();

    @BindView(R.id.ivChannelsContactProfile)
    ImageView ivImageOfNew;
    @BindView(R.id.tvChannelsContactName)
    TextView tvOrgLogin;
    @BindView(R.id.tvChannelsTimeOfMessage)
    TextView tvOrgLocation;
    @BindView(R.id.tvChannelsContactLastMessage)
    TextView tvOrgUrl;
    @BindView(R.id.wrap)
    RelativeLayout wrap;

    private String newId;
    private Organization item;

    public OrganizationItemView(Context context) {
        super(context);
        init();
    }

    public OrganizationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OrganizationItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.organizations_item_view, this);
        ButterKnife.bind(this);
    }

    void setImageOfOrg(String src) {
        List<ImageView> list = new ArrayList<>();
        list.add(0, ivImageOfNew);
        if (isNetworkConnected()) {
            new DownloadImageTask(list).execute(src);
        } else {
            ivImageOfNew.setImageBitmap(getImageFromSD());
        }
    }

    void setTitleOfOrg(String login) {
        tvOrgLogin.setText(login);
    }

    void setAuthorOfOrg(String location) {
        tvOrgLocation.setText(location);
    }

    void setUrlOfOrg(String url) {
        tvOrgUrl.setText(url);
    }

    public static OrganizationItemView inflate(ViewGroup parent) {
        OrganizationItemView itemView = (OrganizationItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.org_item, parent, false);
        return itemView;
    }

    public void setItem(Organization item) {
        if (item != null) {
            OrganizationItemView.this.item = item;
            newId = String.valueOf(item.getId());
            final String imageUri = item.getAvatarUrl();
            final String login = item.getLogin();
            final String location = item.getOrganizationsUrl();
            final String url = item.getHtmlUrl();
            setImageOfOrg(imageUri);
            setTitleOfOrg(login);
            setAuthorOfOrg(location);
            setUrlOfOrg(url);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        List<ImageView> bmImageList = new ArrayList<>();

        public DownloadImageTask(List<ImageView> bmImageList) {
            this.bmImageList = bmImageList;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            saveImage(result);
            bmImageList.get(0).setImageBitmap(result);
        }
    }

    private void saveImage(Bitmap finalBitmap) {

        File file = new File(getPrivateDirectory(getContext()), newId);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImageFromSD() {
        File file = new File(getPrivateDirectory(getContext()), newId);
        String photoPath = file.getAbsolutePath();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        return bitmap;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static File getPrivateDirectory(Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("story", Context.MODE_PRIVATE);
        //File directory = context.getExternalFilesDir(DIRECTORY_PICTURES);
        if (!directory.exists()) directory.mkdir();
        return directory;
    }
}