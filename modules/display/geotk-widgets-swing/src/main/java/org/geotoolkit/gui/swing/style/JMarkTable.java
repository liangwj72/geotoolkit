/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2007 - 2008, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2008 - 2009, Johann Sorel
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
package org.geotoolkit.gui.swing.style;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import org.geotoolkit.gui.swing.resource.IconBundle;
import org.geotoolkit.map.MapLayer;
import org.geotoolkit.style.StyleConstants;
import org.opengis.style.GraphicalSymbol;
import org.opengis.style.Mark;

/**
 * mark table
 * 
 * @author Johann Sorel
 * @module pending
 */
public class JMarkTable <T> extends StyleElementEditor<List> {

    private static final Icon ICO_UP = IconBundle.getIcon("16_uparrow");
    private static final Icon ICO_DOWN = IconBundle.getIcon("16_downarrow");
    private static final Icon ICO_NEW = IconBundle.getIcon("16_add_data");
    private static final Icon ICO_DELETE = IconBundle.getIcon("16_delete");

    private MapLayer layer = null;
    private final GraphicalModel model = new GraphicalModel(null);
    private final GraphicEditor editor = new GraphicEditor();

    /** Creates new form JFontsPanel */
    public JMarkTable() {
        super(List.class);
        initComponents();
        init();
    }

    private void init() {
        tabGraphics.setTableHeader(null);
        tabGraphics.setModel(model);
        tabGraphics.getColumnModel().getColumn(0).setCellEditor(editor);
        tabGraphics.setDefaultRenderer(Mark.class, new GraphicRenderer());
        tabGraphics.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public void setLayer(final MapLayer layer) {
        editor.setLayer(layer);
        this.layer = layer;
    }

    @Override
    public MapLayer getLayer() {
        return layer;
    }

    @Override
    public void parse(final List graphics) {
        model.setGraphics(graphics);
    }

    @Override
    public List<GraphicalSymbol> create() {
        return model.getGraphics();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        tabGraphics = new JTable();
        guiUp = new JButton();
        guiDown = new JButton();
        guiNew = new JButton();
        guiDelete = new JButton();

        setOpaque(false);

        jScrollPane1.setViewportView(tabGraphics);

        guiUp.setIcon(ICO_UP);
        guiUp.setBorderPainted(false);
        guiUp.setContentAreaFilled(false);
        guiUp.setMargin(new Insets(2, 2, 2, 2));
        guiUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guiUpActionPerformed(evt);
            }
        });

        guiDown.setIcon(ICO_DOWN);
        guiDown.setBorderPainted(false);
        guiDown.setContentAreaFilled(false);
        guiDown.setMargin(new Insets(2, 2, 2, 2));
        guiDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guiDownActionPerformed(evt);
            }
        });

        guiNew.setIcon(ICO_NEW);
        guiNew.setBorderPainted(false);
        guiNew.setContentAreaFilled(false);
        guiNew.setMargin(new Insets(2, 2, 2, 2));
        guiNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guiNewActionPerformed(evt);
            }
        });

        guiDelete.setIcon(ICO_DELETE);
        guiDelete.setBorderPainted(false);
        guiDelete.setContentAreaFilled(false);
        guiDelete.setMargin(new Insets(2, 2, 2, 2));
        guiDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guiDeleteActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(guiNew)
                    .addComponent(guiUp)
                    .addComponent(guiDown)
                    .addComponent(guiDelete)))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {guiDelete, guiDown, guiNew, guiUp});

        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(guiUp)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiDown)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiNew)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiDelete)
                .addContainerGap(93, Short.MAX_VALUE))
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    private void guiUpActionPerformed(final ActionEvent evt) {//GEN-FIRST:event_guiUpActionPerformed
        int index = tabGraphics.getSelectionModel().getMinSelectionIndex();

        if (index >= 0) {
            Mark m = (Mark) model.getValueAt(index, 0);
            model.moveUp(m);
        }
}//GEN-LAST:event_guiUpActionPerformed

    private void guiDownActionPerformed(final ActionEvent evt) {//GEN-FIRST:event_guiDownActionPerformed
        int index = tabGraphics.getSelectionModel().getMinSelectionIndex();

        if (index >= 0) {
            Mark m = (Mark) model.getValueAt(index, 0);
            model.moveDown(m);
        }
}//GEN-LAST:event_guiDownActionPerformed

    private void guiNewActionPerformed(final ActionEvent evt) {//GEN-FIRST:event_guiNewActionPerformed
        model.newGraphical();
}//GEN-LAST:event_guiNewActionPerformed

    private void guiDeleteActionPerformed(final ActionEvent evt) {//GEN-FIRST:event_guiDeleteActionPerformed
        int index = tabGraphics.getSelectionModel().getMinSelectionIndex();

        if (index >= 0) {
            model.deleteGraphical(index);
        }
    }//GEN-LAST:event_guiDeleteActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton guiDelete;
    private JButton guiDown;
    private JButton guiNew;
    private JButton guiUp;
    private JScrollPane jScrollPane1;
    private JTable tabGraphics;
    // End of variables declaration//GEN-END:variables



