package com.mycompany.swingosgi;

import com.mycompany.secondbundle.TimeService;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

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
            
            ServiceReference timeRef = bc.getServiceReference(TimeService.class.getName());
            if (timeRef != null) {
                System.out.println("SERWIS DOSTEPNY");
                TimeService timeService = (TimeService) bc.getService(timeRef);
                System.out.println("Wiadomość z serwisu: " + timeService.getCurrentTime());
            }else{
                System.out.println("SERWIS NIEDOSTEPNY");
            }
            
        } else if (clicked == goodbyeButton) {
            // textPanel.appendText("Bye");
            if (textListener != null) {
                textListener.textEmitted("Goodbye");
            }
        }
    }
}
