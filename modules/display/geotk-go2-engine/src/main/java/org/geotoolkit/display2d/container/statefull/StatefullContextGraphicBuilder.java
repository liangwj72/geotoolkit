/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2008 - 2009, Geomatys
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
package org.geotoolkit.display2d.container.statefull;

import java.util.Collection;
import java.util.Collections;
import org.geotoolkit.display2d.canvas.J2DCanvas;
import org.geotoolkit.display2d.container.ContextGraphicBuilder;
import org.geotoolkit.display2d.primitive.GraphicJ2D;
import org.geotoolkit.map.MapContext;

/**
 *
 * @author Johann Sorel (Puzzle-GIS)
 * @module pending
 */
public class StatefullContextGraphicBuilder implements ContextGraphicBuilder{

    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<? extends GraphicJ2D> createGraphics(final J2DCanvas canvas, final MapContext context) {
        return Collections.singleton(new StatefullMapItemJ2D(canvas, null, context));
    }

}
