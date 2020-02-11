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

package org.orbitrondev.jass.lib.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * The service locator, which manages globally available objects.
 *
 * @author https://www.geeksforgeeks.org/service-locator-pattern/
 * @version %I%, %G%
 * @since 0.0.1
 */
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
