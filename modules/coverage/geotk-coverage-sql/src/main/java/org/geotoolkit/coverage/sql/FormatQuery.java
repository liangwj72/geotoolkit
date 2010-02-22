/*
 *    Geotoolkit.org - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2005-2010, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2007-2010, Geomatys
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
package org.geotoolkit.coverage.sql;

import org.geotoolkit.internal.sql.Ordering;
import static org.geotoolkit.coverage.sql.QueryType.*;


/**
 * The query to execute for a {@link FormatTable}.
 *
 * @author Martin Desruisseaux (IRD, Geomatys)
 * @version 3.09
 *
 * @since 3.09 (derived from Seagis)
 * @module
 */
final class FormatQuery extends Query {
    /**
     * Column to appear after the {@code "SELECT"} clause.
     */
    final Column name, format, encoding;

    /**
     * Parameter to appear after the {@code "FROM"} clause.
     */
    final Parameter byName;

    /**
     * Creates a new query for the specified database.
     *
     * @param database The database for which this query is created.
     */
    public FormatQuery(final Database database) {
        super(database, "Formats");
        final QueryType[] usage = {SELECT, LIST};
        name     = addMandatoryColumn("name",     usage);
        format   = addMandatoryColumn("plugin",   usage);
        encoding = addMandatoryColumn("packMode", usage);
        byName   = addParameter(name, SELECT);
        name.setOrdering(Ordering.ASC, LIST);
    }
}
