package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ClawHorz {
    private Servo left, right, claw;
    public boolean open;

    public ClawHorz(HardwareMap hardwareMap) {
        left = hardwareMap.get(Servo.class, "leftDown");
        right = hardwareMap.get(Servo.class, "rightDown");
        claw = hardwareMap.get(Servo.class, "clawDown");

        left.scaleRange(0.2,0.78);
        right.scaleRange(0.03,0.6);
        claw.scaleRange(0.18,0.98);
        open = false;
    }

    public void open() {
        right.setPosition(1);
        left.setPosition(0);
        open = true;
    }

    public void close() {
        right.setPosition(0);
        left.setPosition(1);
        open = false;
    }

    public void in() {
        //claw.setDirection(Servo.Direction.FORWARD);
        claw.setPosition(1);
    }
    public void out() {
        //claw.setDirection(Servo.Direction.REVERSE);
        claw.setPosition(0);
    }
}
