package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claws {
    private Servo leftUp, rightUp, leftDown, rightDown, clawUp, clawDown;
    public Claws(HardwareMap hardwareMap) {
        leftUp = hardwareMap.get(Servo.class, "leftUp");
        rightUp = hardwareMap.get(Servo.class, "rightUp");
        clawUp = hardwareMap.get(Servo.class, "clawUp");
        leftDown = hardwareMap.get(Servo.class, "leftDown");
        rightDown = hardwareMap.get(Servo.class, "rightDown");
        clawDown = hardwareMap.get(Servo.class, "clawDown");

        leftUp.scaleRange(0, 1);
        rightUp.scaleRange(0, 1);
        clawUp.scaleRange(0, 1);
        leftDown.scaleRange(0, 1);
        rightDown.scaleRange(0, 1);
        clawDown.scaleRange(0, 1);
    }

    public void openUp() {}
    public void closeUp() {}
    public void openDown() {}
    public void closeDown() {}
}
