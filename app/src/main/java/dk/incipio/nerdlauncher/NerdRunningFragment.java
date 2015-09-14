package dk.incipio.nerdlauncher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tbrams on 14/09/15.
 */
public class NerdRunningFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_nerd_launcher, container, false);

        TextView tv = (TextView) view.findViewById(R.id.text_field_1);
        //   tv.setText(getArguments().getString("msg"));
        return view;
    }

}
