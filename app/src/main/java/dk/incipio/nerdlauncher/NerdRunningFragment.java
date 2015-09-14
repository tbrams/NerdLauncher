package dk.incipio.nerdlauncher;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tbrams on 14/09/15.
 */

public class NerdRunningFragment extends ListFragment {

    private static final String TAG = "NerdRunningFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager am = (ActivityManager) getActivity().getSystemService(getActivity().ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(Integer.MAX_VALUE);

        Log.i(TAG, "Found a total of " + taskInfo.size() + " running activities with getActivity().ACTIVITY_SERVICE");

        ArrayAdapter<ActivityManager.RunningTaskInfo> adapter = new ArrayAdapter<ActivityManager.RunningTaskInfo>(
            getActivity(), R.layout.rowlayout, taskInfo) {

        public View getView(int pos, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }
            ActivityManager.RunningTaskInfo ri = getItem(pos);

            TextView applicationName = (TextView)convertView.findViewById(android.R.id.text1);
            applicationName.setText(ri.baseActivity.getPackageName().toString());


            final PackageManager pm = getActivity().getPackageManager();
            ApplicationInfo ai;
            try {
                ai = pm.getApplicationInfo( ri.baseActivity.getPackageName(), 0);
            } catch (final PackageManager.NameNotFoundException e) {
                ai = null;
            }

            Drawable iconImage = ai.loadIcon(getActivity().getPackageManager());
            ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
            if (imageView != null) {
                imageView.setImageDrawable(iconImage);
            }

            return convertView;
        }
    };
    setListAdapter(adapter);
}

    @TargetApi(Build.VERSION_CODES.HONEYCOMB) // because moveTaskToFront need api 11
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ActivityManager.RunningTaskInfo ri = (ActivityManager.RunningTaskInfo)l.getAdapter().getItem(position);
        ActivityManager am = (ActivityManager) getActivity().getSystemService(getActivity().ACTIVITY_SERVICE);
        am.moveTaskToFront(ri.id, 0);
    }

}
