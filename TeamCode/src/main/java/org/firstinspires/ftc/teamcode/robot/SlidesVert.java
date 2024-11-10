package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SlidesVert {
    private DcMotorEx left, right;
    private static double targetPosition;
    private final double Kp = 30;
    private final double Ki = 0;
    private final double Kd = 0;
    private static double left_error, right_error;
    private static double previous_left_error, previous_right_error;
    private static double d_left, d_right;
    private static double i_left, i_right;
    private static double leftVelocity, rightVelocity;
    private final double MAX_ROTATION_RATE = 2400;
    private static final int[] POSITIONS = {0, 2900};
    public SlidesVert(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotorEx.class, "leftVertical");
        right = hardwareMap.get(DcMotorEx.class, "rightVertical");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        targetPosition = 0;
        i_left = 0;
        i_right = 0;
    }
    public void setTarget(double target) {targetPosition = target;}
    public void setDefaultTarget(int in) {setTarget(POSITIONS[in]);}
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
    private double clamp(double a, double b, double c) {
        return Math.min(Math.max(a, b), c);
    }
}