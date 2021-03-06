/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotoolkit.filter.identity;

import java.io.Serializable;
import org.geotoolkit.feature.FeatureExt;
import org.opengis.filter.identity.GmlObjectId;

import static org.apache.sis.util.ArgumentChecks.*;
import org.opengis.feature.Feature;

/**
 * Immutable gml object id.
 *
 * @author Johann Sorel (Geomatys)
 * @module
 */
public class DefaultGmlObjectId implements GmlObjectId,Serializable {

    private final String id;

    public DefaultGmlObjectId(final String id) {
        ensureNonNull("gml id", id);
        this.id = id;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean matches(final Object object) {
        if (object instanceof Feature) {
            return id.equals( FeatureExt.getId((Feature)object).getID());
        }

        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return id;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultGmlObjectId other = (DefaultGmlObjectId) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
