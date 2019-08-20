package com.example.lenovo.pfa_project.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.example.lenovo.pfa_project.Activities.LoginActivity;
import com.example.lenovo.pfa_project.Activities.ReserverActivity;
import com.example.lenovo.pfa_project.Activities.RestaurantActivity;
import com.example.lenovo.pfa_project.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReserverInfo extends BottomSheetDialogFragment implements View.OnClickListener {
    Context context;
    NumberPicker npicker;
    Button Terminer;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private String nb,date,formatedDate,idRestaurant;

    public ReserverInfo newInstance(Context context) {

        this.context=context;
        ReserverInfo f = new ReserverInfo();
        return f;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_reserver, container, false);

        npicker=(NumberPicker)v.findViewById(R.id.quantite);
        npicker.setMaxValue(20);
        npicker.setMinValue(1);
        datePicker=v.findViewById(R.id.date);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        timePicker= v.findViewById(R.id.time);
        timePicker.setIs24HourView(true);

        Terminer=(Button) v.findViewById(R.id.terminer);
        Terminer.setOnClickListener(this);
        return v;
    }
    public void setContext(Context context){
        this.context=context;

    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    @Override
    public void onClick(View v) {

        //******  get value date ************

        int   day  = datePicker.getDayOfMonth();
        int   month= datePicker.getMonth();
        int   year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        formatedDate = sdf.format(calendar.getTime());
        System.out.println("++++++++++++++date :"+formatedDate);

        //**********************************

        int pickedValue = npicker.getValue();

        nb=(Integer.toString(pickedValue));
        System.out.println("++++++++++++++nombre :"+nb);

        //************************************

        int heure=timePicker.getCurrentHour();
        int min=timePicker.getCurrentMinute();
        String time=Integer.toString(heure)+":"+Integer.toString(min);
        System.out.println("++++++++++++++heure :"+time);

        //*********************
        Intent i = new Intent(getActivity(), ReserverActivity.class);
        i.putExtra("date",formatedDate );
        i.putExtra("nombre",nb );
        i.putExtra("heure",time );
        i.putExtra("idRest",idRestaurant );


        startActivity(i);
    }
}
