import Memory.Memory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemoryGUI {
    private JPanel panel1;
    private JTextArea textArea;
    private JButton updateButton;
    private JScrollPane textAreaScroll;


    public MemoryGUI() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO add printouts for all the registers 
                for(int i = 0; i < 2048; i++)
                {
                    textArea.append("Address:" + i + "   ");
                    textArea.append(Integer.toBinaryString(Main.mem.getFromMemory(i)) + "\n");
                }

            }
        });
    }

    public static void CreateMemoryGUI() {
        JFrame frame = new JFrame("MemoryGUI");
        frame.setContentPane(new MemoryGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}


