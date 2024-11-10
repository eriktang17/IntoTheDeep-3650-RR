package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawVert {
    private Servo left, right, claw;
    public boolean open;

    public ClawVert(HardwareMap hardwareMap) {
        left = hardwareMap.get(Servo.class, "leftUp");
        right = hardwareMap.get(Servo.class, "rightUp");
        claw = hardwareMap.get(Servo.class, "clawUp");

        left.scaleRange(0.38, 0.6);
        right.scaleRange(0.45, 0.68);
        claw.scaleRange(0, 1);
        open = false;
    }

    public void close() {
        right.setPosition(0);
        left.setPosition(1);
        open = false;
    }

    public void open() {
        right.setPosition(1);
        left.setPosition(0);
        open = true;
    }

    public void in() {
        //claw.setDirection(Servo.Direction.FORWARD);
        claw.setPosition(0);
    }
    public void out() {
        //claw.setDirection(Servo.Direction.REVERSE);
        claw.setPosition(1);
    }
}

