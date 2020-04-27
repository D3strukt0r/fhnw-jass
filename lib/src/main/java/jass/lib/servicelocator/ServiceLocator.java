/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package jass.lib.servicelocator;

import java.util.ArrayList;
import java.util.List;

/**
 * The service locator, which manages globally available objects.
 *
 * @author https://www.geeksforgeeks.org/service-locator-pattern/
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class ServiceLocator {
    /**
     * A list of all the services available.
     */
    private static final List<Service> services = new ArrayList<>();

    /**
     * Utility classes, which are collections of static members, are not meant
     * to be instantiated.
     */
    private ServiceLocator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param serviceName The name of the service requested
     *
     * @return Return the service object or null if not found.
     */
    public static Service get(final Class<? extends Service> serviceName) {
        for (Service service : services) {
            if (service.getClass().getName().equalsIgnoreCase(serviceName.getName())) {
                return service;
            }
        }
        return null;
    }

    /**
     * @param newService The service to be added to the list.
     */
    public static void add(final Service newService) {
        boolean exists = false;
        for (Service service : services) {
            if (service.getClass().getName().equalsIgnoreCase(newService.getClass().getName())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            services.add(newService);
        }
    }

    /**
     * @param serviceName The service to be removed from the list.
     */
    public static void remove(final Class<? extends Service> serviceName) {
        Service objectFound = null;
        for (Service service : services) {
            if (service.getClass().getName().equalsIgnoreCase(serviceName.getName())) {
                objectFound = service;
            }
        }
        if (objectFound != null) {
            services.remove(objectFound);
        }
    }
}
