package org.orbitrondev.jass.lib.ServiceLocator;

/* https://www.geeksforgeeks.org/service-locator-pattern/ */

import java.util.ArrayList;
import java.util.List;

public class ServiceLocator {
    private static List<Service> services = new ArrayList<>();

    public static Service get(String serviceName) {
        for (Service service : services) {
            if (service.getName().equalsIgnoreCase(serviceName)) {
                return service;
            }
        }
        return null;
    }

    public static void add(Service newService) {
        boolean exists = false;
        for (Service service : services) {
            if (service.getName().equalsIgnoreCase(newService.getName())) {
                exists = true;
            }
        }
        if (!exists) {
            services.add(newService);
        }
    }

    public static void remove(String serviceName) {
        Service objectFound = null;
        for (Service service : services) {
            if (service.getName().equalsIgnoreCase(serviceName)) {
                objectFound = service;
            }
        }
        if (objectFound != null) {
            services.remove(objectFound);
        }
    }
}
