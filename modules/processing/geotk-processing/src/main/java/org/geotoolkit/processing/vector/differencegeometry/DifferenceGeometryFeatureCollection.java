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
package org.geotoolkit.processing.vector.differencegeometry;

import com.vividsolutions.jts.geom.Geometry;

import org.geotoolkit.data.FeatureCollection;
import org.geotoolkit.data.memory.WrapFeatureCollection;
import org.geotoolkit.processing.vector.VectorProcessUtils;

import org.opengis.feature.Feature;
import org.opengis.feature.FeatureType;

/**
 * FeatureCollection for DifferenceGeometry process
 * @author Quentin Boileau
 * @module
 */
public class DifferenceGeometryFeatureCollection extends WrapFeatureCollection {

    private final FeatureType newFeatureType;
    private final Geometry diffGeometry;

    /**
     * Connect to the original FeatureConnection
     * @param originalFC FeatureCollection
     */
    public DifferenceGeometryFeatureCollection(final FeatureCollection originalFC, final Geometry diffGeometry) {
        super(originalFC);
        this.diffGeometry = diffGeometry;
        this.newFeatureType = VectorProcessUtils.changeGeometryFeatureType(super.getType(), Geometry.class);
    }

    /**
     * Return the new FeatureType
     * @return FeatureType
     */
    @Override
    public FeatureType getType() {
        return newFeatureType;
    }

    /**
     *  {@inheritDoc }
     */
    @Override
    protected Feature modify(final Feature original) {
        return DifferenceGeometryProcess.clipFeature(original, newFeatureType, diffGeometry);
    }
}
