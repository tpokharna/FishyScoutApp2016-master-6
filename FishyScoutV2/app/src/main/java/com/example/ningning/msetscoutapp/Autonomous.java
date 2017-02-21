package com.example.ningning.msetscoutapp;

/**
 * textView for high/low goals
 * balls possessed --> not in confirmation page
 */

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

//part 1 of input activity
public class Autonomous extends Fragment  {

    // 2/17 revisions
    RoboInfo myRobo = RoboInfo.getInstance();
    String matchT;
    String teamT;

    ToggleButton zero;
    ToggleButton one;
    ToggleButton two;
    ToggleButton three;

    Button highHit;
    Button highMiss;
    Button highDelete;

    Button lowHit;
    Button lowMiss;
    Button lowDelete;

    TextView highView;
    TextView lowView;

    Button submit;

    static EditText matchText;
    static EditText teamText;
    static EditText scouterText;
    static ToggleButton spyButton;
    static ToggleButton reachButton;
    static Spinner crossSpinner;
    static TextView highGoal;
    static TextView lowGoal;

    static Spinner a;
    static Spinner b;
    static Spinner c;
    static Spinner d;

    private RoboInfo autoInfo = new RoboInfo();

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View in = inflater.inflate(R.layout.activity_autonomous, container, false); // adds Autonomous tab to input activity

        // 2/17 Heher revisions
        matchText = (EditText)in.findViewById(R.id.matchNumberEdit);
        teamText = (EditText)in.findViewById(R.id.teamNumberEdit);
        scouterText = (EditText)in.findViewById(R.id.scouterNameEdit);
        spyButton = (ToggleButton) in.findViewById(R.id.spyToggleButton);
        reachButton = (ToggleButton) in.findViewById(R.id.reachDefenseToggleButton);
        crossSpinner = (Spinner) in.findViewById(R.id.crossDefenseSpinner);
        highGoal = (TextView) in.findViewById(R.id.highGoalView1);
        lowGoal = (TextView) in.findViewById(R.id.lowGoalView1);

        //set up radiogroup-like behaviors for toggle buttons
        zero = (ToggleButton) in.findViewById(R.id.zeroBallsToggle);
        one = (ToggleButton) in.findViewById(R.id.oneBallsToggle);
        two = (ToggleButton) in.findViewById(R.id.twoBallsToggle);
        three = (ToggleButton) in.findViewById(R.id.threeBallsToggle);

        zero.setOnCheckedChangeListener(changeChecker);
        one.setOnCheckedChangeListener(changeChecker);
        two.setOnCheckedChangeListener(changeChecker);
        three.setOnCheckedChangeListener(changeChecker);

        highHit = (Button) in.findViewById(R.id.highGoalHitButton1);
        highMiss = (Button) in.findViewById(R.id.highGoalMissButton1);
        highDelete = (Button) in.findViewById(R.id.highGoalDeleteButton1);
        lowHit = (Button) in.findViewById(R.id.lowGoalHitButton1);
        lowMiss = (Button) in.findViewById(R.id.lowGoalMissButton1);
        lowDelete = (Button) in.findViewById(R.id.lowGoalDeleteButton1);

        a = (Spinner) in.findViewById(R.id.spinnerA);
        b = (Spinner) in.findViewById(R.id.spinnerB);
        c = (Spinner) in.findViewById(R.id.spinnerC);
        d = (Spinner) in.findViewById(R.id.spinnerD);

        a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Teleop.spinner2.setText(Autonomous.a.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Teleop.spinner2.setText(Autonomous.a.getSelectedItem().toString());
            }

        });
        b.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Teleop.spinner3.setText(Autonomous.b.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Teleop.spinner3.setText(Autonomous.b.getSelectedItem().toString());
            }

        });
        c.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Teleop.spinner4.setText(Autonomous.c.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Teleop.spinner4.setText(Autonomous.c.getSelectedItem().toString());
            }

        });
        d.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Teleop.spinner5.setText(Autonomous.d.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Teleop.spinner5.setText(Autonomous.d.getSelectedItem().toString());
            }

        });
        highView = (TextView) in.findViewById(R.id.highGoalView1);
        lowView = (TextView) in.findViewById(R.id.lowGoalView1);

        highHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highView.append("1 ");
            }
        });

        highMiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highView.append("0 ");
            }
        });

        highDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (highView.getText().length() > 0) {
                    highView.setText(highView.getText().subSequence(0, highView.getText().length() - 2));
                }
            }
        });

        lowHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowView.append("1 ");
            }
        });

        lowMiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowView.append("0 ");
            }
        });

        lowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lowView.getText().length() > 0) {
                    lowView.setText(lowView.getText().subSequence(0, lowView.getText().length() - 2));
                }
            }
        });

        // 2/17 revision
        matchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                String  str = matchText.getText().toString();
                myRobo.setMatchT(str);
            }
        });

        teamText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                String  str = teamText.getText().toString();
                myRobo.setTeamT(str);
            }
        });

        scouterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                String  str = scouterText.getText().toString();
                myRobo.setScouterT(str);
            }
        });

        return in;
    }

    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView != zero) {
                    zero.setChecked(false);
                }
                if (buttonView != one) {
                    one.setChecked(false);
                }
                if (buttonView != two) {
                    two.setChecked(false);
                }
                if (buttonView != three) {
                    three.setChecked(false);
                }
                if (buttonView == zero) {
                    String str = zero.getText().toString();
                    myRobo.setBalls(str);
                }
                else if (buttonView == one) {
                    String str = one.getText().toString();
                    myRobo.setBalls(str);
                }
                else if (buttonView == two) {
                    String str = two.getText().toString();
                    myRobo.setBalls(str);
                }
                else if (buttonView == three) {
                    String str = three.getText().toString();
                    myRobo.setBalls(str);
                }
            }
        }
    };

  /*  @Override
    public RoboInfo getData() {
        this.autoInfo.matchT = this.matchT; // Assuming subcon has been updated.. else use txt1.getText();
        return this.autoInfo;
    }

    @Override
    public void setData(RoboInfo workData) {
        this.autoInfo = workData;
        // Update this page's views with the workData...
        // This assumes the fragment has already been created and txt1 is set to a view
        this.matchT = workData.matchT; // Actually could just use subCon in workData, but be aware that workData actually points to the Activity's copy (kinda makes getdata redundant.. but I like symmetry and couldn't be bothered making lots of copies of the object).
    }

    public static Autonomous newInstance(String a)
    {
        Autonomous fragment=new Autonomous();
        Bundle bundle=new Bundle();
        bundle.putString("a", "matchText");
        fragment.setArguments(bundle);
        return fragment;
    }*/
}


