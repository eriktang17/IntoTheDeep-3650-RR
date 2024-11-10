package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SlidesHorz {
    private DcMotorEx left, right;
    private static double targetPosition;
    private final double Kp = 6;
    private final double Ki = 0;
    private final double Kd = 0;
    private static double left_error, right_error;
    private static double previous_left_error, previous_right_error;
    private static double d_left, d_right;
    private static double i_left, i_right;
    private static double leftVelocity, rightVelocity;
    private final double MAX_ROTATION_RATE = 2400;
    private static final int[] POSITIONS = {0, 1500};
    private ElapsedTime timer;
    public SlidesHorz(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotorEx.class, "leftHorizontal");
        right = hardwareMap.get(DcMotorEx.class, "rightHorizontal");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        targetPosition = 0;
        i_left = 0;
        i_right = 0;
        timer = new ElapsedTime();
    }
    public void setTarget(double target) {targetPosition = target;}
    public void setDefaultTarget(int in) {
        setTarget(POSITIONS[in]);
        if (in == 0) {
            timer.reset();
        }
    }
    public void runToTargetPID(long clock_time) {
        //if (mode != 1) return;
        left_error = targetPosition - left.getCurrentPosition();
        right_error = -targetPosition - right.getCurrentPosition();

        d_left = (left_error - previous_left_error) / (clock_time * Math.pow(10, -9));
        d_right = (right_error - previous_right_error) / (clock_time * Math.pow(10, -9));
        i_left += left_error * clock_time * Math.pow(10, -9);
        i_right += right_error * clock_time * Math.pow(10, -9);
        previous_left_error = left_error;
        previous_right_error = right_error;

        leftVelocity = clamp(-MAX_ROTATION_RATE, Kp * left_error + Kd * d_left + Ki * i_left, MAX_ROTATION_RATE);
        rightVelocity = clamp(-MAX_ROTATION_RATE, Kp * right_error + Kd * d_right + Ki * i_right, MAX_ROTATION_RATE);

        left.setVelocity(leftVelocity);
        right.setVelocity(rightVelocity);
    }
    public boolean ready() {return timer.nanoseconds() > 1*Math.pow(10, 9);}
    public boolean set() {return timer.nanoseconds() > 2*Math.pow(10, 9);}
    public boolean go() {return timer.nanoseconds() > 2.5*Math.pow(10, 9);}
    private double clamp(double a, double b, double c) {
        return Math.min(Math.max(a, b), c);
    }
}
