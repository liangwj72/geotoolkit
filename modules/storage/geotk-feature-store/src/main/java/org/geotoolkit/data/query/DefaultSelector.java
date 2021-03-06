/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2010, Geomatys
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

package org.geotoolkit.data.query;

import org.geotoolkit.data.session.Session;
import static org.apache.sis.util.ArgumentChecks.*;
import org.opengis.util.GenericName;

/**
 * Default selector implementation.
 *
 * @author Johann Sorel (Geomatys)
 * @module
 */
public final class DefaultSelector implements Selector{

    private final Session session;
    private final String typeName;
    private final String name;

    public DefaultSelector(final Session session, final String typeName, final String selectorName) {
        ensureNonNull("selector feature type name", typeName);
        ensureNonNull("selector name", selectorName);
        this.session = session;
        this.typeName = typeName;
        this.name = selectorName;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Session getSession() {
        return session;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getFeatureTypeName() {
        return typeName;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getSelectorName() {
        return name;
    }

}
