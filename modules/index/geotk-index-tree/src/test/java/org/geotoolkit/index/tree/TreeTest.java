/*
 *    Geotoolkit.org - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2008-2012, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2009-2012, Geomatys
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
package org.geotoolkit.index.tree;

import java.util.List;
import org.geotoolkit.geometry.GeneralDirectPosition;
import org.geotoolkit.geometry.GeneralEnvelope;
import org.geotoolkit.util.ArgumentChecks;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**Class which contains tree test utils methods.
 *
 * @author Rémi Maréchal (Géomatys).
 */
public abstract class TreeTest {

    /**Compare 2 lists elements.
     *
     * <blockquote><font size=-1> <strong> NOTE: return {@code true} if listA
     * and listB are empty. </strong> </font></blockquote>
     *
     * @param listA
     * @param listB
     * @throws IllegalArgumentException if listA or ListB is null.
     * @return true if listA contains same elements from listB.
     */
    protected boolean compareList(final List<Envelope> listA, final List<Envelope> listB) {
        ArgumentChecks.ensureNonNull("compareList : listA", listA);
        ArgumentChecks.ensureNonNull("compareList : listB", listB);

        if (listA.isEmpty() && listB.isEmpty()) {
            return true;
        }

        if (listA.size() != listB.size()) {
            return false;
        }

        boolean shapequals = false;
        for (Envelope shs : listA) {
            for (Envelope shr : listB) {
                if (new GeneralEnvelope(shs).equals(shr, 1E-9, false)) {
                    shapequals = true;
                    break;
                }
            }
            if (!shapequals) {
                return false;
            }
            shapequals = false;
        }
        return true;
    }

    /**Create a default adapted test entry({@code GeneralEnvelope}).
     *
     * @param position the median of future entry.
     * @return {@code GeneralEnvelope} entry.
     */
    public static GeneralEnvelope createEntry(final DirectPosition position) {
        final double[] coord = position.getCoordinate();
        int length = coord.length;
        double[] coordLow = new double[length];
        double[] coordUpp = new double[length];
        for (int i = 0; i < length; i++) {
            coordLow[i] = coord[i] - (Math.random() * 5 + 5);
            coordUpp[i] = coord[i] + (Math.random() * 5 + 5);
        }
        final CoordinateReferenceSystem crs = position.getCoordinateReferenceSystem();
        if (crs == null) {
            return new GeneralEnvelope(new GeneralDirectPosition(coordLow), new GeneralDirectPosition(coordUpp));
        }
        final GeneralDirectPosition dpLow = new GeneralDirectPosition(crs);
        final GeneralDirectPosition dpUpp = new GeneralDirectPosition(crs);
        for (int i = 0; i < length; i++) {
            dpLow.setOrdinate(i, coordLow[i]);
            dpUpp.setOrdinate(i, coordUpp[i]);
        }
        return new GeneralEnvelope(dpLow, dpUpp);
    }
}
