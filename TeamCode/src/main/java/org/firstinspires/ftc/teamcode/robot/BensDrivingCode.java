package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class BensDrivingCode {

    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;

    public BensDrivingCode (HardwareMap hardwareMap) {
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
    }
        public void BensMecanumMath(double theta, double power, double turn) {
            double sin = Math.sin(theta - Math.PI/4);
            double cos = Math.cos(theta - Math.PI/4);
            double max = Math.max(Math.abs(sin), Math.abs(cos));

            double frontRight = power * sin/max - turn;
            double backRight = power * cos/max - turn;
            double frontLeft = power * cos/max + turn;
            double backLeft =  power * sin/max +turn;

            if ((power + Math.abs(turn)) > 1); {
                frontRight /= power + turn;
                backRight /= power + turn;
                frontLeft /= power + turn;
                backLeft /= power + turn;
            }

        }

    }




