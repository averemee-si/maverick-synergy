/*
 *    _           _             _   _
 *   (_) __ _  __| | __ _ _ __ | |_(_)_   _____
 *   | |/ _` |/ _` |/ _` | '_ \| __| \ \ / / _ \
 *   | | (_| | (_| | (_| | |_) | |_| |\ V /  __/
 *  _/ |\__,_|\__,_|\__,_| .__/ \__|_| \_/ \___|
 * |__/                  |_|
 *
 * This file is part of the Maverick Synergy Hotfixes Java SSH API
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Copyright (C) 2002-2021 JADAPTIVE Limited - All Rights Reserved
 *
 * Use of this software may also be covered by third-party licenses depending on the choices you make about what features to use.
 *
 * Please visit the link below to see additional third-party licenses and copyrights
 *
 * https://www.jadaptive.com/app/manpage/en/article/1565029/What-third-party-dependencies-does-the-Maverick-Synergy-API-have
 */
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
package com.sshtools.common.files.vfs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.sshtools.common.files.AbstractFile;
import com.sshtools.common.files.AbstractFileAdapter;
import com.sshtools.common.permissions.PermissionDeniedException;
import com.sshtools.common.util.FileUtils;

public class VirtualFileObject extends AbstractFileAdapter implements VirtualFile {

	VirtualMount parentMount;
	Map<String,AbstractFile> mounts;
	protected VirtualFileFactory fileFactory;
	
	protected VirtualFileObject(VirtualFileFactory factory, VirtualMount parentMount) {
		this.fileFactory = factory;
		this.parentMount = parentMount;
	}
	
	
	@Override
	public synchronized void refresh() {
		mounts = null;
		super.refresh();
	}

	public VirtualMount getMount() {
		return parentMount;
	}
	
	@Deprecated
	/**
	 * @deprecated Use getMount instead as it's now part of the VirtualFile interface.
	 * @return
	 */
	public VirtualMount getParentMount() {
		return parentMount;
	}

	protected synchronized Map<String,AbstractFile> getVirtualMounts() throws IOException, PermissionDeniedException {
		
		if(Objects.isNull(mounts)) {
			Map<String,AbstractFile> files = new HashMap<String,AbstractFile>();
			
			String currentPath = FileUtils.checkEndsWithSlash(getAbsolutePath());
	
			VirtualMountManager mgr = fileFactory.getMountManager();
			
			for(VirtualMount m : mgr.getMounts()) {
				
				String mountPath = FileUtils.checkEndsWithSlash(m.getMount());
				
				if(mountPath.startsWith(currentPath)) {
					String childPath = m.getMount().substring(currentPath.length());
					
					if(childPath.indexOf('/') > -1) {
						childPath = FileUtils.checkEndsWithSlash(
										FileUtils.checkStartsWithSlash(
												childPath.substring(0,childPath.indexOf('/'))));
					} else {
						childPath = mountPath;
					}
					
					if(mountPath.startsWith(currentPath) && !mountPath.equals(currentPath)) {
						if(mountPath.equals(childPath)) {
							files.put(childPath, new VirtualMountFile(
									FileUtils.checkEndsWithNoSlash(childPath),
									m, true, fileFactory));
						} else {
							files.put(childPath, new VirtualMountFile(
									FileUtils.checkEndsWithNoSlash(childPath),
										parentMount, true, fileFactory));
						}
					}
				}
			}
			
			mounts =  files;
		}
		return mounts;
	}
}
