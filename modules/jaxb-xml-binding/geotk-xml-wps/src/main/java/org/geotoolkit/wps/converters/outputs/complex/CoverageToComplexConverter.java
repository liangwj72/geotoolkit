/*
 *    Constellation - An open source and standard compliant SDI
 *    http://www.constellation-sdi.org
 *
 *    (C) 2011, Geomatys
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 3 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotoolkit.wps.converters.outputs.complex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import net.iharder.Base64;
import org.geotoolkit.coverage.grid.GridCoverage2D;
import org.geotoolkit.coverage.io.CoverageIO;
import org.geotoolkit.coverage.io.CoverageStoreException;
import org.apache.sis.util.UnconvertibleObjectException;
import org.geotoolkit.wps.io.WPSMimeType;
import org.geotoolkit.wps.xml.ComplexDataType;
import org.geotoolkit.wps.xml.WPSXmlFactory;
import org.opengis.coverage.Coverage;

/**
 * Convert an GridCoverage2D to ComplexDataType using Base64 encoding.
 *
 * @author Quentin Boileau (Geomatys)
 */
public class CoverageToComplexConverter extends AbstractComplexOutputConverter<GridCoverage2D> {

    private static CoverageToComplexConverter INSTANCE;

    private CoverageToComplexConverter() {
    }

    public static synchronized CoverageToComplexConverter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CoverageToComplexConverter();
        }
        return INSTANCE;
    }

    @Override
    public Class<GridCoverage2D> getSourceClass() {
        return GridCoverage2D.class;
    }

    @Override
    public ComplexDataType convert(GridCoverage2D source, Map<String, Object> params) throws UnconvertibleObjectException {

        if (source == null) {
            throw new UnconvertibleObjectException("The output data should be defined.");
        }
        if (!(source instanceof GridCoverage2D) || !(source instanceof Coverage)) {
            throw new UnconvertibleObjectException("The requested output data is not an instance of GridCoverage2D.");
        }
        final WPSMimeType wpsMime = WPSMimeType.customValueOf((String) params.get(MIME));
        if (!wpsMime.equals(WPSMimeType.IMG_GEOTIFF) && !wpsMime.equals(WPSMimeType.IMG_GEOTIFF_BIS)) {
            throw new UnconvertibleObjectException("Only support GeoTiff Base64 encoding.");
        }

        String wpsVersion  = (String) params.get(WPSVERSION);
        if (wpsVersion == null) {
            LOGGER.warning("No WPS version set using default 1.0.0");
            wpsVersion = "1.0.0";
        }
        final ComplexDataType complex = WPSXmlFactory.buildComplexDataType(wpsVersion, "base64", WPSMimeType.IMG_GEOTIFF.val(), null);

        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            CoverageIO.write(source, "GEOTIFF", baos);
            baos.flush();
            byte[] bytesOut = baos.toByteArray();
            complex.getContent().add(Base64.encodeBytes(bytesOut));
            baos.close();
        } catch (CoverageStoreException ex) {
            throw new UnconvertibleObjectException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new UnconvertibleObjectException(ex.getMessage(), ex);
        }
        return complex;
    }

}
