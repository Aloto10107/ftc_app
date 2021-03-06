package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Devices.DriveBase;

import static com.sun.tools.javac.util.Constants.format;

/**
 * Created by aloto10107 on 10/14/17.
 */

public class RedLeftAuto extends LinearOpMode {

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    DriveBase drive;
    Orientation angles;
    VectorF trans;
    public double Yerror;
    public double Xerror;
    public double Xtarget;
    double tY = 0;
    double tX = 0;
    double tZ = 0;
    double rX = 0;
    double rY = 0;
    double rZ = 0;
    long Distance = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new DriveBase(hardwareMap);
        drive.imuINIT();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AVRhcM3/////AAAAGdetb+T75E2Rt8tVnrcGqXuLsDscSqvkPx0UOvd40mxhtsQ1JoHpzZAC8DZVa0sfCpfEsmB4m4fGZkchjlAQ9Xw8GTRxvXSfmFIaog08ORICJVLzdCRAzICnf3pLy4nrT23pFZGwtwS4qau9nlNJbGpFXP56No+xcgwTzl9ZnRdZlUoUmdv12c0Ljsx2ZtoReB8MfZF7hOVe4pCwxhYFnRUUj2LPTkm62g+DSbdZXF2hlQIsoqr3jYlyShpa/CRWf4ab4NsS+OT2wY85TZd0ZlzMBGxxCFKNMPRSaMJfazDr13J472e5jA20Flq5fOKqrxzfZK19mH6jUhENsJtG99GxZkWHxcBFqmmN8WmukUzX";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTrackables.activate();
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

/*        while(vuMark != null){
            trans = pose.getTranslation();
            telemetry.addData("distance", trans.get(0));
            telemetry.addData(">", "Press Play to start");
            telemetry.update();
            if (isStarted()){
                break;
            }
        }*/
        telemetry.addData(">","" +
                "                 .eeeeeeeee\n" +
                "                .$$$$$$$$P\"\n" +
                "               .$$$$$$$$P\n" +
                "              z$$$$$$$$P\n" +
                "             z$$$$$$$$\"\n" +
                "            z$$$$$$$$\"\n" +
                "           d$$$$$$$$\"\n" +
                "          d$$$$$$$$\"\n" +
                "        .d$$$$$$$P\n" +
                "       .$$$$$$$$P\n" +
                "      .$$$$$$$$$.........\n" +
                "     .$$$$$$$$$$$$$$$$$$\"\n" +
                "    z$$$$$$$$$$$$$$$$$P\"\n" +
                "   -**********$$$$$$$P\n" +
                "             d$$$$$$\"\n" +
                "           .d$$$$$$\"\n" +
                "          .$$$$$$P\"\n" +
                "         z$$$$$$P\n" +
                "        d$$$$$$\"\n" +
                "      .d$$$$$$\"\n" +
                "     .$$$$$$$\"\n" +
                "    z$$$$$$$beeeeee\n" +
                "   d$$$$$$$$$$$$$*\n" +
                "  ^\"\"\"\"\"\"\"\"$$$$$\"\n" +
                "          d$$$*\n" +
                "         d$$$\"\n" +
                "        d$$*\n" +
                "       d$P\"\n" +
                "     .$$\"\n" +
                "    .$P\"\n" +
                "   .$\"\n" +
                "  .P\"\n" +
                " .\"     Gilo94'\n" +
                "/\"");
        while (!isStarted()) {
            telemetry.addData("sonar",drive.getSonar());
            telemetry.update();
        }        waitForStart();

        sleep(1000);
        drive.setLift(-.5);
        sleep(1500);
        drive.setLift(0);
        drive.upanddown.setPosition(0);
        Thread.sleep(2500);
        if((drive.getColor()[0] - drive.getColor()[2])*1.0/drive.getColor()[0] >= .5)
        {
            drive.turn(.5,100);
            Thread.sleep(100);
            drive.upanddown.setPosition(1);
            Thread.sleep(100);
            drive.turn(-.5,100);
        }
        else if((drive.getColor()[0] - drive.getColor()[2])*1.0/drive.getColor()[0] <= 0.1)
        {
            drive.turn(-.5,100);
            Thread.sleep(100);
            drive.upanddown.setPosition(1);
            Thread.sleep(100);
            drive.turn(.5, 100);
        }
        drive.upanddown.setPosition(1);
        Thread.sleep(1000);

