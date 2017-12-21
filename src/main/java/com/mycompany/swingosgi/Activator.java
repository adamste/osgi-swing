package com.mycompany.swingosgi;

import com.mycompany.secondbundle.TimeService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator, BundleListener {

    MainFrame frame;

    public void start(BundleContext bc) throws Exception {
        frame = new MainFrame(bc);

        ServiceReference timeRef = bc.getServiceReference(TimeService.class.getName());
        if (timeRef != null) {
            TimeService timeService = (TimeService) bc.getService(timeRef);
            System.out.println("Wiadomość z serwisu: " + timeService.getCurrentTime());
        }
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
