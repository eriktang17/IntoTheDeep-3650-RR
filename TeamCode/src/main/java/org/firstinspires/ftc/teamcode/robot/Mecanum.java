package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.lang.Math;

public class Mecanum {

    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;

    public Mecanum (HardwareMap hardwareMap) {
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
    }
    public void setDrive(double x, double y, double turn, double scale) {

        double fr = x - y - turn;
        double br = -x - y - turn;
        double fl = -x + y - turn;
        double bl = x + y - turn;

        double max = Math.sqrt(x*x + y*y) + Math.abs(turn);
        if (max > 1); {
            fr /= max;
            br /= max;
            fl /= max;
            bl /= max;
        }

        frontRight.setPower(fr*scale);
        backRight.setPower(br*scale);
        frontLeft.setPower(fl*scale);
        backLeft.setPower(bl*scale);
    }

}
