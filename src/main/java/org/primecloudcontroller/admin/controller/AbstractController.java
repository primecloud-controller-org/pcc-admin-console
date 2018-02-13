/*
 * Copyright 2018 by PrimeCloud Controller/OSS Community.
 * 
 * This file is part of PrimeCloud Controller(TM).
 * 
 * PrimeCloud Controller(TM) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * PrimeCloud Controller(TM) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PrimeCloud Controller(TM). If not, see <http://www.gnu.org/licenses/>.
 */
package org.primecloudcontroller.admin.controller;

public abstract class AbstractController {

    protected Integer toInteger(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return Integer.valueOf(str);
    }

    protected Long toLong(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return Long.valueOf(str);
    }

    protected Boolean toBoolean(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return Boolean.valueOf(str);
    }

}
