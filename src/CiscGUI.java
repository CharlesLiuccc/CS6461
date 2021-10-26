

import Memory.Cache;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Locale;
import java.io.File;
import java.util.Objects;
import java.util.Scanner;


/***
 * Here is the layout for the Graphical User Interface.
 * Buttons next to registers allow for user to update registers manually
 * GUI desgin implemented with Intellij UI Designer using Swing package
 *
 *
 * @author Brian
 */

public class CiscGUI {
    //private static Label operationTextField;
    private JPanel mainPanel;
    private JPanel valuePanel;
    private JPanel instructionPanel;
    private JLabel gpr0Label;
    private JTextField gpr0TextField;
    private JLabel pcLabel;
    private JTextField pcTextField;
    private JLabel gpr1Label;
    private JButton gpr0LoadButton;
    private JButton pcLoadButton;
    private JTextField gpr1TextField;
    private JButton gpr1LoadButton;
    private JLabel marLabel;
    private JTextField marTextField;
    private JLabel gpr2Label;
    private JLabel gpr3Label;
    private JLabel ixr1Label;
    private JLabel ixr2Label;
    private JLabel ixr3Label;
    private JTextField gpr2TextField;
    private JTextField gpr3TextField;
    private JTextField ixr1TextField;
    private JTextField ixr2TextField;
    private JTextField ixr3TextField;
    private JButton gpr2LoadButton;
    private JButton gpr3LoadButton;
    private JButton ixr1LoadButton;
    private JButton ixr2LoadButton;
    private JButton ixr3LoadButton;
    private JLabel mbrLabel;
    private JLabel irLabel;
    private JLabel mfrLabel;
    private JTextField mbrTextField;
    private JTextField irTextField;
    private JTextField mfrTextField;
    private JPanel opcodePanel;
    private JTextField operationTextField;
    private JButton zeroButton;
    private JButton oneButton;
    private JButton twoButton;
    private JButton threeButton;
    private JButton fourButton;
    private JButton fiveButton;
    private JLabel opcodeLabel;
    private JLabel gprLabel;
    private JLabel ixrLabel;
    private JLabel iLabel;
    private JLabel addressLabel;
    private JButton sixButton;
    private JButton sevenButton;
    private JButton eightButton;
    private JButton nineButton;
    private JButton tenButton;
    private JButton elevenButton;
    private JButton twelveButton;
    private JButton thirteenButton;
    private JButton fourteenButton;
    private JButton fifteenButton;
    private JTextField gprTextField;
    private JTextField ixrTextField;
    private JTextField iTextField;
    private JTextField addressTextField;
    private JButton iplButton;
    private JButton loadButton;
    private JButton storeButton;
    private JPanel gprPanel;
    private JPanel ixrPanel;
    private JPanel iPanel;
    private JPanel addressPanel;
    private JButton runButton;
    private JButton ssButton;
    private JButton memoryGUIButton;
    private JPanel BottomPanel;
    private JTextArea consoleTextArea;
    private JPanel consolePanel;
    private JScrollPane consoleScrollPane;
    private JTextField inputTextField;
    private JPanel inputPanel;
    private JLabel inputLabel;
    private JButton instructionEnterButton;


    private int[] guiArray = new int[16];


