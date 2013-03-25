/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2012-2013, Geomatys
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
package org.geotoolkit.gui.swing.etl;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import org.geotoolkit.feature.FeatureUtilities;
import org.geotoolkit.gui.swing.propertyedit.JAttributeEditor;
import org.geotoolkit.gui.swing.resource.MessageBundle;
import org.geotoolkit.process.chain.ChainProcessDescriptor;
import org.geotoolkit.process.chain.model.Chain;
import org.geotoolkit.process.chain.model.ChainDataTypes;
import org.geotoolkit.process.chain.model.ClassFull;
import org.geotoolkit.process.chain.model.DataLink;
import org.geotoolkit.process.chain.model.Parameter;
import org.geotoolkit.process.chain.model.Parameterized;
import org.jdesktop.swingx.combobox.ListComboBoxModel;
import org.opengis.parameter.ParameterDescriptor;

/**
 * Dialog to edit parameter values.
 *
 * @author Johann Sorel (Geomatys)
 * @author Cédric Briançon (Geomatys)
 */
public class JParameterPanel extends javax.swing.JPanel {

    private final Parameterized process;
    private final Parameter parameter;
    private final int paramId;
    private final boolean inParam;
    private final JDialog dialog;
    private final JAttributeEditor defValueEditor;
    // Last value for class
    private Class oldType;

    private JParameterPanel(final Parameterized process, final Parameter parameter, 
            final int paramId, final boolean inParam, final JDialog dialog, final boolean editable) {
        this.process = process;
        this.parameter = parameter;
        this.paramId = paramId;
        this.inParam = inParam;
        this.dialog = dialog;
        initComponents();
        defValueEditor = new JAttributeEditor();
        oldType = parameter.getType().getRealClass();

        guiMandatory.setSelected(parameter.getMinOccurs()>0);
        guiCode.setText(parameter.getCode());
        guiDescription.setText(parameter.getRemarks());

        guiType.setModel(new ListComboBoxModel(ChainDataTypes.VALID_TYPES));
        guiType.setSelectedItem(parameter.getType().getRealClass());
        guiType.setRenderer(new JClassCellRenderer());

        final ParameterDescriptor paramDesc = ChainProcessDescriptor.convertParameterDtoToParameterDescriptor(parameter, false);
        defValueEditor.setProperty(FeatureUtilities.toProperty(paramDesc.createValue()));
        defValueEditor.setPreferredSize(guiPanelDefaultValue.getPreferredSize());
        guiPanelDefaultValue.add(defValueEditor, BorderLayout.WEST);

        guiCode.setEditable(editable);
        guiMandatory.setEnabled(editable);
        guiDescription.setEditable(editable);
        guiType.setEnabled(editable);
        guiSave.setVisible(editable);
        defValueEditor.setEnabled(editable);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        guiCode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        guiType = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        guiDescription = new javax.swing.JTextArea();
        guiMandatory = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        guiCancel = new javax.swing.JButton();
        guiSave = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        guiPanelDefaultValue = new javax.swing.JPanel();

        jLabel1.setText(MessageBundle.getString("code")); // NOI18N

        jLabel2.setText(MessageBundle.getString("type")); // NOI18N

        guiType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guiTypeActionPerformed(evt);
            }
        });

        jLabel3.setText(MessageBundle.getString("description")); // NOI18N

        guiDescription.setColumns(20);
        guiDescription.setRows(5);
        jScrollPane1.setViewportView(guiDescription);

        guiMandatory.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jLabel5.setText(MessageBundle.getString("mandatory")); // NOI18N

        guiCancel.setText(MessageBundle.getString("cancel")); // NOI18N
        guiCancel.setMargin(new java.awt.Insets(0, 20, 0, 20));
        guiCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guiCancelActionPerformed(evt);
            }
        });

        guiSave.setText(MessageBundle.getString("save")); // NOI18N
        guiSave.setMargin(new java.awt.Insets(0, 20, 0, 20));
        guiSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guiSaveActionPerformed(evt);
            }
        });

        jLabel4.setText(MessageBundle.getString("defaultValue")); // NOI18N

        guiPanelDefaultValue.setPreferredSize(new java.awt.Dimension(280, 29));
        guiPanelDefaultValue.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guiMandatory)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(guiSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(guiCancel))
                            .addComponent(jScrollPane1)
                            .addComponent(guiType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(guiCode)
                            .addComponent(guiPanelDefaultValue, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(guiMandatory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guiCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guiType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guiPanelDefaultValue, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(guiCancel)
                                    .addComponent(guiSave))
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jLabel4)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
    }// </editor-fold>//GEN-END:initComponents

