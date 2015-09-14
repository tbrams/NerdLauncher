package dk.incipio.nerdlauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NerdLauncherFragment extends ListFragment {
    private static final String TAG = "NerdLauncherFragment";

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ResolveInfo resolveInfo=(ResolveInfo)l.getAdapter().getItem(position);
        ActivityInfo activityInfo = resolveInfo.activityInfo;

        if (activityInfo == null) {
            return;
        }

        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);

        Log.i(TAG, "Found a total of " + activities.size() + " activities with ACTION_MAIN & CATEGORY_LAUNCHER");

        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(lhs.loadLabel(pm).toString(), rhs.loadLabel(pm).toString());
            }
        });

        ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(getActivity(), R.layout.rowlayout, activities) {

            public View getView(int pos, View convertview, ViewGroup parent) {

                View view = convertview;  // check this first
                if (view == null) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.rowlayout, parent, false);
                }
                TextView textView = (TextView)view.findViewById(android.R.id.text1);

                // Get text and image from ResolveInfo Object - need packetmgr to do that...
                PackageManager pm = getActivity().getPackageManager();
                ResolveInfo resolveInfo = getItem(pos);
                textView.setText(resolveInfo.loadLabel(pm));

                ImageView imageView = (ImageView) view.findViewById(R.id.icon);
                imageView.setImageDrawable(resolveInfo.loadIcon(pm));

                return view;
            }

        };

        setListAdapter(adapter);

    }
}
