package guthboss.com.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

/**
 * Created by user on 4/6/2017.
 */

public class LivingRoomFragment extends Fragment {
    int position;
    View rootView;
    Snackbar snack;
    Context ctx;

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        Bundle bun = getArguments();
        position = bun.getInt("position");
        ctx = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        switch(position){
            case 1: //Lamp1
                rootView = inflater.inflate(R.layout.activity_lamp1, container, false);
                ToggleButton tg = (ToggleButton)rootView.findViewById(R.id.lamp1Toggle);
                tg.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        //Create snackbar here
                        snack.make(view, "Turned Lamp On",Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    }
                });
                break;
            case 2: //Lamp 2
                rootView = inflater.inflate(R.layout.activity_lamp2, container, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Database Loaded");
                builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
// Create the AlertDialog
                FragmentTransaction ft = getFragmentManager().beginTransaction();
               new CustomDialog().show(ft, "A tag");

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

public  class CustomDialog extends DialogFragment{
    @Override
        public Dialog onCreateDialog(Bundle saved)
        {
            return new AlertDialog.Builder(getActivity()).setTitle("Hi").create();

        }

    }
}
