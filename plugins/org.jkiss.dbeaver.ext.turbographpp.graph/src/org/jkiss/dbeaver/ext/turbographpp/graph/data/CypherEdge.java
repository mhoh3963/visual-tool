/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2024 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.turbographpp.graph.data;

import java.util.LinkedHashMap;
import java.util.List;
import org.jkiss.dbeaver.ext.turbographpp.graph.graphfx.graphview.SmartStyleProxy;

public class CypherEdge {
    private String id;
    private List<String> types;
    private LinkedHashMap<String, Object> property;
    private String startNodeID;
    private String endNodeID;
    private DisplayType displayType;
    private String displayProperty;
    private String lineStrength = SmartStyleProxy.DEFAULT_EDGE_LINE_STRENGTH;
    private String lineColor = SmartStyleProxy.DEFAULT_EDGE_LINE_COLOR;
    private String lineStyle = SmartStyleProxy.DEFAULT_EDGE_LINE_STYLE;
    private int textSize = SmartStyleProxy.DEFAULT_EDGE_LABEL_SIZE;

    public CypherEdge(
            String id,
            List<String> types,
            LinkedHashMap<String, Object> property,
            String startNodeID,
            String endNodeID) {
        this.id = id;
        this.types = types;
        this.property = new LinkedHashMap<>();
        if (property != null) {
            this.property.putAll(property);
        }
        this.startNodeID = startNodeID;
        this.endNodeID = endNodeID;
        this.displayType = DisplayType.TYPE;
    }

    public String getID() {
        return this.id;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public LinkedHashMap<String, Object> getProperties() {
        return this.property;
    }

    public String getProperty(String key) {
        return String.valueOf(this.property.get(key));
    }

    public String getStartNodeID() {
        return this.startNodeID;
    }

    public String getEndNodeID() {
        return this.endNodeID;
    }

    public String toString() {
        switch (displayType) {
            case ID:
                return id;
            case PROPERTY:
                return String.valueOf(property.get(displayProperty));
            case TYPE:
                return String.valueOf(types);
            default:
                return String.valueOf(types);
        }
    }

    public void setDisplayType(DisplayType type) {
        displayType = type;
    }

    public void setDisplayProperty(String property) {
        displayProperty = property;
    }

    public void setLineStrength(String strength) {
        lineStrength = strength;
    }

    public String getLineStrength() {
        return lineStrength;
    }

    public void setLineColor(String color) {
        lineColor = color;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineStyle(String style) {
        lineStyle = style;
    }

    public String getLineStyle() {
        return lineStyle;
    }

    public void setTextSize(int size) {
        textSize = size;
    }

    public int getTextSize() {
        return textSize;
    }
}
