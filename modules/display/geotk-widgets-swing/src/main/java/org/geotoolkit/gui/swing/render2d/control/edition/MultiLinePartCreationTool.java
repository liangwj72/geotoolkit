/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2011, Geomatys
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

package org.geotoolkit.gui.swing.render2d.control.edition;

import com.vividsolutions.jts.geom.MultiLineString;
import org.geotoolkit.feature.FeatureExt;
import org.geotoolkit.gui.swing.render2d.JMap2D;
import org.geotoolkit.gui.swing.resource.IconBundle;
import org.geotoolkit.gui.swing.resource.MessageBundle;
import org.geotoolkit.map.FeatureMapLayer;
import org.apache.sis.util.iso.SimpleInternationalString;
import org.opengis.feature.FeatureType;
import org.opengis.feature.AttributeType;
import org.opengis.feature.PropertyNotFoundException;

/**
 * Edition tool to create multi line parts.
 *
 * @author Johann Sorel (Geomatys)
 * @module
 */
public class MultiLinePartCreationTool extends AbstractEditionTool {

    public MultiLinePartCreationTool() {
        super(1050,"multilinePartCreation", MessageBundle.formatInternational(MessageBundle.Keys.createPart),
             new SimpleInternationalString("Tool to create part of a MultiLine."),
             IconBundle.getIcon("16_add_subpolygon"), FeatureMapLayer.class);
    }

    @Override
    public boolean canHandle(final Object candidate) {
        if(!super.canHandle(candidate)){
            return false;
        }

        //check the geometry type is type Point
        final FeatureMapLayer layer = (FeatureMapLayer) candidate;
        final FeatureType ft = layer.getCollection().getType();
        try {
            final Class geomClass = FeatureExt.castOrUnwrap(FeatureExt.getDefaultGeometry(ft))
                    .map(AttributeType::getValueClass)
                    .orElse(null);

            if (geomClass == null) {
                return false;
            }
            return MultiLineString.class.isAssignableFrom(geomClass);
        } catch (PropertyNotFoundException | IllegalStateException e) {
            return false;
        }
    }

    @Override
    public EditionDelegate createDelegate(final JMap2D map, final Object candidate) {
        return new MultiLinePartCreationDelegate(map, (FeatureMapLayer) candidate);
    }

}
