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

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.DouglasPeuckerSimplifier;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotoolkit.feature.FeatureExt;
import org.geotoolkit.geometry.jts.JTSMapping;
import org.geotoolkit.geometry.jts.JTS;
import org.geotoolkit.gui.swing.render2d.JMap2D;
import org.geotoolkit.gui.swing.resource.MessageBundle;
import org.apache.sis.referencing.CRS;
import org.apache.sis.util.logging.Logging;
import org.opengis.feature.AttributeType;
import org.opengis.feature.Feature;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author Johann Sorel (Geomatys)
 * @module
 */
public class JSimplificationPanel extends javax.swing.JPanel {

    private static final Logger LOGGER = Logging.getLogger("org.geotoolkit.gui.swing.render2d.control.edition");

    public static final String GEOMETRY_PROPERTY = "geometry";

    private JMap2D map;
    private Feature original = null;
    private Geometry current = null;

    public JSimplificationPanel(final JMap2D map) {
        this.map = map;
        initComponents();
    }

    public JMap2D getMap() {
        return map;
    }

    public void setMap(JMap2D map) {
        this.map = map;
    }

    public void setGeometry(final Feature feature){
        this.original = feature;
        this.current = null;
    }

    /**
     * @return Geometry is data CRS.
     */
    public Geometry getGeometry(){
        if(current == null){
            return FeatureExt.getDefaultGeometryValue(original)
                    .filter(Geometry.class::isInstance)
                    .map(Geometry.class::cast)
                    .orElse(null);
        }else{
            return current;
        }
    }

    private boolean generate(){

        if(original == null){
            return false;
        }

        if(map == null){
            return false;
        }

        final boolean mapCrs = guiMapCrs.isSelected();

        try{
            final CoordinateReferenceSystem mapCRS = map.getCanvas().getObjectiveCRS2D();
            final AttributeType<?> desc = FeatureExt.castOrUnwrap(FeatureExt.getDefaultGeometry(original.getType()))
                    .orElseThrow(() -> new IllegalStateException("Impossible to determine a valid geometric attribute"));
            final CoordinateReferenceSystem dataCRS = FeatureExt.getCRS(desc);
            Geometry geom = FeatureExt.getDefaultGeometryValue(original)
                    .filter(Geometry.class::isInstance)
                    .map(Geometry.class::cast)
                    .orElseThrow(() -> new IllegalStateException("No geometry initialized"));
            geom = (Geometry) geom.clone();
            final Class clazz = geom.getClass();

            if(mapCrs){
                //reproject geometry in map crs for simplification
                final MathTransform trs = CRS.findOperation(dataCRS,mapCRS, null).getMathTransform();
                geom = JTS.transform(geom, trs);
            }

            if(guiDouglas.isSelected()){
                final DouglasPeuckerSimplifier simplifier = new DouglasPeuckerSimplifier(geom);
                simplifier.setDistanceTolerance((Double)guiIndice.getValue());
                current = simplifier.getResultGeometry();
            }else{
                final TopologyPreservingSimplifier simplifier = new TopologyPreservingSimplifier(geom);
                simplifier.setDistanceTolerance((Double)guiIndice.getValue());
                current = simplifier.getResultGeometry();
            }

            if(mapCrs){
                //reproject geometry in data crs
                final MathTransform trs = CRS.findOperation(mapCRS, dataCRS, null).getMathTransform();
                current = JTS.transform(current, trs);
            }

            //ensure geometry type is preserved
            current = JTSMapping.convertType(current, clazz);

            guiError.setText("");
            return true;
        }catch(Exception ex){
            LOGGER.log(Level.INFO, ex.getLocalizedMessage(),ex);
            guiError.setText(ex.getLocalizedMessage());
            return false;
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupSimplify = new javax.swing.ButtonGroup();
        groupCRS = new javax.swing.ButtonGroup();
        guiRollback = new javax.swing.JButton();
        guiApply = new javax.swing.JButton();
        guiError = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        guiIndice = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        guiMapCrs = new javax.swing.JRadioButton();
        guiDataCrs = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        guiDouglas = new javax.swing.JRadioButton();
        guiTopology = new javax.swing.JRadioButton();

        guiRollback.setText(MessageBundle.format("cancel")); // NOI18N
        guiRollback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guiRollbackActionPerformed(evt);
            }
        });

        guiApply.setText(MessageBundle.format("apply")); // NOI18N
        guiApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guiApplyActionPerformed(evt);
            }
        });

        guiError.setForeground(new java.awt.Color(255, 0, 0));

        lbl.setText(MessageBundle.format("factor")); // NOI18N

        guiIndice.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(MessageBundle.format("crs"))); // NOI18N

        groupCRS.add(guiMapCrs);
        guiMapCrs.setSelected(true);
        guiMapCrs.setText(MessageBundle.format("map")); // NOI18N

        groupCRS.add(guiDataCrs);
        guiDataCrs.setText(MessageBundle.format("data")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(guiDataCrs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(guiMapCrs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(guiMapCrs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guiDataCrs)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(MessageBundle.format("type"))); // NOI18N

        groupSimplify.add(guiDouglas);
        guiDouglas.setSelected(true);
        guiDouglas.setText(MessageBundle.format("douglaspeuker")); // NOI18N

        groupSimplify.add(guiTopology);
        guiTopology.setText(MessageBundle.format("topologic")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(guiDouglas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(guiTopology, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(guiDouglas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guiTopology)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guiIndice, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(guiError, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guiApply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guiRollback))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {guiApply, guiRollback});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl)
                    .addComponent(guiIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(guiError, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(guiRollback, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(guiApply, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void guiApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guiApplyActionPerformed
        if(generate()){
            firePropertyChange(GEOMETRY_PROPERTY, -1,1);
        }
    }//GEN-LAST:event_guiApplyActionPerformed

    private void guiRollbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guiRollbackActionPerformed
        this.current = null;
        firePropertyChange(GEOMETRY_PROPERTY, -1,1);
    }//GEN-LAST:event_guiRollbackActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup groupCRS;
    private javax.swing.ButtonGroup groupSimplify;
    private javax.swing.JButton guiApply;
    private javax.swing.JRadioButton guiDataCrs;
    private javax.swing.JRadioButton guiDouglas;
    private javax.swing.JLabel guiError;
    private javax.swing.JSpinner guiIndice;
    private javax.swing.JRadioButton guiMapCrs;
    private javax.swing.JButton guiRollback;
    private javax.swing.JRadioButton guiTopology;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl;
    // End of variables declaration//GEN-END:variables
}
