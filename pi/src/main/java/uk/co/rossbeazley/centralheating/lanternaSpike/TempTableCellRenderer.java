package uk.co.rossbeazley.centralheating.lanternaSpike;

import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.ThemeDefinition;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableCellRenderer;

/**
 * Default implementation of {@code TableCellRenderer}
 * @param <V> Type of data stored in each table cell
 * @author Martin
 */
public class TempTableCellRenderer<V> implements TableCellRenderer<V> {
    @Override
    public TerminalSize getPreferredSize(Table<V> table, V cell, int columnIndex, int rowIndex) {
        String[] lines = getContent(cell);
        int maxWidth = 0;
        for(String line: lines) {
            int length = TerminalTextUtils.getColumnWidth(line);
            if(maxWidth < length) {
                maxWidth = length;
            }
        }
        return new TerminalSize(maxWidth, lines.length);
    }

    @Override
    public void drawCell(Table<V> table, V cell, int columnIndex, int rowIndex, TextGUIGraphics textGUIGraphics) {
        ThemeDefinition themeDefinition = table.getThemeDefinition();
        if((table.getSelectedColumn() == columnIndex && table.getSelectedRow() == rowIndex) ||
                (table.getSelectedRow() == rowIndex && !table.isCellSelection())) {
            if(table.isFocused()) {
                textGUIGraphics.applyThemeStyle(themeDefinition.getActive());
            }
            else {
                textGUIGraphics.applyThemeStyle(themeDefinition.getSelected());
            }
            textGUIGraphics.fill(' ');  //Make sure to fill the whole cell first
        }
        else {
            textGUIGraphics.applyThemeStyle(themeDefinition.getNormal());
        }

        if(columnIndex==1) {

            if(cell.toString().equals("on")) {
                TextColor red=TextColor.Indexed.fromRGB(255,0,0);
                textGUIGraphics.setBackgroundColor(red);
            } else {
                TextColor red=TextColor.Indexed.fromRGB(0,0,255);
                textGUIGraphics.setBackgroundColor(red);
            }
            textGUIGraphics.putString(0,0,"          ");
        }
        else
        {
            String[] lines = getContent(cell);
            int rowCount = 0;
            for(String line: lines) {
                textGUIGraphics.putString(0, rowCount++, line);
            }
        }

    }

    private String[] getContent(V cell) {
        String[] lines;
        if(cell == null) {
            lines = new String[] { "" };
        }
        else {
            lines = cell.toString().split("\r?\n");
        }
        return lines;
    }
}
