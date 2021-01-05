/**
 * (c) 2002-2021 JADAPTIVE Limited. All Rights Reserved.
 *
 * This file is part of the Maverick Synergy Java SSH API.
 *
 * Maverick Synergy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Maverick Synergy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Maverick Synergy.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.sshtools.agent;

public class ForwardingNotice {
    String remoteHostname;
    String remoteIPAddress;
    int remotePort;

    /**
     * Creates a new ForwardingNotice object.
     *
     * @param remoteHostname
     * @param remoteIPAddress
     * @param remotePort
     */
    public ForwardingNotice(String remoteHostname, String remoteIPAddress,
        int remotePort) {
        this.remoteHostname = remoteHostname;
        this.remoteIPAddress = remoteIPAddress;
        this.remotePort = remotePort;
    }

    /**
     *
     *
     * @return
     */
    public String getRemoteHostname() {
        return remoteHostname;
    }

    /**
     *
     *
     * @return
     */
    public String getRemoteIPAddress() {
        return remoteIPAddress;
    }

    /**
     *
     *
     * @return
     */
    public int getRemotePort() {
        return remotePort;
    }
}
