/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2012 - 2013, Geomatys
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
package org.geotoolkit.coverage;

import java.awt.Dimension;
import java.awt.image.RenderedImage;
import java.util.List;
import java.util.Map;
import javax.swing.ProgressMonitor;
import org.apache.sis.storage.DataStoreException;
import org.geotoolkit.coverage.io.CoverageStoreException;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * May be implemented by Coverage reference when the underlying structure is a
 * pyramid.
 *
 * @author Johann Sorel (Geomatys)
 * @module pending
 */
public interface PyramidalCoverageReference extends CoverageReference{

    PyramidSet getPyramidSet() throws DataStoreException;

    List<GridSampleDimension> getSampleDimensions(int index) throws DataStoreException;

    /**
     *
     * @return true if model can be modified
     */
    @Override
    boolean isWritable() throws CoverageStoreException;

    /**
     * Create SampleDimension.
     * @param dimensions samples
     * @param analyse a map of min/max values per bands (can be null)
     * @throws DataStoreException
     */
    void createSampleDimension(final List<GridSampleDimension> dimensions, final Map<String, Object> analyse) throws DataStoreException;

    /**
     *
     * @param crs
     * @return created pyramid
     * @throws DataStoreException
     */
    Pyramid createPyramid(CoordinateReferenceSystem crs) throws DataStoreException;

    /**
     * Delete given pyramid.
     *
     * @throws DataStoreException
     */
    void deletePyramid(String pyramidId) throws DataStoreException;

    /**
     *
     * @param pyramidId : pyramid id in which to insert the mosaic
     * @param gridSize : size in number of column and row
     * @param tilePixelSize : size of a tile in pixel
     * @param upperleft : upperleft corner position in pyramid crs
     * @param pixelscale : size of a pixel in crs unit
     * @return created mosaic
     * @throws DataStoreException
     */
    GridMosaic createMosaic(String pyramidId, Dimension gridSize,
             Dimension tilePixelSize, DirectPosition upperleft, double pixelscale) throws DataStoreException;

    /**
     * Delete given mosaic.
     *
     * @throws DataStoreException
     */
    void deleteMosaic(String pyramidId, String mosaicId) throws DataStoreException;

    /**
     * Write a complete mosaic level used the given rendered image.
     * The rendered image size and tile size must match the mosaic definition.
     *
     * @param pyramidId
     * @param mosaicId
     * @param image
     * @param onlyMissing : set to true to fill only missing tiles
     * @param monitor A progress monitor in order to eventually cancel the process. May be {@code null}.
     * @throws DataStoreException
     */
    void writeTiles(String pyramidId, String mosaicId, RenderedImage image, boolean onlyMissing, ProgressMonitor monitor) throws DataStoreException;

    /**
     * Write or update a single tile in the mosaic.
     * Rendered image size must match mosaic tile size.
     *
     * @param pyramidId : pyramid id in which to insert the tile
     * @param mosaicId : mosaic id in which to insert the tile
     * @param tileX : position of the tile , column
     * @param tileY : position of the tile , row
     * @param image : image to insert
     * @throws DataStoreException
     */
    void writeTile(String pyramidId, String mosaicId, int tileX, int tileY, RenderedImage image) throws DataStoreException;

    void deleteTile(String pyramidId, String mosaicId, int tileX, int tileY) throws DataStoreException;
}
