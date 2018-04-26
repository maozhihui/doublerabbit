package com.comba.web.ui;

/**
 * 对应EasyUI Combobox组件选项Json模型
 *前端对应使用实例
 *$('#Id').combobox({
	url:'',
	valueField:'value',
	textField:'text',
	editable:false
});
 * @author wengzhonghui
 */
public class ComboBoxOptionModel {

    private String value;
    private String text;
    private boolean selected;

    public ComboBoxOptionModel() {
        super();
    }

    public ComboBoxOptionModel(String value, String text) {
        super();
        this.value = value;
        this.text = text;
    }

    public ComboBoxOptionModel(String value, String text, boolean selected) {
        this.value = value;
        this.text = text;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
