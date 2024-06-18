import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Screen extends JPanel {

    public static final int WIDTH = 200, HEIGHT = 90;

    Point[] points;

    JLabel pointInfo;
    JButton startButton, numPointsB;
    JSpinner startNum, endNum;

    boolean running = false;
    boolean selecting;

    public Screen() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setDoubleBuffered(true);
        this.setLayout(null);

        selecting = false;

        int x = 0;
        int y = 0;

        JLabel startInfoL = new JLabel("Start (>=0)");
        startInfoL.setBounds(x,y,100,15);
        this.add(startInfoL);

        x += 100;

        JLabel endInfoL = new JLabel("End (0 = Inf)");
        endInfoL.setBounds(x,y,100,15);
        this.add(endInfoL);

        x = 0;
        y += 15;
        startNum = new JSpinner();
        startNum.setBounds(x, y, 100, 20);
        this.add(startNum);

        x += 100;

        endNum = new JSpinner();
        endNum.setBounds(x, y, 100, 20);
        this.add(endNum);

        x = 0;
        y += 20;

        numPointsB = new JButton("Select Points (p)");
        numPointsB.setBounds(x,y,200,20);
        numPointsB.addActionListener(list -> {
            if (!selecting) {
                points = new Point[2];
                selecting = true;
            }
        });
        this.add(numPointsB);

        y += 20;

        pointInfo = new JLabel("(x,y)   (x,y)", SwingConstants.CENTER);
        pointInfo.setBounds(x,y,200,15);
        this.add(pointInfo);

        y += 15;

        startButton = new JButton("Start");
        startButton.setBounds(x,y,200,20);
        startButton.addActionListener(list -> {
            if (points != null && points[0] != null && points[1] != null) {
                try {
                    start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
        this.add(startButton);

    }

    private void updateLabel() {
        if (points != null) {
            String s = "";
            if (points[0] != null) {
                s += "("+points[0].x+","+points[0].y+")";
            } else {
               s += "(x,y)";
            }
            s += "   ";
            if (points[1] != null) {
                s += "("+points[1].x+","+points[1].y+")";
            } else {
                s += "(x,y)";
            }
            pointInfo.setText(s);
        } else {
            pointInfo.setText("(x,y)   (x,y)");
        }
    }

    int curCount;
    int endCount;
    private void start() throws InterruptedException {
        curCount = (int) startNum.getValue();
        endCount = (int) endNum.getValue();

        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        if (r == null) System.exit(0);

        if (endCount == 0) {
            for (;;) {
                countNext(r);
                Thread.sleep(1500);
            }
        } else {
            for (int i = curCount; i <= endCount; i++) {
                countNext(r);
                Thread.sleep(1500);
            }
        }
    }

    private void countNext(Robot r) throws InterruptedException {

        Point p = points[curCount%2];
        r.mouseMove(p.x, p.y);
        Thread.sleep(10);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);

        writeNumber(r, curCount);

        curCount++;
    }

    public void keyPressed(int code) {
        switch (code) {
            case 25:
                // P
                if (this.selecting) {
                    if (points[0] == null) {
                        points[0] = MouseInfo.getPointerInfo().getLocation();
                    } else {
                        points[1] = MouseInfo.getPointerInfo().getLocation();
                        this.selecting = false;
                    }
                    updateLabel();
                }
                break;
            case 16:
                // Q
                System.exit(0);
                break;
        }
    }

    private static void writeNumber(Robot robot, int num) {
        String str = ""+num;
        for (char c : str.toCharArray()) {
            writeNumChar(robot, Integer.parseInt(""+c));
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private static void writeNumChar(Robot robot, int num) {
        switch (num) {
            case 0:
                robot.keyPress(KeyEvent.VK_0);
                robot.keyRelease(KeyEvent.VK_0);
                break;
            case 1:
                robot.keyPress(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_1);
                break;
            case 2:
                robot.keyPress(KeyEvent.VK_2);
                robot.keyRelease(KeyEvent.VK_2);
                break;
            case 3:
                robot.keyPress(KeyEvent.VK_3);
                robot.keyRelease(KeyEvent.VK_3);
                break;
            case 4:
                robot.keyPress(KeyEvent.VK_4);
                robot.keyRelease(KeyEvent.VK_4);
                break;
            case 5:
                robot.keyPress(KeyEvent.VK_5);
                robot.keyRelease(KeyEvent.VK_5);
                break;
            case 6:
                robot.keyPress(KeyEvent.VK_6);
                robot.keyRelease(KeyEvent.VK_6);
                break;
            case 7:
                robot.keyPress(KeyEvent.VK_7);
                robot.keyRelease(KeyEvent.VK_7);
                break;
            case 8:
                robot.keyPress(KeyEvent.VK_8);
                robot.keyRelease(KeyEvent.VK_8);
                break;
            case 9:
                robot.keyPress(KeyEvent.VK_9);
                robot.keyRelease(KeyEvent.VK_9);
                break;
        }
    }

}
