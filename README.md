# StepUp

`StepUp` is an Android application which basically tracks steps of users. We wanted to encourage people to excercise.
Unlike other pedometer apps which track steps of users and convert those steps into calories, our app converts the foods that users eat into steps and calories.

## Team members:
- Hai Phan
- Bang Nguyen
- Tehetena Behailu

## Screenshots

![alt tag](https://github.com/titay2/StepUp/blob/master/14632770_1312896135421267_512722842_o.jpg | width=200)
![alt tag](https://github.com/titay2/StepUp/blob/master/14672651_1312896115421269_125766546_o.jpg | width=200)

## Features
- Scan for bluetooth device
- Add food
- Show list of food
- Count steps
- Count calories
- Track steps in the background
- UI Design follows Material Design


##Code example:
We used BMI160 Accelerometer sensor to detect steps.

```Java
bmi160AccModule.enableStepDetection();
bmi160AccModule.start();
bmi160AccModule.readStepCounter(false);

bmi160AccModule.routeData().fromStepDetection().stream("step_detector").commit()
    .onComplete(new AsyncOperation.CompletionHandler<RouteManager>() {
        @Override
        public void success(RouteManager result) {
            result.subscribe("step_detector", new RouteManager.MessageHandler() {
                @Override
                public void process(Message msg) {
                    stepsTaken++;
                    calculateStepsLeft();
                    calculateCaloriesLeft();
                    setStepCount();
                    setCaloriesCount();
                }
            });
        }
});
```


