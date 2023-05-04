/*
 * (c) 2002-2023 JADAPTIVE Limited. All Rights Reserved.
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
package com.sshtools.common.files.vfs.tests;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.sshtools.common.files.AbstractFile;
import com.sshtools.common.files.vfs.VFSFileFactory;
import com.sshtools.common.files.vfs.VirtualFileFactory;
import com.sshtools.common.files.vfs.VirtualMountTemplate;
import com.sshtools.common.permissions.PermissionDeniedException;

public class VirtualFileWithVFSFileFactoryTests extends VFSFileTests {

	VirtualFileFactory factory; 
	
	protected File getBaseFolder() throws IOException {
		File baseFolder = super.getBaseFolder();
		if(Objects.isNull(factory)) {
			try {
				factory = new VirtualFileFactory(new VirtualMountTemplate("/", baseFolder.getAbsolutePath(), 
						new VFSFileFactory(baseFolder.toURI().toASCIIString()), false));
			} catch (PermissionDeniedException e) {
				throw new IOException(e.getMessage(), e);
			}
		}
		return baseFolder;
 	}
	
	@Override
	protected AbstractFile getFile(String path) throws PermissionDeniedException, IOException {
		return factory.getFile(path);
	}

	@Override
	protected String getBasePath() throws IOException {
		return "/";
	}

	@Override
	protected String getCanonicalPath() throws IOException {
		return "/";
	}
}