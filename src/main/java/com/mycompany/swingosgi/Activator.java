package com.mycompany.swingosgi;

import com.mycompany.secondbundle.TimeService;
import javax.swing.SwingUtilities;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator, BundleListener {

    MainFrame frame;
    BundleContext bc;

    public void start(final BundleContext bc) throws Exception {
        this.bc=bc;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame = new MainFrame(bc);
            }
        });
        bc.addBundleListener(this);
    }

    public void stop(BundleContext context) throws Exception {
        destroyInterface();
        context.removeBundleListener(this);
    }

    private void destroyInterface() {
        frame.setVisible(false);
        frame.dispose();
    }

    public void bundleChanged(BundleEvent event) {
        String symbolicName = event.getBundle().getSymbolicName();
        String type = typeAsString(event);

        if (symbolicName.equals("com.mycompany.SecondBundle") && event.getType() == BundleEvent.STARTED) {
            frame.showSecondTab();
        } else if (symbolicName.equals("com.mycompany.SecondBundle")) {
            frame.destroySecondTab();
        }
        System.out.println("BundleChanged: " + symbolicName + ", event.type: " + type);
    }

    private static String typeAsString(BundleEvent event) {
        if (event == null) {
            return "null";
        }
        int type = event.getType();
        switch (type) {
            case BundleEvent.INSTALLED:
                return "INSTALLED";
            case BundleEvent.LAZY_ACTIVATION:
                return "LAZY_ACTIVATION";
            case BundleEvent.RESOLVED:
                return "RESOLVED";
            case BundleEvent.STARTED:
                return "STARTED";
            case BundleEvent.STARTING:
                return "Starting";
            case BundleEvent.STOPPED:
                return "STOPPED";
            case BundleEvent.UNINSTALLED:
                return "UNINSTALLED";
            case BundleEvent.UNRESOLVED:
                return "UNRESOLVED";
            case BundleEvent.UPDATED:
                return "UPDATED";
            default:
                return "unknown event type: " + type;
        }
    }
}
