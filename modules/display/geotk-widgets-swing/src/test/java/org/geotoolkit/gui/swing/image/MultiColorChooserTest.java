/*
 *    Geotoolkit.org - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2003-2009, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2009, Geomatys
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
package org.geotoolkit.gui.swing.image;

import org.geotoolkit.gui.test.SwingBase;


/**
 * Tests the {@link MultiColorChooserTest}.
 *
 * @author Martin Desruisseaux (Geomatys)
 * @version 3.01
 *
 * @since 3.01
 */
public class MultiColorChooserTest extends SwingBase<MultiColorChooser> {
    /**
     * Constructs the test case.
     */
    public MultiColorChooserTest() {
        super(MultiColorChooser.class);
    }

    /**
     * Creates the widget.
     */
    @Override
    protected MultiColorChooser create() {
        return new MultiColorChooser();
    }
}
