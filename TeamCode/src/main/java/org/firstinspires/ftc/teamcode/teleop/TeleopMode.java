package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.ClawHorz;
import org.firstinspires.ftc.teamcode.robot.ClawVert;
import org.firstinspires.ftc.teamcode.robot.Mecanum;
import org.firstinspires.ftc.teamcode.robot.SlidesHorz;
import org.firstinspires.ftc.teamcode.robot.SlidesVert;

@TeleOp
public class TeleopMode extends LinearOpMode {

    private long clock_time = 0;
    private boolean isPickup, onVertical;
    private double currentRotation;
    double gamepadX, gamepadY, turn, x, y;
    static public BNO055IMU.Parameters parameters;
    static public BNO055IMU imu;
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime timer = new ElapsedTime();
        Mecanum mecanumDrive = new Mecanum(hardwareMap);
        SlidesHorz horizontalSlides = new SlidesHorz(hardwareMap);
        SlidesVert verticalSlides = new SlidesVert(hardwareMap);
        ClawVert verticalClaw = new ClawVert(hardwareMap);
        ClawHorz horizontalClaw = new ClawHorz(hardwareMap);
        isPickup = false;
        onVertical = true;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        waitForStart();
        while(opModeIsActive()) {

            gamepadX = gamepad1.left_stick_x;
            gamepadY = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;

            currentRotation = imu.getAngularOrientation().firstAngle;

            x = Math.cos(currentRotation)*gamepadX - Math.sin(currentRotation)*gamepadY;
            y = Math.sin(currentRotation)*gamepadX + Math.cos(currentRotation)*gamepadY;

            if (!gamepad1.guide) {
                if (gamepad1.a) {
                    mecanumDrive.setDrive(x, y, turn, 1);

                }
                else {
                    mecanumDrive.setDrive(x, y, turn, 0.5);
                }
            }

            clock_time = timer.nanoseconds();
            timer.reset();

            if (gamepad1.y && !isPickup) {
                horizontalSlides.setDefaultTarget(0);
                verticalSlides.setDefaultTarget(0);
                horizontalClaw.in();
                verticalClaw.open();
                isPickup = true;
                onVertical = true;
            }

            if (horizontalSlides.ready() && isPickup) {
                verticalClaw.in();
            }

            if (horizontalSlides.set() && isPickup) {
                verticalClaw.close();
            }

            if (horizontalSlides.go() && isPickup) {
                horizontalClaw.open();
                horizontalClaw.out();
                verticalClaw.out();
                verticalSlides.setDefaultTarget(1);
                isPickup = false;
            }

            if (gamepad1.x && !isPickup) {
                horizontalSlides.setDefaultTarget(1);
                verticalSlides.setDefaultTarget(0);
                horizontalClaw.out();
                horizontalClaw.open();
                onVertical = false;
            }

            horizontalSlides.runToTargetPID(clock_time);
            verticalSlides.runToTargetPID(clock_time);

            if (gamepad1.right_bumper && !isPickup) {
                if (onVertical) {
                    verticalClaw.open();
                }
                else {
                    horizontalClaw.close();
                }
            }

            if (gamepad1.left_bumper && !isPickup) {
                if (onVertical) {
                    verticalClaw.close();
                }
                else {
                    horizontalClaw.open();
                }
            }
        }
    }
}
