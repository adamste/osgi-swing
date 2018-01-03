/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.swingosgi;

import com.mycompany.secondbundle.TimeService;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author astepien1
 */
public class MainFrame extends JFrame {

//    private final TextPanel textPanel;
//    private final Toolbar toolbar;
    JPanel firstPanel = new JPanel();
    JLabel firstLabel = new JLabel("First");

    JPanel secondPanel;
    JLabel secondLabel;
    BundleContext bc;

    JTabbedPane tabbedPane = new JTabbedPane();

    public MainFrame(BundleContext bc) {
        super("OSGi Example");
        this.bc = bc;

        firstPanel.setLayout(new BorderLayout());
        
        firstPanel.add(firstLabel, BorderLayout.CENTER);
        JButton btn1=new JButton("OK");
        firstPanel.add(btn1, BorderLayout.SOUTH);
        tabbedPane.add("First panel", firstPanel);
        firstPanel.add(new JLabel("123123123"));
        firstPanel.add(new JLabel("123123123"));
        firstPanel.add(new JLabel("123123123"));
        showSecondTab();
        add(tabbedPane);
        
        

//        setLayout(new BorderLayout());
//        toolbar = new Toolbar(bc);
//        textPanel = new TextPanel();
//
//        toolbar.setStringListener(new StringListener() {
//            @Override
//            public void textEmitted(String text) {
//                textPanel.appendText(text);
//            }
//        });
//
//        add(toolbar, BorderLayout.NORTH);
//        add(textPanel, BorderLayout.CENTER);

        setVisible(true);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showSecondTab() {
        ServiceReference timeRef = bc.getServiceReference(TimeService.class.getName());
        if (timeRef != null) {
            TimeService timeService = (TimeService) bc.getService(timeRef);
            secondPanel=timeService.getPanel();
            tabbedPane.add(timeService.tabName, secondPanel);
        } else {
            System.out.println("Second bundle doesn't respond");
        }
        this.validate();
        this.repaint();
    }

    public void destroySecondTab() {
        tabbedPane.remove(secondPanel);
        this.validate();
        this.repaint();
    }
}
