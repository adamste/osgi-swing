/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.swingosgi;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import org.osgi.framework.BundleContext;

/**
 *
 * @author astepien1
 */
public class MainFrame extends JFrame {

    private final TextPanel textPanel;
    private final Toolbar toolbar;

    public MainFrame(BundleContext bc) {
        super("Hello World");

        setLayout(new BorderLayout());

        toolbar = new Toolbar(bc);
        textPanel = new TextPanel();

        toolbar.setStringListener(new StringListener() {
            @Override
            public void textEmitted(String text) {
                textPanel.appendText(text);
            }
        });

        add(toolbar, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);

        setVisible(true);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
