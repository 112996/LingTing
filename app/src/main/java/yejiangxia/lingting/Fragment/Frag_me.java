package yejiangxia.lingting.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yejiangxia.lingting.Mood_User.CircleImageView;
import yejiangxia.lingting.R;



public class Frag_me extends Fragment {
    private final long SPLASH_LENGTH = 3000;
    Handler handler=new Handler();

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_frag_me, container,false);

        return view;
    }

}
