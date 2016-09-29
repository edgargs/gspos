package com.gs.mifarma;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;


import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TestKeyEvents {
    public static void main(String[] args) {
        new TestKeyEvents();
    }
    public TestKeyEvents() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }
                /*JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);*/
                new Thread(new KeyDispatcher()).start();
            }
        });
    }
    public class TestPane extends JPanel {
        public TestPane() {
            setLayout(new BorderLayout());
            JTextArea area = new JTextArea(10, 30);
            area.setWrapStyleWord(true);
            area.setLineWrap(true);
            add(area);
        }
    }
    public class KeyDispatcher implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
            //dispatchKeyEventsViaEventQueue();
            dispatchKeyEventsViaRobot();
        }

        protected void dispatchKeyEventsViaEventQueue() {
            if (EventQueue.isDispatchThread()) {
                String text = "This is a key sequence dispatched via the event queue\n";
                KeySequence keySequence = getKeySequence(text);
                List<KeyEvent> events = new ArrayList<KeyEvent>();
                List<Integer> modifers = new ArrayList<Integer>();
                for (Key key : keySequence) {
                    events.clear();
                    System.out.println(key);
                    switch (key.getStrokeType()) {
                        case Press:
                            switch (key.getKeyCode()) {
                                case KeyEvent.VK_SHIFT:
                                case KeyEvent.VK_ALT:
                                case KeyEvent.VK_CONTROL:
                                case KeyEvent.VK_META:
                                    if (!modifers.contains(key.getKeyCode())) {
                                        modifers.add(key.getKeyCode());
                                    }
                                    break;
                                default:
                                    events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), getModifiers(modifers), key.getKeyCode(), key.getKeyChar()));
                                    break;
                            }
                            break;
                        case Release:
                            switch (key.getKeyCode()) {
                                case KeyEvent.VK_SHIFT:
                                case KeyEvent.VK_ALT:
                                case KeyEvent.VK_CONTROL:
                                case KeyEvent.VK_META:
                                    if (!modifers.contains(key.getKeyCode())) {
                                        modifers.remove(key.getKeyCode());
                                    }
                                    break;
                                default:
                                    events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), getModifiers(modifers), key.getKeyCode(), key.getKeyChar()));
                                    break;
                            }
                            break;
                        case Type:
                            events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), getModifiers(modifers), key.getKeyCode(), key.getKeyChar()));
                            events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), getModifiers(modifers), key.getKeyCode(), key.getKeyChar()));
                            events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_TYPED, System.currentTimeMillis(), getModifiers(modifers), KeyEvent.VK_UNDEFINED, key.getKeyChar()));
                            break;
                    }

                    for (KeyEvent evt : events) {
                        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(evt);
                    }
                }
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            dispatchKeyEventsViaEventQueue();
                        }
                    });
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        }

        protected void dispatchKeyEventsViaRobot() {
            try {
                Robot robot = new Robot();
                System.out.println("---------------");
                String text = "a";
                KeySequence keySequence = getKeySequence(text);
                List<KeyEvent> events = new ArrayList<>();
                for (Key key : keySequence) {
                    events.clear();
                    System.out.println(key);
                    switch (key.getStrokeType()) {
                        case Press:
                            robot.keyPress(key.getKeyCode());
                            break;
                        case Release:
                            robot.keyRelease(key.getKeyCode());
                            break;
                        case Type:
                            robot.keyPress(key.getKeyCode());
                            robot.keyRelease(key.getKeyCode());
                            break;
                    }
                    
                }
            } catch (AWTException exp) {
                exp.printStackTrace();
            }
        }
    }

    public static  int getModifiers(List<Integer> mods) {
        int result = 0;
        for (int mod : mods) {
            result &= mod;
        }
        return result;
    }

    public static class Key {
        public enum StrokeType {
            Type,
            Press,
            Release
        }
        private StrokeType strokeType;
        private int keyCode;
        private char keyChar;
        public Key(StrokeType type, int keyCode, char keyChar) {
            this.strokeType = type;
            this.keyCode = keyCode;
            this.keyChar = keyChar;
        }

        public StrokeType getStrokeType() {
            return strokeType;
        }

        public int getKeyCode() {
            return keyCode;
        }

        public char getKeyChar() {
            return keyChar;
        }

        @Override
        public String toString() {
            return getStrokeType().name() + " " + getKeyChar() + " (" + getKeyCode() + ")";
        }
    }

    public static KeySequence getKeySequence(String text) {
        KeySequence ks = new KeySequence();
        for (char c : text.toCharArray()) {
            addKeySequence(ks, c);
        }
        return ks;
    }

    public static void addKeySequence(KeySequence ks, char character) {
        //@ --> F5 , * --> ENTER, ! --> F1, $ --> F4  , ?--> FLECHA ARRIBA , ¿ FLECHA ABAJO
        switch (character) {
            case '@' :                
                ks.press(KeyEvent.VK_F5, character);
                break;
            case '*' :                
                ks.press(KeyEvent.VK_ENTER, character);
                break;
            case '!' :                
                ks.press(KeyEvent.VK_F1, character);
                break;
            case '$' :                
                ks.press(KeyEvent.VK_F4, character);
                break;
            case '?' :                
                ks.press(KeyEvent.VK_UP, character);
                break;
            case '¿' :                
                ks.press(KeyEvent.VK_DOWN, character);
                break;
            case 'a':
                ks.press(KeyEvent.VK_A, character);
                break;
            case 'b':
                ks.press(KeyEvent.VK_B, character);
                break;
            case 'c':
                ks.type(KeyEvent.VK_C, character);
                break;
            case 'd':
                ks.type(KeyEvent.VK_D, character);
                break;
            case 'e':
                ks.type(KeyEvent.VK_E, character);
                break;
            case 'f':
                ks.type(KeyEvent.VK_F, character);
                break;
            case 'g':
                ks.type(KeyEvent.VK_G, character);
                break;
            case 'h':
                ks.type(KeyEvent.VK_H, character);
                break;
            case 'i':
                ks.type(KeyEvent.VK_I, character);
                break;
            case 'j':
                ks.type(KeyEvent.VK_J, character);
                break;
            case 'k':
                ks.type(KeyEvent.VK_K, character);
                break;
            case 'l':
                ks.type(KeyEvent.VK_L, character);
                break;
            case 'm':
                ks.type(KeyEvent.VK_M, character);
                break;
            case 'n':
                ks.type(KeyEvent.VK_N, character);
                break;
            case 'o':
                ks.type(KeyEvent.VK_O, character);
                break;
            case 'p':
                ks.type(KeyEvent.VK_P, character);
                break;
            case 'q':
                ks.type(KeyEvent.VK_Q, character);
                break;
            case 'r':
                ks.type(KeyEvent.VK_R, character);
                break;
            case 's':
                ks.type(KeyEvent.VK_S, character);
                break;
            case 't':
                ks.type(KeyEvent.VK_T, character);
                break;
            case 'u':
                ks.type(KeyEvent.VK_U, character);
                break;
            case 'v':
                ks.type(KeyEvent.VK_V, character);
                break;
            case 'w':
                ks.type(KeyEvent.VK_W, character);
                break;
            case 'x':
                ks.type(KeyEvent.VK_X, character);
                break;
            case 'y':
                ks.type(KeyEvent.VK_Y, character);
                break;
            case 'z':
                ks.type(KeyEvent.VK_Z, character);
                break;
            case 'A':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_A, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'B':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_B, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'C':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_C, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'D':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_D, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'E':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_E, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'F':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_F, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'G':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_G, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'H':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_H, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'I':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_I, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'J':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_J, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'K':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_K, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'L':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_L, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'M':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_M, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'N':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_N, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'O':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_O, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'P':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_P, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'Q':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_Q, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'R':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_R, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'S':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_S, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'T':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_T, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'U':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_U, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'V':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_V, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'W':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_W, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'X':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_X, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'Y':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_Y, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case 'Z':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_Z, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case '`':
                ks.type(KeyEvent.VK_BACK_QUOTE, character);
                break;
            case '0':
                ks.type(KeyEvent.VK_0, character);
                break;
            case '1':
                ks.type(KeyEvent.VK_1, character);
                break;
            case '2':
                ks.type(KeyEvent.VK_2, character);
                break;
            case '3':
                ks.type(KeyEvent.VK_3, character);
                break;
            case '4':
                ks.type(KeyEvent.VK_4, character);
                break;
            case '5':
                ks.type(KeyEvent.VK_5, character);
                break;
            case '6':
                ks.type(KeyEvent.VK_6, character);
                break;
            case '7':
                ks.type(KeyEvent.VK_7, character);
                break;
            case '8':
                ks.type(KeyEvent.VK_8, character);
                break;
            case '9':
                ks.type(KeyEvent.VK_9, character);
                break;
            case '-':
                ks.type(KeyEvent.VK_MINUS, character);
                break;
            case '=':
                ks.type(KeyEvent.VK_EQUALS, character);
                break;
            case '~':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_BACK_QUOTE, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            /*case '!':
                ks.type(KeyEvent.VK_EXCLAMATION_MARK, character);
                break;*/
            /*case '@':
                ks.type(KeyEvent.VK_AT, character);
                break;*/
            case '#':
                ks.type(KeyEvent.VK_NUMBER_SIGN, character);
                break;
            /*case '$':
                ks.type(KeyEvent.VK_DOLLAR, character);
                break;*/
            case '%':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_5, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case '^':
                ks.type(KeyEvent.VK_CIRCUMFLEX, character);
                break;
            case '&':
                ks.type(KeyEvent.VK_AMPERSAND, character);
                break;
            /*case '*':
                ks.type(KeyEvent.VK_ASTERISK, character);
                break;*/
            case '(':
                ks.type(KeyEvent.VK_LEFT_PARENTHESIS, character);
                break;
            case ')':
                ks.type(KeyEvent.VK_RIGHT_PARENTHESIS, character);
                break;
            case '_':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_MINUS, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case '+':
                ks.type(KeyEvent.VK_PLUS, character);
                break;
            case '\t':
                ks.type(KeyEvent.VK_TAB, character);
                break;
            case '\n':
                ks.type(KeyEvent.VK_ENTER, character);
                break;
            case '[':
                ks.type(KeyEvent.VK_OPEN_BRACKET, character);
                break;
            case ']':
                ks.type(KeyEvent.VK_CLOSE_BRACKET, character);
                break;
            case '\\':
                ks.type(KeyEvent.VK_BACK_SLASH, character);
                break;
            case '{':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_OPEN_BRACKET, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case '}':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_CLOSE_BRACKET, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case '|':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_BACK_SLASH, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;
            case ';':
                ks.type(KeyEvent.VK_SEMICOLON, character);
                break;
            case ':':
                ks.type(KeyEvent.VK_COLON, character);
                break;
            case '\'':
                ks.type(KeyEvent.VK_QUOTE, character);
                break;
            case '"':
                ks.type(KeyEvent.VK_QUOTEDBL, character);
                break;
            case ',':
                ks.type(KeyEvent.VK_COMMA, character);
                break;
            case '<':
                ks.type(KeyEvent.VK_LESS, character);
                break;
            case '.':
                ks.type(KeyEvent.VK_PERIOD, character);
                break;
            case '>':
                ks.type(KeyEvent.VK_GREATER, character);
                break;
            case '/':
                ks.type(KeyEvent.VK_SLASH, character);
                break;
            /*case '?':
                ks.press(KeyEvent.VK_SHIFT, '\0');
                ks.type(KeyEvent.VK_SLASH, character);
                ks.release(KeyEvent.VK_SHIFT, '\0');
                break;*/
            case ' ':
                ks.type(KeyEvent.VK_SPACE, character);
                break;
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
    }

    public static class KeySequence implements Iterable<Key> {
        private List<Key> keys;
        public KeySequence() {
            keys = new ArrayList<>(25);
        }

        public void type(int keyCode, char keyChar) {
            keys.add(new Key(Key.StrokeType.Type, keyCode, keyChar));
        }

        public void press(int keyCode, char keyChar) {
            keys.add(new Key(Key.StrokeType.Press, keyCode, keyChar));
        }

        public void release(int keyCode, char keyChar) {
            keys.add(new Key(Key.StrokeType.Release, keyCode, keyChar));
        }

        public Iterator<Key> iterator() {
            return keys.iterator();
        }
    }
}