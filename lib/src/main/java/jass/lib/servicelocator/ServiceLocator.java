/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari & Victor Hargrave & Thomas Weber & Sasa
 * Trajkova
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
 * @author https://www.geeksforgeeks.org/service-locator-pattern/ & Manuele
 * Vaccari
 * @version %I%, %G%
 * @since 1.0.0
 */
public final class ServiceLocator {
    /**
     * A list of all the services available.
     */
    private static final List<Service> services = new ArrayList<>();

    /**
     * Utility classes, which are collections of static members, are not meant
     * to be instantiated.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    private ServiceLocator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param serviceName The name of the service requested.
     * @param <S>         The class of of the service.
     *
     * @return Return the service object or null if not found.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static <S extends Service> S get(final Class<S> serviceName) {
        for (Service service : services) {
            if (service.getClass().getName().equalsIgnoreCase(serviceName.getName())) {
                @SuppressWarnings("unchecked")
                S serviceCasted = (S) service;
                return serviceCasted;
            }
        }
        return null;
    }

    /**
     * @param newService The service to be added to the list.
     * @param <S>        The class of of the service.
     *
     * @author Manuele Vaccari
     * @since 1.0.0
     */
    public static <S extends Service> void add(final S newService) {
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
     *
     * @author Manuele Vaccari
     * @since 1.0.0
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