private void guiSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guiSaveActionPerformed

    final String oldCode = parameter.getCode();
    String newCode = guiCode.getText();

    //clean parameter code
    newCode = newCode.trim().replace(' ', '_');


    //check if this code is already used by another parameter
    if(!oldCode.equals(newCode)){
        final List<Parameter> lst = inParam ? process.getInputs() : process.getOutputs();
        boolean found;
        do{
            found = false;
            for(Parameter p : lst){
                if(newCode.equals(p.getCode())){
                    newCode += "_";
                    found = true;
                }
            }
        }while(found);
    }


    final List<DataLink> updatedLinks = new ArrayList<DataLink>();
    if (process instanceof Chain) {
        final Chain chain = (Chain) process;
        //check if the code changed, if so we must properly update the links
        if(!parameter.getCode().equals(newCode)){
            for(final DataLink lk : chain.getDataLinks().toArray(new DataLink[chain.getDataLinks().size()])){
                if(lk.getSourceId() == paramId && lk.getSourceCode().equals(parameter.getCode())){
                    chain.getDataLinks().remove(lk);
                    lk.setSourceId(paramId);
                    lk.setSourceCode(newCode);
                    updatedLinks.add(lk);
                }
                if(lk.getTargetId() == paramId && lk.getTargetCode().equals(parameter.getCode())){
                    chain.getDataLinks().remove(lk);
                    lk.setTargetId(paramId);
                    lk.setTargetCode(newCode);
                    updatedLinks.add(lk);
                }
            }
        }
    }

    if(paramId == Integer.MIN_VALUE){
        process.getInputs().remove(parameter);
    }else if(paramId == Integer.MAX_VALUE){
         process.getOutputs().remove(parameter);
    }

    parameter.setCode(newCode);
    parameter.setRemarks(guiDescription.getText());
    parameter.setMinOccurs(guiMandatory.isSelected() ?  1 : 0);
    parameter.setMaxOccurs(1);
    parameter.setType(new ClassFull((Class)guiType.getSelectedItem()));

    parameter.setDefaultValue(
            ChainProcessDescriptor.convertDefaultValueInClass(defValueEditor.getProperty().getValue(), (Class)guiType.getSelectedItem()));

    if(paramId == Integer.MIN_VALUE){
        process.getInputs().add(parameter);
    }else if(paramId == Integer.MAX_VALUE){
         process.getOutputs().add(parameter);
    }

     if (process instanceof Chain) {
        final Chain chain = (Chain) process;
        for(DataLink lk : updatedLinks){
            chain.getDataLinks().add(lk);
        }
     }

    dialog.dispose();
}//GEN-LAST:event_guiSaveActionPerformed

private void guiCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guiCancelActionPerformed
    dialog.dispose();
}//GEN-LAST:event_guiCancelActionPerformed

    private void guiTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guiTypeActionPerformed
        if (oldType.equals(guiType.getSelectedItem())) {
            return;
        }

        // if there is a choice made in the type, empty the parameter default value and display
        // the appropriate editor.
        oldType = (Class)guiType.getSelectedItem();
        parameter.setType(new ClassFull((Class)guiType.getSelectedItem()));
        parameter.setDefaultValue(null);
        final ParameterDescriptor paramDesc = ChainProcessDescriptor.convertParameterDtoToParameterDescriptor(parameter, false);
        defValueEditor.setProperty(FeatureUtilities.toProperty(paramDesc.createValue()));
        guiPanelDefaultValue.repaint();
    }//GEN-LAST:event_guiTypeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton guiCancel;
    private javax.swing.JTextField guiCode;
    private javax.swing.JTextArea guiDescription;
    private javax.swing.JCheckBox guiMandatory;
    private javax.swing.JPanel guiPanelDefaultValue;
    private javax.swing.JButton guiSave;
    private javax.swing.JComboBox guiType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public static void showDialog(final Parameterized process,final Parameter parameter, final int paramId, final boolean inParam, final boolean editable){

        final JDialog dialog = new JDialog();
        dialog.setModal(true);
        final JParameterPanel pane = new JParameterPanel(process, parameter, paramId, inParam, dialog, editable);
        dialog.setContentPane(pane);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

}
