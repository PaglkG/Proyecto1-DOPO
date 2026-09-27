package slotMachine.test;


import slotMachine.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shapes.Canvas;

import java.util.Map;

/**
 * The test class Problem.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Problem
{
    private SlotMachine slmch;
    private Map<Integer, Wheel> wheels;
    private static int numWheels;
    
    @Test
    public void simulate() throws InterruptedException {
        slmch = new SlotMachine(3);
        slmch.simulate(3);
        slmch.solve(3);
        slmch = new SlotMachine(5);
        slmch.simulate(5);
        slmch.solve(5);
        slmch = new SlotMachine(10);
        slmch.simulate(10);
        slmch.solve(10);
        slmch = new SlotMachine(4);
        slmch.simulate(4);
        slmch.solve(4);
    }
}