class GraphicalModel extends AbstractTableModel {

    private final List<GraphicalSymbol> graphics = new ArrayList<GraphicalSymbol>();

    GraphicalModel(final List<GraphicalSymbol> graphs) {
        if(graphs != null) this.graphics.addAll(graphs);
    }

    public void newGraphical() {
        Mark m = getStyleFactory().mark(getFilterFactory().literal("round"),StyleConstants.DEFAULT_FILL,StyleConstants.DEFAULT_STROKE);

        graphics.add(m);
        int last = graphics.size() - 1;
        fireTableRowsInserted(last, last);
    }

    public void deleteGraphical(final int index) {
        graphics.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public void moveUp(final GraphicalSymbol m) {
        int index = graphics.indexOf(m);
        if (index != 0) {
            graphics.remove(m);
            graphics.add(index - 1, m);
            fireTableDataChanged();
        }
    }

    public void moveDown(final GraphicalSymbol m) {
        int index = graphics.indexOf(m);
        if (index != graphics.size() - 1) {
            graphics.remove(m);
            graphics.add(index + 1, m);
            fireTableDataChanged();
        }
    }

    public void setGraphics(final List<GraphicalSymbol> marks) {
        this.graphics.clear();
        this.graphics.addAll(marks);
        fireTableDataChanged();
    }

    public List<GraphicalSymbol> getGraphics() {
        return new ArrayList<GraphicalSymbol>(this.graphics);
    }

    @Override
    public int getRowCount() {
        return graphics.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return Mark.class;
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return graphics.get(rowIndex);
    }
}

class GraphicRenderer extends DefaultTableCellRenderer {

    private String text = "";

    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {

        JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);

        if(value instanceof Mark){
            Mark m = (Mark) value;
            lbl.setText(m.getWellKnownName().toString());
        }
        return lbl;
    }
}

class GraphicEditor extends AbstractCellEditor implements TableCellEditor {//implements TableCellEditor{
    private MapLayer layer = null;
    private final JMarkPane editpane = new JMarkPane();
    private final JButton but = new JButton("");
    private Mark mark = null;

    public GraphicEditor() {
        super();
        but.setBorderPainted(false);

        but.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (mark != null) {
                    JDialog dia = new JDialog();

                    Mark oldMark = mark;
                    //panneau d'edition           
                    editpane.parse(mark);

                    dia.setContentPane(editpane);
                    dia.setLocationRelativeTo(but);
                    dia.pack();
                    dia.setModal(true);
                    dia.setVisible(true);

                    mark = editpane.create();
                    System.out.println(mark);

                    List<GraphicalSymbol> symbols = model.getGraphics();
                    symbols.add(symbols.indexOf(oldMark),mark);
                    symbols.remove(oldMark);
                    model.setGraphics(symbols);

                    but.setText(mark.getWellKnownName().toString());
                }
            }
        });
    }

    public void setLayer(final MapLayer layer) {
        editpane.setLayer(layer);
        this.layer = layer;
    }

    public MapLayer getLayer() {
        return layer;
    }

    @Override
    public Object getCellEditorValue() {
        return mark;
    }

//    public boolean isCellEditable(EventObject e) {
//        return true;
//    }
    
    @Override
    public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {

        if (value != null && value instanceof Mark) {
            mark = (Mark) value;
            but.setText(mark.getWellKnownName().toString());
        } else {
            but.setText("????");
            mark = null;
        }
        return but;
    }

//    public boolean shouldSelectCell(EventObject anEvent) {
//        return true;
//    }
//
//    public boolean stopCellEditing() {
//        return true;
//    }
//
//    public void cancelCellEditing() {
//    }
//
//    public void addCellEditorListener(CellEditorListener l) {        
//    }
//    
//    public void removeCellEditorListener(CellEditorListener l) {
//    }
}

}
