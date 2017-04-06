package guthboss.com.finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 4/6/2017.
 */

public class LivingRoomFragment extends Fragment {
    int position;
    View rootView;

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        Bundle bun = getArguments();
        position = bun.getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        //Just testing to see if this works.
        /*
        TODO
        implement different activities based on which ID comes in with the bundle.
        Just use a basic conditional flow for this.
        You can move the switch from the main to here instead
         */
        switch(position){
            case 1: //Lamp 1
                rootView = inflater.inflate(R.layout.activity_lamp1, container, false);
                break;
            case 2: //Lamp 2
                rootView = inflater.inflate(R.layout.activity_lamp2, container, false);
                break;
            case 3: //Lamp 3
                rootView = inflater.inflate(R.layout.activity_lamp3, container, false);
                break;
            case 4: // Television
                rootView = inflater.inflate(R.layout.activity_television, container, false);
                break;
            case 5: //Blinds
                    rootView = inflater.inflate(R.layout.activity_blinds, container, false);
                break;
            default: //For added items
                rootView = inflater.inflate(R.layout.activity_not_implemented, container, false);
        }
        return rootView;
    }
}
