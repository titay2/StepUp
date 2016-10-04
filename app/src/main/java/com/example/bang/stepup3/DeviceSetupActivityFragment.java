package com.example.bang.stepup3;

/**
 * Created by Bang on 10/2/2016.
 */

import android.app.Activity;
import android.app.Instrumentation;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbientlab.metawear.AsyncOperation;
import com.mbientlab.metawear.DataSignal;
import com.mbientlab.metawear.Message;
import com.mbientlab.metawear.MetaWearBleService;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.RouteManager;
import com.mbientlab.metawear.UnsupportedModuleException;
import com.mbientlab.metawear.data.CartesianFloat;
import com.mbientlab.metawear.module.Accelerometer;
import com.mbientlab.metawear.module.Bmi160Accelerometer;
import com.mbientlab.metawear.module.Bmi160Accelerometer.ProofTime;
import com.mbientlab.metawear.module.Bmi160Accelerometer.SkipTime;
import com.mbientlab.metawear.module.DataProcessor;
import com.mbientlab.metawear.module.Switch;
import com.mbientlab.metawear.module.Timer;
import com.mbientlab.metawear.processor.Counter;

import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class DeviceSetupActivityFragment extends Fragment implements ServiceConnection {
    public interface FragmentSettings {
        BluetoothDevice getBtDevice();
    }

    private MetaWearBoard mwBoard;
    private FragmentSettings settings;
    Bmi160Accelerometer bmi160AccModule;
    Timer timer;
    Switch switchModule;
    private TextView count;


    public DeviceSetupActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity owner = getActivity();
        if (!(owner instanceof FragmentSettings)) {
            throw new ClassCastException("Owning activity must implement the FragmentSettings interface");
        }

        settings = (FragmentSettings) owner;
        owner.getApplicationContext().bindService(new Intent(owner, MetaWearBleService.class), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ///< Unbind the service when the activity is destroyed
        getActivity().getApplicationContext().unbindService(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_device_setup, container, false);
        count = (TextView)view.findViewById(R.id.textView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.acc_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bmi160AccModule.routeData().fromAxes().stream("acc_stream").commit()
//                        .onComplete(new AsyncOperation.CompletionHandler<RouteManager>() {
//                            @Override
//                            public void success(RouteManager result) {
//                                result.subscribe("acc_stream", new RouteManager.MessageHandler() {
//                                    @Override
//                                    public void process(Message msg) {
//                                        Log.i("tutorial", msg.getData(CartesianFloat.class).toString());
//                                    }
//                                });
//
//                                bmi160AccModule.enableAxisSampling();
//                                bmi160AccModule.start();
//                            }
//                        });

                bmi160AccModule.enableStepDetection();
                bmi160AccModule.start();

                bmi160AccModule.readStepCounter(false);

                //Step counter
                bmi160AccModule.routeData().fromStepCounter(false).stream("step_counter").commit()
                        .onComplete(new AsyncOperation.CompletionHandler<RouteManager>() {
                            @Override
                            public void success(final RouteManager result) {
                                result.subscribe("step_counter", new RouteManager.MessageHandler() {
                                    @Override
                                    public void process(Message msg) {
                                        String text = "Count: " + msg.getData(Integer.class);
                                        Log.i("MainActivity", "Steps= " + msg.getData(Integer.class));
//                                        count.setText(msg.getData(Integer.class));
                                        sensorMsg(text);

                                    }
                                });
                            }
                        });
                //Time interval
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void commands() {
                        bmi160AccModule.readStepCounter(false);
                    }
                }, 1000, false).onComplete(new AsyncOperation.CompletionHandler<Timer.Controller>() {
                    @Override
                    public void success(Timer.Controller result) {
                        result.start();
                    }
                });

                // Receive notifications for each step detected
                bmi160AccModule.routeData().fromStepDetection().stream("step_detector").commit()
                        .onComplete(new AsyncOperation.CompletionHandler<RouteManager>() {
                            @Override
                            public void success(RouteManager result) {
                                result.subscribe("step_detector", new RouteManager.MessageHandler() {
                                    @Override
                                    public void process(Message msg) {
//                                        final String axes = msg.getData(Integer.class).toString();
                                        Log.i("MainActivity", "You took a step");
//                                        Log.i("MainActivity", "Steps= " + msg.getData(Integer.class));
//                                        Tried to display data to UI
//                                        sensorMsg(axes);
                                     }
                                });
                            }

                        });

                bmi160AccModule.routeData().fromStepDetection()
                        // Count how many digital input samples have passed through
                        .process("ccounter?size=2", new Counter())
                        .commit();
            }
        });
        view.findViewById(R.id.acc_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmi160AccModule.stop();
                bmi160AccModule.resetStepCounter();
//                bmi160AccModule.disableAxisSampling();
//                mwBoard.removeRoutes();
            }
        });
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mwBoard = ((MetaWearBleService.LocalBinder) service).getMetaWearBoard(settings.getBtDevice());
        ready();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    /**
     * Called when the app has reconnected to the board
     */
    public void reconnected() {
    }

    /**
     * Called when the mwBoard field is ready to be used
     */
    public void ready() {
        try {
            bmi160AccModule = mwBoard.getModule(Bmi160Accelerometer.class);
            timer = mwBoard.getModule(Timer.class);
            switchModule = mwBoard.getModule(Switch.class);

//            bmi160AccModule.configureAxisSampling()
//                    .setFullScaleRange(Bmi160Accelerometer.AccRange.AR_16G)
//                    .setOutputDataRate(Bmi160Accelerometer.OutputDataRate.ODR_100_HZ)
//                    .commit();
            bmi160AccModule.configureStepDetection()
                    // Set sensitivity to normal
                    .setSensitivity(Bmi160Accelerometer.StepSensitivity.NORMAL)
                    // Enable step counter
                    .enableStepCounter()
                    .commit();
//            bmi160AccModule.enableAxisSampling();

        } catch (UnsupportedModuleException e) {
            Snackbar.make(getActivity().findViewById(R.id.device_setup_fragment), e.getMessage(),
                    Snackbar.LENGTH_SHORT).show();
        }

    }


    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            String msg = "Count: " + (int)event.values[0];
            count.setText(msg);
            System.out.println(msg);
        }
    }

    public void sensorMsg(final String msg) {
        final String reading = msg;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                view.setContentView(R.layout.fragment_device_setup);
                count.setText(reading);


//                count.setText("Steps: " + reading.getData(Integer.class));
            }
        });
    }

}
