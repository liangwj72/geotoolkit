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
package org.geotoolkit.filter.visitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.geotoolkit.util.NamesExt;
import org.opengis.feature.FeatureType;
import org.opengis.feature.PropertyType;

import org.opengis.util.GenericName;
import org.opengis.filter.expression.PropertyName;


/**
 * A simple visitor that extracts every attribute used by a filter or an expression
 * @module
 */
public class FilterAttributeExtractor extends DefaultFilterVisitor {

    /*
     * Last set visited
     */
    private final Set<GenericName> attributeNames = new HashSet<>();

    /**
     * feature type to evaluate against
     */
    private final FeatureType featureType;

    /**
     * Just extract the property names; don't check against a feature type.
     */
    public FilterAttributeExtractor() {
        this(null);
    }

    /**
     * Use the provided feature type as a sanity check when extracting
     * property names.
     *
     * @param featureType
     */
    public FilterAttributeExtractor(final FeatureType featureType) {
        this.featureType = featureType;
    }

    /**
     * @return an unmofiable set of the attribute names found so far during the visit
     */
    public Set getAttributeNameSet() {
        return Collections.unmodifiableSet(attributeNames);
    }

    /**
     * @return an array of the attribute names found so far during the visit
     */
    public GenericName[] getAttributeNames() {
        return (GenericName[]) attributeNames.toArray(new GenericName[attributeNames.size()]);
    }

    /**
     * Resets the attributes found so that a new attribute search can be performed
     */
    public void clear() {
        attributeNames.clear();
    }

    @Override
    public Object visit(final PropertyName expression, final Object data) {
        if (data != null && data != attributeNames && data instanceof Set) {
            attributeNames.addAll((Set) data);
        }

        if (featureType != null) {
            //evaluate against the feature type instead of using straight name
            // since the path from the property name may be an xpath or a
            // namespace prefixed string
            PropertyType type = (PropertyType) expression.evaluate(featureType);
            if(type != null) {
               attributeNames.add( type.getName() );
            }else{
               attributeNames.add(NamesExt.valueOf(expression.getPropertyName()) );
            }
        }else{
            attributeNames.add(NamesExt.valueOf(expression.getPropertyName()) );
        }

        return attributeNames;
    }
}