        while (true)
        {
            drive.cry();
            telemetry.addData("Tears", drive.tears);
            telemetry.update();
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if ((vuMark != null && vuMark != RelicRecoveryVuMark.UNKNOWN) || drive.tears >= 100000)
            {
                if(vuMark == RelicRecoveryVuMark.RIGHT || vuMark == RelicRecoveryVuMark.UNKNOWN){
                    Distance = 150;
                }
                if(vuMark == RelicRecoveryVuMark.CENTER){
                    Distance = 1400;
                }
                if(vuMark == RelicRecoveryVuMark.LEFT){
                    Distance = 1750;
                }
                /*drive.setBoth(-.25,-.25);
                sleep(2500);
                drive.setBoth(0,0);*/
                drive.FrontandBack(-.25, 2500,0);
                /*drive.setBoth(-.5,-.5);
                sleep(750);
                drive.setBoth(0,0);*/
                drive.FrontandBack(-.5,750,0);
                telemetry.addData("VuMark", "%s visible", vuMark);
                telemetry.update();
                break;
            }
        }
        //srikar is gay
        /*drive.setBoth(-.5,-.5);
        sleep(Distance);
        drive.setBoth(0,0);*/
        drive.FrontandBack(-.5,Distance,0);
        drive.gyroTurn(90);
        sleep(1000);
        drive.setBoth(.5,.5);
        sleep(1500);
        drive.setBoth(0,0);
        drive.setBoth(-.5,-.5);
        sleep(700);
        drive.setBoth(0,0);
        sleep(900);
        drive.setBoth(.5,.5);
        sleep(1000);
        drive.setBoth(0,0);
        sleep(900);
        drive.setBoth(-.5,-.5);
        sleep(800);
        drive.setBoth(0,0);


/*        drive.gyroTurn(-90);
        sleep(500);
        drive.BodGot();
        sleep(100);
        drive.setLift(.5);
        sleep(800);
        drive.setLift(0);
        sleep(100);
        drive.FrontandBack(.5,2900,-90);
        drive.NoBodGot();
        drive.bluepinch();
        drive.redpinch();
        drive.FrontandBack(-.5,1000,-90);
        drive.gyroTurn(90);
        sleep(100);
        drive.setLift(-.5);
        sleep(3000);
        drive.setLift(0);
        drive.FrontandBack(.5,2900, 90);
        drive.bluenotPinch();
        drive.rednotPinch();
        sleep(200);
        drive.FrontandBack(-.5,200,90);*/


        telemetry.addData("error", String.valueOf(drive.Gerror));
        telemetry.addData("red", drive.getColor()[0]);
        telemetry.addData("green", drive.getColor()[1]);
        telemetry.addData("blue", drive.getColor()[2]);
        telemetry.addData("Distance", drive.Derror);
        telemetry.update();
    }
    public void drive(double power, double distance){
        ElapsedTime runtime = new ElapsedTime();

        if (opModeIsActive()){

            int targetLeftFront = drive.leftMotors[0].getCurrentPosition() + (int)(distance*drive.COUNTS_PER_CM);
            int targetRightFront = drive.rightMotors[0].getCurrentPosition() + (int)(distance*drive.COUNTS_PER_CM);
            int targetLeftBack = drive.leftMotors[1].getCurrentPosition() + (int)(distance*drive.COUNTS_PER_CM);
            int targetRightBack = drive.rightMotors[1].getCurrentPosition() + (int)(distance*drive.COUNTS_PER_CM);

            drive.leftMotors[0].setTargetPosition(targetLeftFront);
            drive.rightMotors[0].setTargetPosition(targetRightFront);
            drive.leftMotors[1].setTargetPosition(targetLeftBack);
            drive.rightMotors[1].setTargetPosition(targetRightBack);

            drive.leftMotors[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            drive.rightMotors[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            drive.leftMotors[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            drive.rightMotors[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            drive.rightMotors[0].setPower(Math.abs(power));
            drive.leftMotors[1].setPower(Math.abs(power));
            drive.leftMotors[1].setPower(Math.abs(power));
            drive.leftMotors[0].setPower(Math.abs(power));

            while (opModeIsActive() && drive.rightMotors[0].isBusy() && drive.leftMotors[0].isBusy() && drive.rightMotors[1].isBusy() && drive.leftMotors[1].isBusy() && (runtime.seconds() < 5)){
                telemetry.addData("Path1",  "Running to %7d :%7d", targetLeftFront,  targetRightFront);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        drive.leftMotors[0].getCurrentPosition(),
                        drive.rightMotors[0].getCurrentPosition());
                telemetry.update();
            }
            drive.rightMotors[0].setPower(0);
            drive.leftMotors[1].setPower(0);
            drive.leftMotors[1].setPower(0);
            drive.leftMotors[0].setPower(0);


        }
    }
}


