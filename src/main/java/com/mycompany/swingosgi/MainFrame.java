package com.mycompany.swingosgi;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.mycompany.secondbundle.TabService;

public class MainFrame extends JFrame implements ActionListener {

    private final JPanel firstPanel = new JPanel();
    private final JLabel firstLabel = new JLabel("First");

    private JPanel secondPanel;
    private final BundleContext bc;
    private final JButton btn1;
    private JTextArea textArea;
    private JTabbedPane tabbedPane = new JTabbedPane();

    public MainFrame(BundleContext bc) {
        super("OSGi Example App");
        this.bc = bc;

        firstPanel.setLayout(new BorderLayout());

        firstPanel.add(firstLabel, BorderLayout.CENTER);
        btn1 = new JButton("OK");
        btn1.addActionListener(this);

        firstPanel.add(btn1, BorderLayout.SOUTH);

        firstPanel.add(createPanelWithTextArea());

        tabbedPane.add("First panel", firstPanel);

        showSecondTab();
        add(tabbedPane);

        setVisible(true);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public final JPanel createPanelWithTextArea() {
        JPanel panel = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    public void showSecondTab() {
        ServiceReference timeRef = bc.getServiceReference(TabService.class.getName());
        if (timeRef != null) {
            TabService tabService = (TabService) bc.getService(timeRef);
            secondPanel = tabService.getPanel();
            tabbedPane.add(tabService.tabName, secondPanel);
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

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == btn1) {
            textArea.append("You clicked 'OK' button!\n");
        }
    }
}