    public CiscGUI() {

        zeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[0] == 0)
                    guiArray[0] = 1;
                else guiArray[0] = 0;
                PrintOperation();
            }
        });
        iplButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleTextArea.setText("");

                Main.mem.resetMemory();
                Main.setHALT(false);
                Main.gpr0.setValue(0); //Resetting all registers
                Main.gpr1.setValue(0);
                Main.gpr2.setValue(0);
                Main.gpr3.setValue(0);
                Main.x1.setValue(0);
                Main.x2.setValue(0);
                Main.x3.setValue(0);
                Main.mar.setValue(0);
                Main.mbr.setValue(0);
                Main.ir.setValue(0);
                Main.mfr.setValue(0);

                JFileChooser file_chooser = new JFileChooser();

                if (file_chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    File file = file_chooser.getSelectedFile();
                    try {
                        Scanner read = new Scanner(file);
                        Main.pc.setValue(6); //Assume first instruction is at 6 every time
                        while(read.hasNextLine()) {
                            String str = read.nextLine().trim();
                            int address = Integer.parseInt(str.substring(0,4),16);
                            int data = Integer.parseInt(str.substring(5,9),16);

                            //TODO use Memory.SetMEM(address,data)
                            Main.mem.setToCache(address,data); //first set to cache memory
                            printCacheFile(); //prints cache to a file
                            UpdateGUI();

                            consoleTextArea.setText("File loaded, please click ss or run button to run the program");
                        }

                    } catch (FileNotFoundException ex) {
                        consoleTextArea.setText("Invalid File: Please Click IPL again to choose a new file");
                    }
                }

            }
        });
        oneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[1] == 0)
                    guiArray[1] = 1;
                else guiArray[1] = 0;
                PrintOperation();
            }
        });
        twoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[2] == 0)
                    guiArray[2] = 1;
                else guiArray[2] = 0;
                PrintOperation();
            }
        });
        threeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[3] == 0)
                    guiArray[3] = 1;
                else guiArray[3] = 0;
                PrintOperation();
            }
        });
        fourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[4] == 0)
                    guiArray[4] = 1;
                else guiArray[4] = 0;
                PrintOperation();
            }
        });
        fiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[5] == 0)
                    guiArray[5] = 1;
                else guiArray[5] = 0;
                PrintOperation();
            }
        });
        sixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[6] == 0)
                    guiArray[6] = 1;
                else guiArray[6] = 0;
                PrintGPR();
            }
        });
        sevenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[7] == 0)
                    guiArray[7] = 1;
                else guiArray[7] = 0;
                PrintGPR();
            }
        });
        eightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[8] == 0)
                    guiArray[8] = 1;
                else guiArray[8] = 0;
                PrintIXR();
            }
        });
        nineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[9] == 0)
                    guiArray[9] = 1;
                else guiArray[9] = 0;
                PrintIXR();
            }
        });
        tenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[10] == 0)
                    guiArray[10] = 1;
                else guiArray[10] = 0;
                PrintI();
            }
        });
        elevenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[11] == 0)
                    guiArray[11] = 1;
                else guiArray[11] = 0;
                PrintAddress();
            }
        });
        twelveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[12] == 0)
                    guiArray[12] = 1;
                else guiArray[12] = 0;
                PrintAddress();
            }
        });
        thirteenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[13] == 0)
                    guiArray[13] = 1;
                else guiArray[13] = 0;
                PrintAddress();
            }
        });
        fourteenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[14] == 0)
                    guiArray[14] = 1;
                else guiArray[14] = 0;
                PrintAddress();
            }
        });
        fifteenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guiArray[15] == 0)
                    guiArray[15] = 1;
                else guiArray[15] = 0;
                PrintAddress();
            }
        });
        gpr0LoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = ConvertArrayToInt();
                //TODO need to add error checking here and other areas like this
                Main.gpr0.setValue(value);
                gpr0TextField.setText(Integer.toBinaryString(value | 0x10000).substring(1));
            }
        });
        gpr1LoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = ConvertArrayToInt();
                Main.gpr1.setValue(value);
                gpr1TextField.setText(Integer.toBinaryString(value | 0x10000).substring(1));
            }
        });
        gpr2LoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = ConvertArrayToInt();
                Main.gpr2.setValue(value);
                gpr2TextField.setText(Integer.toBinaryString(value | 0x10000).substring(1));
            }
        });
        gpr3LoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = ConvertArrayToInt();
                Main.gpr3.setValue(value);
                gpr3TextField.setText(Integer.toBinaryString(value | 0x10000).substring(1));
            }
        });
        ixr1LoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = ConvertArrayToInt();
                Main.x1.setValue(value);
                ixr1TextField.setText(Integer.toBinaryString(value | 0x10000).substring(1));
            }
        });
        ixr2LoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = ConvertArrayToInt();
                Main.x2.setValue(value);
                ixr2TextField.setText(Integer.toBinaryString(value | 0x10000).substring(1));
            }
        });
        ixr3LoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = ConvertArrayToInt();
                Main.x3.setValue(value);
                ixr3TextField.setText(Integer.toBinaryString(value | 0x10000).substring(1));
            }
        });
        pcLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = ConvertArrayToInt();
                Main.pc.setValue(value);
                pcTextField.setText(Integer.toBinaryString(value | 0x1000).substring(1));
            }
        });
        storeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO Send this to computer to execute
                int start = guiArray[0];
                for(int i = 1; i < 6; i++){
                    start = start << 1;
                    start = (start | guiArray[i]);
                }

                if(start == 2 || start == 42 ) //All valid load operations
                {
                    Main.loadAndStore(ConvertArrayToInt());
                }

                else GUIError();
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO send this to computer to execute
                //Will calculate Opcode
                int start = guiArray[0];
                for(int i = 1; i < 6; i++){
                    start = start << 1;
                    start = (start | guiArray[i]);
                }

                if(start == 1 || start == 3 || start == 41 ) //All valid load operations
                {
                    Main.loadAndStore(ConvertArrayToInt());
                }

                else GUIError();
            }
        });
        ssButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.singleStep();
                UpdateGUI();

            }
        });
        memoryGUIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleTextArea.setText("");
                consoleTextArea.append("GPR0: " + Integer.toBinaryString(Main.gpr0.getValue()) + "\n");
                consoleTextArea.append("GPR1: " + Integer.toBinaryString(Main.gpr1.getValue()) + "\n");
                consoleTextArea.append("GPR2: " + Integer.toBinaryString(Main.gpr2.getValue()) + "\n");
                consoleTextArea.append("GPR3: " + Integer.toBinaryString(Main.gpr3.getValue()) + "\n");
                consoleTextArea.append("IXR1: " + Integer.toBinaryString(Main.x1.getValue()) + "\n");
                consoleTextArea.append("IXR2: " + Integer.toBinaryString(Main.x2.getValue()) + "\n");
                consoleTextArea.append("IXR3: " + Integer.toBinaryString(Main.x3.getValue()) + "\n");
                consoleTextArea.append("PC: " + Integer.toBinaryString(Main.pc.getValue()) + "\n");
                consoleTextArea.append("MAR: " + Integer.toBinaryString(Main.mar.getValue()) + "\n");
                consoleTextArea.append("MBR: " + Integer.toBinaryString(Main.mbr.getValue()) + "\n");
                consoleTextArea.append("IR: " + Integer.toBinaryString(Main.ir.getValue()) + "\n");
                consoleTextArea.append("MFR: " + Integer.toBinaryString(Main.mfr.getValue()) + "\n");
                for(int i = 0; i < 2048; i++)
                {
                    consoleTextArea.append("Address:" + i + "   ");
                    consoleTextArea.append(Integer.toBinaryString(Main.mem.getFromMemory(i)) + "\n");
                }
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while(!Main.HALT){
                    Main.singleStep();
                }
                UpdateGUI();

            }
        });
    }

    public void CreateandShowGUI() {
        JFrame frame = new JFrame("CiscGUI");
        frame.setContentPane(new CiscGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void UpdateGUI(){
        //Integer.toBinaryString(value | 0x10000).substring(1)
        gpr0TextField.setText(Integer.toBinaryString(Main.gpr0.getValue() | 0x10000).substring(1));
        gpr1TextField.setText(Integer.toBinaryString(Main.gpr1.getValue()| 0x10000).substring(1));
        gpr2TextField.setText(Integer.toBinaryString(Main.gpr2.getValue()| 0x10000).substring(1));
        gpr3TextField.setText(Integer.toBinaryString(Main.gpr3.getValue()| 0x10000).substring(1));
        ixr1TextField.setText(Integer.toBinaryString(Main.x1.getValue()| 0x10000).substring(1));
        ixr2TextField.setText(Integer.toBinaryString(Main.x2.getValue()| 0x10000).substring(1));
        ixr3TextField.setText(Integer.toBinaryString(Main.x3.getValue()| 0x10000).substring(1));
        pcTextField.setText(Integer.toBinaryString(Main.pc.getValue()| 0x1000).substring(1));
        marTextField.setText(Integer.toBinaryString(Main.mar.getValue()| 0x1000).substring(1));
        mbrTextField.setText(Integer.toBinaryString(Main.mbr.getValue()| 0x10000).substring(1));
        irTextField.setText(Integer.toBinaryString(Main.ir.getValue()| 0x10000).substring(1));
        mfrTextField.setText(Integer.toBinaryString(Main.mfr.getValue()| 0x10).substring(1));
        if(Main.HALT){
            consoleTextArea.setText("The program is finished!");
        }
    }

    private void PrintOperation(){
            String toprint = "";
            for(int i = 0; i < 6; i++){
                toprint = toprint + Integer.toBinaryString(guiArray[i]) + "   ";
            }
            operationTextField.setText(toprint);

    }

    private void PrintGPR(){
        String toprint = "";
        for(int i = 6; i < 8; i++){
            toprint = toprint + Integer.toBinaryString(guiArray[i]) + "   ";
        }
        gprTextField.setText(toprint);
    }

    private void PrintIXR(){
        String toprint = "";
        for(int i = 8; i < 10; i++){
            toprint = toprint + Integer.toBinaryString(guiArray[i]) + "   ";
        }
        ixrTextField.setText(toprint);
    }

    private void PrintI(){
        String toprint = Integer.toBinaryString(guiArray[10]);
        iTextField.setText(toprint);
    }

    private void PrintAddress() {
        String toprint = "";
        for(int i = 11; i < 16; i++){
            toprint = toprint + Integer.toBinaryString(guiArray[i]) + "   ";
        }
        addressTextField.setText(toprint);
    }

    private int ConvertArrayToInt() {
        int start = guiArray[0];
        for(int i = 1; i < 16; i++){
            start = start << 1;
            start = (start | guiArray[i]);
        }

        return start;

    }

    private void GUIError(){
        for(int i = 0; i < 16; i++)
            guiArray[i] = 0;
        PrintOperation();
        PrintGPR();
        PrintIXR();
        PrintI();
        PrintAddress();
        consoleTextArea.setText("Error - Invalid Instruction - Click IPL to restart\n");
        UpdateGUI();



    }

    //Function prompts user to enter a character and will return the ascii value of the char entered
    public int In_Instruction(){
        String inputString = JOptionPane.showInputDialog("Enter in a number: ");
        inputString = inputString.replaceAll("\\s+", "");
        int input = Integer.parseInt(inputString); 
        return input;
    }

    //Prints a file with the cache information
    private void printCacheFile() {


        String cacheFile = "cacheFile.txt";
        PrintWriter writer = null;

        try {


           writer = new PrintWriter(cacheFile);
        }catch(FileNotFoundException e) {
            e.printStackTrace();

        }
                writer.printf("%-10s%2s%n","Address" ,"Data"); //Formats data labels
            for (Cache.CacheLine line : Main.mem.getCache().getCacheLines()) {
                writer.printf("%-10d%-2d%n",line.getTag(),line.getData() );//Formats data output

            }

            writer.close();

        }


}
