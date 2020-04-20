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

/**
 * The interface which has to implemented to be part of the ServiceLocator.
 *
 * @author https://www.geeksforgeeks.org/service-locator-pattern/
 * @version %I%, %G%
 * @since 0.0.1
 */
public interface Service {
    /**
     * @return Returns the name of the service.
     */
    String getServiceName();
}