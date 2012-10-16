/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2012, Geomatys
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotoolkit.gui.swing.go2.control.edition;

import org.geotoolkit.gui.swing.go2.JMap2D;
import org.geotoolkit.gui.swing.resource.MessageBundle;
import org.geotoolkit.map.CoverageMapLayer;

/**
 * Coverage editor tool.
 * 
 * @author Johann Sorel (Geomatys)
 */
public class CoverageEditionTool extends AbstractEditionTool{

    public CoverageEditionTool() {
        super(2000, "coverageEditor", 
                MessageBundle.getI18NString("editor"), 
                MessageBundle.getI18NString("editor"), 
                null, 
                CoverageMapLayer.class);
    }
    
    @Override
    public EditionDelegate createDelegate(JMap2D map, Object candidate) {
        return new CoverageEditionDelegate(map,(CoverageMapLayer)candidate);
    }
    
}
