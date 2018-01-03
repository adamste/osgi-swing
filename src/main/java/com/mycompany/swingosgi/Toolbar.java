package com.mycompany.swingosgi;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import org.osgi.framework.BundleContext;

public class Toolbar extends JPanel implements ActionListener {

    private final JButton helloButton;
    private final JButton goodbyeButton;
    private StringListener textListener;
    private BundleContext bc;

    public Toolbar(BundleContext bc) {
        this.bc = bc;
        helloButton = new JButton("Hello");
        goodbyeButton = new JButton("Goodbye");

        helloButton.addActionListener(this);
        goodbyeButton.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(helloButton);
        add(goodbyeButton);
    }

    public void setStringListener(StringListener listener) {
        this.textListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        final JButton clicked = (JButton) event.getSource();

        if (clicked == helloButton) {
            // textPanel.appendText("Hello");
            if (textListener != null) {
                textListener.textEmitted("Hello");
            }
            
        } else if (clicked == goodbyeButton) {
            // textPanel.appendText("Bye");
            if (textListener != null) {
                textListener.textEmitted("Goodbye");
            }
        }
    }
}
